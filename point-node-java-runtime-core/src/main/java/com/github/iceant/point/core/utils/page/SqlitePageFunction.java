package com.github.iceant.point.core.utils.page;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlitePageFunction<T> implements PageFunction{
    private final String sql;
    private final List<Object> params;
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<T> rowMapper;

    public SqlitePageFunction(String sql, List<Object> params, JdbcTemplate jdbcTemplate, RowMapper<T> rowMapper) {
        this.sql = sql;
        this.params = params;
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public Page<List<T>> getPage(PageRequest pageRequest) {
        StringBuilder count = new StringBuilder();
        count.append("SELECT COUNT(*) FROM (").append(sql).append(") AS __table__");
        Long dataSize = jdbcTemplate.query(count.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                for(int i=0; i<params.size(); i++){
                    ps.setObject(i+1, params.get(i));
                }
            }
        }, new ResultSetExtractor<Long>() {
            @Override
            public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    return rs.getLong(1);
                }
                return 0L;
            }
        });
        if(dataSize==0){
            return Page.makeEmpty(pageRequest.getPageNumber(), pageRequest.getPageSize());
        }
        StringBuilder pageSql = new StringBuilder(sql).append(" LIMIT ? OFFSET ?");
        int offset = (pageRequest.getPageNumber()-1)* pageRequest.getPageSize();
        List<T> data =  jdbcTemplate.query(pageSql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 0;
                if(params!=null && params.size()>0) {
                    for (; i < params.size(); i++) {
                        ps.setObject(i + 1, params.get(i));
                    }
                }
                ps.setObject(++i, pageRequest.getPageSize());
                ps.setObject(++i, offset);
            }
        }, rowMapper);
        return Page.makePage(pageRequest.getPageNumber(), pageRequest.getPageSize(), data, dataSize);
    }
}