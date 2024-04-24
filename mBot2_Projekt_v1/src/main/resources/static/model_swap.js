function toggleVisibility() {
    const imageContainer = document.querySelector('.custom-image-container');
    const threeContainer = document.querySelector('.container3D');

    if (imageContainer.style.display === 'none') {
        imageContainer.style.display = 'block';
        threeContainer.style.display = 'none';
    } else {
        imageContainer.style.display = 'none';
        threeContainer.style.display = 'block';
    }
}