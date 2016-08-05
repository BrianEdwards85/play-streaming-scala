var session = undefined;

$( document ).ready(function() {
    var pathname = window.location.href;
    console.log( "ready! " + pathname );
    var loc = pathname.indexOf("#");
    if(loc != -1 && !!window.EventSource) {
        session = pathname.substr(loc+1)
        var stringSource = new EventSource("/slide/" + session);
        stringSource.addEventListener('message', function(e) {
            $("#frame").attr("src",e.data );
        });
    }
});



