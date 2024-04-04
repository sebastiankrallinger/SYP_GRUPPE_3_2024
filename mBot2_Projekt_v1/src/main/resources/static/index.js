$(document).ready(function() {
    btnDrive();
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
}