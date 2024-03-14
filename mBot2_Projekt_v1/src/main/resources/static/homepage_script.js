function handleClickPC(container) {
    // Code to execute when the container is clicked
    window.location.href = '/mBot';
    console.log("Container clicked!");
}

function handleClickPhone(container) {
    console.log("Container clicked!");
}

function handleHover(container) {
    // Code to execute when the container is hovered
    container.style.backgroundColor = "lightblue"; // Example: Change background color
}

function handleLeave(container) {
    // Code to execute when the mouse leaves the container
    container.style.backgroundColor = ""; // Reset background color
}