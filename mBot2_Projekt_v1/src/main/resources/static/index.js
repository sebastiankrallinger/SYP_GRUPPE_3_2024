$(document).ready(function() {
    btnDrive();
    btnSuicide();
});

function connect(event) {
    event.stopPropagation();

    var clickedElement = event.target;

    if (clickedElement.tagName === "A") {
        var i = clickedElement.textContent;
        ip = i.split('-')
        document.getElementById("selectedDevice").innerText = i;
        var xhr = new XMLHttpRequest();
        xhr.open('Get', "/getDevice?ipAdresse=" + ip[0], true);
        xhr.send();
    }
}

function btnDrive(){
    $("#driveBtnU").click(function() {
        $.ajax({
            url: "/buttonControl",
            type: "POST",
            data: { direction: "UP" }
        });
    })
    $("#driveBtnD").click(function() {
        $.ajax({
            url: "/buttonControl",
            type: "POST",
            data: { direction: "DOWN" }
        });
    })
    $("#driveBtnL").click(function() {
        $.ajax({
            url: "/buttonControl",
            type: "POST",
            data: { direction: "LEFT" }
        });
    })
    $("#driveBtnR").click(function() {
        $.ajax({
            url: "/buttonControl",
            type: "POST",
            data: { direction: "RIGHT" }
        });
    })
    $("#driveBtnS").click(function() {
        $.ajax({
            url: "/buttonControl",
            type: "POST",
            data: { direction: "STOP" }
        });
    })
}

function btnSuicide(){
    $("#suicideOff").click(function() {
        $.ajax({
            url: "/suicidePrevention",
            type: "POST",
            data: { prevention: "OFF" }
        });
    })
    $("#suicideOn").click(function() {
        $.ajax({
            url: "/suicidePrevention",
            type: "POST",
            data: { prevention: "ON" }
        });
    })
}