(function(window, document, $){
    // configuration
    $app.router.config({ mode: 'hash'});

    // adding routes
    $app.router
        .add(/home/, function() {
            $('h3').html('HOME!!!');
        })
        .add(function() {
            console.log('default');
        }).check('/products/12/edit/22').listen();

    // forwarding
    $app.router.navigate('/home');

    console.log(window.location.hash);
}(window, document, jQuery));