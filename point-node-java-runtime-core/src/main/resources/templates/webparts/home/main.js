;(function(window, document, $){
    return {
        options:{
            el:'#pageBody'
        },
        display:function(ops){
            var self= this;
            $.get('${ctxPath}/api/things/list', function(datas){
                var html = self.part('templates/home.html').render({
                    datas:datas.data
                });
                this.options = $.extend(this.options, ops);
                $(this.options.el).html(html);
            });
        }
    };
}(window, document, jQuery));