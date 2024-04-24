 // Disable scrolling with arrow keys
    window.addEventListener("keydown", function(e) {
    // Check if arrow key is pressed
    if(["ArrowUp", "ArrowDown", "ArrowLeft", "ArrowRight"].includes(e.key)) {
    // Prevent the default behavior
    e.preventDefault();
}
});