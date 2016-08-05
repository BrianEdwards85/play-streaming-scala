var session = undefined;
var slide = 1;

function nextSlide() {
    slide++;
    var nextImage = "/assets/images/essentials_main_v1/Slide" + slide + ".jpg";
    $("#frame").attr("src",nextImage );
    $.ajax({url: "/slide/" + session, method: "PUT", data: nextImage, processData: false, contentType: "text/plain"});
}

function prevSlide() {
    slide--;
    var nextImage = "/assets/images/essentials_main_v1/Slide" + slide + ".jpg";
    $("#frame").attr("src",nextImage );
    $.ajax({url: "/slide/" + session, method: "PUT", data: nextImage, processData: false, contentType: "text/plain"});
}

function shareLink() {
    $("#link").html(window.location.href.replace("master","slave"));
    $("#frame").attr("href",window.location.href.replace("master","slave"));
}

$( document ).ready(function() {
    var pathname = window.location.href;
    console.log( "ready! " + pathname );
    var loc = pathname.indexOf("#");
    if(loc == -1){
        $.ajax({url: "/newSession", success: function (result) {
            session = result;
            location.replace(pathname + "#" + session);
            shareLink();
        }});
    } else {
        session = pathname.substr(loc+1)
        shareLink();
    }
    $("#next").click(nextSlide);
    $("#prev").click(prevSlide);
});



