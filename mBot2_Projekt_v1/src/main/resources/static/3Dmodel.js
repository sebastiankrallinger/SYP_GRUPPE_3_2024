// Ensure DOM content is fully loaded before executing script
/*document.addEventListener('DOMContentLoaded', function() {
    // Initialize Three.js scene
    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    const renderer = new THREE.WebGLRenderer();
    renderer.setSize(window.innerWidth * 0.7, window.innerHeight * 0.7);
    renderer.setClearColor(0xffffff);
    document.body.appendChild(renderer.domElement);

    // Add camera controls (optional)
    const controls = new THREE.OrbitControls(camera, renderer.domElement);

    // Load the .mtl file
    const mtlLoader = new THREE.MTLLoader();
    mtlLoader.load(
        '/bilder/bugatti/bugatti.mtl',
        function(materials) {
            materials.preload();
            // Load the .obj file
            const objLoader = new THREE.OBJLoader();
            objLoader.setMaterials(materials);
            objLoader.load(
                '/bilder/bugatti/bugatti.obj',
                function(object) {
                    scene.add(object);
                },
                function(xhr) {
                    console.log((xhr.loaded / xhr.total * 100) + '% loaded');
                },
                function(error) {
                    console.error('Failed to load .obj file:', error);
                }
            );
        },
        function(xhr) {
            console.log((xhr.loaded / xhr.total * 100) + '% loaded');
        },
        function(error) {
            console.error('Failed to load .mtl file:', error);
        }
    );

    // Set camera position
    camera.position.z = 5;

    // Render loop
    function animate() {
        requestAnimationFrame(animate);
        renderer.render(scene, camera);
    }
    animate();

    const overlay = document.getElementById('overlay');
    overlay.style.position = 'absolute';
    overlay.style.top = '50px'; // Adjust as needed
    overlay.style.left = '10px';
    overlay.style.backgroundColor = 'rgba(255, 255, 255, 0.5)';
    overlay.style.padding = '10px';
    overlay.style.borderRadius = '5px';
    overlay.style.zIndex = '1000';
});*/

document.addEventListener('DOMContentLoaded', function () {
    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    const renderer = new THREE.WebGLRenderer();
    renderer.setSize(window.innerWidth * 0.7, window.innerHeight * 0.7);
    renderer.setClearColor(0xffffff);
    document.body.appendChild(renderer.domElement);

    const controls = new THREE.OrbitControls(camera, renderer.domElement);

    // Geometry
    const geometry = new THREE.BoxGeometry(1, 1, 1);
    const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
    const cube = new THREE.Mesh(geometry, material);
    scene.add(cube);

    camera.position.z = 5;

    function animate() {
        requestAnimationFrame(animate);
        renderer.render(scene, camera);
    }
    animate();

    // Position the overlay
    const overlay = document.getElementById('overlay');
    overlay.style.position = 'absolute';
    overlay.style.top = '50px'; // Adjust as needed
    overlay.style.left = '10px';
    overlay.style.backgroundColor = 'rgba(255, 255, 255, 0.5)';
    overlay.style.padding = '10px';
    overlay.style.borderRadius = '5px';
    overlay.style.zIndex = '1000';
});