function checkDevice() {
    var screenWidth = window.innerWidth;
    var pcContainer = document.getElementById("pcContainer");
    var phoneControls = document.getElementById("phoneControls");
    var deviceTypeMessage = document.getElementById("deviceType");

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
window.onload = checkDevice;

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