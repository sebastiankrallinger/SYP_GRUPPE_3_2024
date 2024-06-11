var iframe = document.getElementById('sketchfab-iframe');
var client = new Sketchfab(iframe);

client.init(null, {
    success: function(api) {
        api.start();
        api.addEventListener('viewerready', function() {
            console.log('Viewer is ready');

            function updateModel() {
                var rotX = parseFloat(document.getElementById('rot_x').innerText) || 0;
                var rotY = parseFloat(document.getElementById('rot_y').innerText) || 0;
                var rotZ = parseFloat(document.getElementById('rot_z').innerText) || 0;

                var matrix = [
                    Math.cos(rotY) * Math.cos(rotZ), -Math.cos(rotY) * Math.sin(rotZ), Math.sin(rotY), 0,
                    Math.cos(rotX) * Math.sin(rotZ) + Math.sin(rotX) * Math.sin(rotY) * Math.cos(rotZ), Math.cos(rotX) * Math.cos(rotZ) - Math.sin(rotX) * Math.sin(rotY) * Math.sin(rotZ), -Math.sin(rotX) * Math.cos(rotY), 0,
                    Math.sin(rotX) * Math.sin(rotZ) - Math.cos(rotX) * Math.sin(rotY) * Math.cos(rotZ), Math.sin(rotX) * Math.cos(rotZ) + Math.cos(rotX) * Math.sin(rotY) * Math.sin(rotZ), Math.cos(rotX) * Math.cos(rotY), 0,
                    0, 0, 0, 1
                ];

                api.setMatrixTransform(matrix);
            }

            setInterval(updateModel, 1000); // Update every second
        });
    },
    error: function() {
        console.log('Viewer error');
    }
});