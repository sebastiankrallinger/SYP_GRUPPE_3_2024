function checkDevice() {

    var screenWidth = window.innerWidth;
    var pcContainer = document.getElementById("pcContainer");
    var phoneControls = document.getElementById("phoneControls");
    var deviceTypeMessage = document.getElementById("deviceType");

    if (screenWidth < 768) {
        // Kleiner als 768px: wahrscheinlich ein Handy
        pcContainer.style.display = "none";
        phoneControls.style.display = "block";
        deviceTypeMessage.innerHTML = "Du besuchst die Seite von einem Handy.";
    } else {
        // Größer oder gleich 768px: wahrscheinlich ein Desktop
        pcContainer.style.display = "block";
        phoneControls.style.display = "none";
        deviceTypeMessage.innerHTML = "Du besuchst die Seite von einem Desktop-Computer.";
    }
}
window.onload = checkDevice;