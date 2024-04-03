function connect(event) {
    event.stopPropagation();

    var clickedElement = event.target;

    if (clickedElement.tagName === "A") {
        var ip = clickedElement.textContent;
        document.getElementById("selectedDevice").innerText = ip;
        var xhr = new XMLHttpRequest();
        xhr.open('Get', "/getDevice?ipAdresse=" + ip, true);
        xhr.send();
    }
}