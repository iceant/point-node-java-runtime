;(function(window, document, $){
    return {
        options:{
            el:'#pageBody'
        },
        display:function(ops){
            var html = this.part('templates/home.html').render({
                datas:[
                    {id:1, name:'user1', age:12},
                    {id:2, name:'user2', age:14},
                    {id:3, name:'user3', age:16},
                ]
            });
            this.options = $.extend(this.options, ops);
            $(this.options.el).html(html);
        }
    };
}(window, document, jQuery));