package com.github.iceant.point.core.config;

import com.github.iceant.point.core.security.RestAuthenticationDetailsSource;
import com.github.iceant.point.core.security.RestAuthenticationFailureHandler;
import com.github.iceant.point.core.security.RestAuthenticationProvider;
import com.github.iceant.point.core.security.RestAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    final DataSource dataSource;
    @Value("${app.security.remember_me_token:point-node-java-runtime-rememberme}")
    private String rememberMeToken;

    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(daoAuthenticationProvider())
                .authenticationProvider(restAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/static/**", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .authenticationDetailsSource(restAuthenticationDetailsSource())
                    .loginProcessingUrl("/login")
                    .successHandler(new RestAuthenticationSuccessHandler())
                    .failureHandler(new RestAuthenticationFailureHandler())
                    .loginPage("/pages/login").permitAll()
                .and()
                .logout()
                    .logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true)
        ;

        http.rememberMe()
                .userDetailsService(userDetailsService())
                .tokenRepository(persistentTokenRepository())
                .key(rememberMeToken)
        ;

        http.sessionManagement()
                .maximumSessions(1)
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().mvcMatchers("/favicon.ico");
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder = passwordEncoder();
        String password = passwordEncoder.encode("pwd@123");

        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
        userDetailsManager.setEnableAuthorities(true);
        userDetailsManager.setEnableGroups(true);
        userDetailsManager.setDataSource(dataSource);
        if (!userDetailsManager.userExists("user")) {
            userDetailsManager.createUser(User.withUsername("user").password(password)
                    .roles("USER").build());
        }
        if (!userDetailsManager.userExists("admin")) {
            userDetailsManager.createUser(User.withUsername("admin").password(password)
                    .roles("USER", "ADMIN").build());
        }
        return userDetailsManager;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //// remember me

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    @Bean
    RestAuthenticationProvider restAuthenticationProvider(){
        return new RestAuthenticationProvider(userDetailsService(), passwordEncoder());
    }

    @Bean
    RestAuthenticationDetailsSource restAuthenticationDetailsSource(){
        return new RestAuthenticationDetailsSource();
    }

}