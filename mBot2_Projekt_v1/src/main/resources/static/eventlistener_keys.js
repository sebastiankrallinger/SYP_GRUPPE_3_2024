let b=true;
let currentdir="";
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
function sendXY(joystick_coordinates) {
    //console.log(joystick_coordinates);
    let xy = [];

    if (joystick_coordinates.split(":").length > 1) {
        xy = joystick_coordinates.split(/[:,}]/);
        console.log(xy[1] + " ----- " + xy[3]);
    }


    if (joystick_coordinates !== "") {
        if (xy[1] > -0.5 && xy[1] < 0.5 && xy[3] < -0.7) {
            if(currentdir!="UP") {
                currentdir = "UP";
                sendDirection('UP');
            }
        }
        else if (xy[1] > -0.5 && xy[1] < 0.5 && xy[3] > 0.7) {
            if(currentdir!="DOWN"){
                currentdir="DOWN";
                sendDirection('DOWN');
            }
        }
        else if (xy[3] > -0.5 && xy[3] < 0.5 && xy[1] < -0.7) {
            if(currentdir!="LEFT"){
                currentdir="LEFT";
                sendDirection('LEFT');
            }
        }
        else if (xy[3] > 0 && xy[3] < 0.7 && xy[1] < -0.7) {
            if(currentdir!="LEFT_B"){
                currentdir="LEFT_B";
                sendDirection('LEFT_B');
            }
        }
        else if (xy[3] > -0.5 && xy[3] < 0.5 && xy[1] > 0.7) {
            if(currentdir!="RIGHT"){
                currentdir="RIGHT";
                sendDirection('RIGHT');
            }
        }
        else if (xy[3] > 0 && xy[3] < 0.7 && xy[1] > 0.7) {
            if(currentdir!="RIGHT_B"){
                currentdir="RIGHT_B";
                sendDirection('RIGHT_B');
            }
        }

        else if (xy[1] === "0" && xy[3] === "0") {
            if(currentdir!="STOP"){
                currentdir="STOP";
                sendDirection('STOP');
            }
        }
    }
}