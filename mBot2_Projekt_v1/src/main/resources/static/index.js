let sock = new SockJS('http://localhost:8080/ws');

let client = Stomp.over(sock);

client.connect({}, (frame) => {
    //console.log("Frame is: " +frame);
    client.subscribe('/topic/sensorData', (payload) => {
        //console.log(payload);
        sensorData(JSON.parse(payload.body))
    });
});

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

function sensorData(data){
    console.log(data);
    document.getElementById("mbotid").innerText = data.mbotid;
    document.getElementById("yaw").innerText = data.yaw;
    document.getElementById("loudness").innerText = data.loudness;
    document.getElementById("rot_x").innerText = data.rot_x;
    document.getElementById("rot_y").innerText = data.rot_y;
    document.getElementById("gyro_y").innerText = data.gyro_y;
    document.getElementById("brightness").innerText = data.brightness;
    document.getElementById("acc_z").innerText = data.acc_z;
    document.getElementById("pitch").innerText = data.pitch;
    document.getElementById("shakeval").innerText = data.shakeval;
    document.getElementById("wave_speed").innerText = data.wave_speed;
    document.getElementById("roll").innerText = data.roll;
    document.getElementById("acc_x").innerText = data.acc_x;
    document.getElementById("wave_angle").innerText = data.wave_angle;
    document.getElementById("acc_y").innerText = data.acc_y;
    document.getElementById("gyro_x").innerText = data.gyro_x;
    document.getElementById("ultrasonic").innerText = data.ultrasonic;
    document.getElementById("gyro_z").innerText = data.gyro_z;
    document.getElementById("rot_z").innerText = data.rot_z;
    document.getElementById("speed").innerText = data.speed;
    document.getElementById("quad_rgb").innerText = data.quad_rgb;
    document.getElementById("mid_left").style.backgroundColor = "#" + data.quad_rgb[1].split("x")[1];
    document.getElementById("mid_right").style.backgroundColor = "#" + data.quad_rgb[2].split("x")[1];
    document.getElementById("out_left").style.backgroundColor = "#" + data.quad_rgb[0].split("x")[1];
    document.getElementById("out_right").style.backgroundColor = "#" + data.quad_rgb[3].split("x")[1];
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
}