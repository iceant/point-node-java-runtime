(function(window, document, $){
    // configuration
    $app.router.config({ mode: 'hash'});

    // adding routes
    $app.router
        .add('/home', function() {
            console.log(arguments);
            $('h3').html('TIME:'+new Date()
                +'<li><a href="#/get/name">Name</a></li>'
                +'<li><a href="#/product/11/part/salf23o4ridsfn@@$$">Part</a></li>'
                +'<li><a href="/logout">Logout</a></li>'
            );
        })
        .add('/get/:key', function(param){
            $('h3').html("<li>"+param.key+":"+$app.storage.decodeGet(param.key)+"</li>"
                +"<a href='#/home'>Home</a>");
        })
        .add('/product/:id/part/:pid', function(param){
            console.trace();
            console.log("/product...");
            $('h3').html("<li>id:"+param.id+"</li><li>pid:"+param.pid+"</li>"
            +"<a href='#/home'>Home</a>");
        })
        .add(function() {
            console.log('default');
        }).listen();

    // forwarding
    $app.router.navigate('/home', {"name":123}, 1, 2, 3);

    console.log(window.location.hash);
}(window, document, jQuery));