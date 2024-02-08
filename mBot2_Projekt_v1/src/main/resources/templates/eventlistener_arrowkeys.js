//Überwachung der Pfeiltasten
document.addEventListener('keydown', function(event) {
    switch(event.key) {
        case 'ArrowUp':
            sendDirection('UP');
            break;
        case 'ArrowDown':
            sendDirection('DOWN');
            break;
        case 'ArrowLeft':
            sendDirection('LEFT');
            break;
        case 'ArrowRight':
            sendDirection('RIGHT');
            break;
    }
});

document.addEventListener('keyup', function(event) {
    if (event.key === 'ArrowUp' || event.key === 'ArrowDown' || event.key === 'ArrowLeft' || event.key === 'ArrowRight') {
        sendDirection('STOP');
    }
});

//Befehl an Funktion im Controller übergeben
function sendDirection(direction) {
    // AJAX-Anfrage an den Controller senden
    var xhr = new XMLHttpRequest();
    xhr.open('Post', '/arrowControl?direction=' + direction, true);
    xhr.send();
}
