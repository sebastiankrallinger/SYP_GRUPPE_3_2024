let b = true;
document.addEventListener('keydown', function(event) {
    if (event.key === 'ArrowLeft' || event.key === 'a' || event.key === 'A') {
        if(b==true) {
            sendDirection('LEFT');
            b = false;
        }
    }
    else if (event.key === 'ArrowRight' || event.key === 'd' || event.key === 'D') {
        if(b==true) {
            sendDirection('RIGHT');
            b = false;
        }
    }
    else if (event.key === 'ArrowUp' || event.key === 'w' || event.key === 'W') {
        if(b==true) {
            sendDirection('UP');
            b = false;
        }
    }
    else if (event.key === 'ArrowDown' || event.key === 's' || event.key === 'S') {
        if(b==true) {
            sendDirection('DOWN');
            b = false;
        }
    }
});
document.addEventListener('keyup', function(event) {
    if (event.key === 'ArrowUp' || event.key === 'ArrowDown' || event.key === 'ArrowLeft' || event.key === 'ArrowRight' || event.key === 'a' || event.key === 'd' || event.key === 'w' || event.key === 's' || event.key === 'A' || event.key === 'D' || event.key === 'W' || event.key === 'S') {
        b = true;
        sendDirection('STOP');
    }
});
function sendDirection(direction) {
    // AJAX-Anfrage an den Controller senden
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/arrowControl?direction=' + direction, true);
    xhr.send();
}