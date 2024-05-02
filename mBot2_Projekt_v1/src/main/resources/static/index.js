$(document).ready(function() {
    btnDrive();
    btnSuicide();
});

function checkDevice() {
    var screenWidth = window.innerWidth;
    var pcContainer = document.getElementById("pcContainer");
    var phoneControls = document.getElementById("phoneControls");

    if (screenWidth < 768) {
        // Kleiner als 768px: wahrscheinlich ein Handy
        pcContainer.style.display = "none";
        phoneControls.style.display = "block";
    } else {
        // Größer oder gleich 768px: wahrscheinlich ein Desktop
        pcContainer.style.display = "block";
        phoneControls.style.display = "none";
    }
}

function connect(event) {
    event.stopPropagation();

    var clickedElement = event.target;
    if (clickedElement.tagName === "A") {
        var i = clickedElement.textContent;
        ip = i.split('-')
        document.getElementById("ipAdresse").innerText = i;
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

function sensorData(){
    fetch("/getSensordata")
        .then(response => response.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => console.error('Error:', error));
}

function updateSliderValue(value, id) {
    console.log(value);
    if (id=="myRange")
    {
        document.getElementById("sliderValue").textContent = value;
    }
    else if (id=="myRange2")
    {
        document.getElementById("sliderValue2").textContent = value;
    }

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/speedControl?speed=' + value, true);
    xhr.send();

    sensorData();
    setInterval(sensorData, 5000);
}