(function(window, document, $){
    // configuration
    $app.router.config({ mode: 'hash'});

    $app.webpart.load('home', function(){
        // adding routes
        $app.router
            .add('/home', function() {
                var home  = $app.webpart.get('home');
                var main = home.get('main.js').eval();
                main.display({el:'#pageBody'});
            })
            .add(function() {
                console.log('default');
            }).listen();

        // forwarding
        $app.router.navigate('/home');
    });

    console.log(window.location.hash);
}(window, document, jQuery));