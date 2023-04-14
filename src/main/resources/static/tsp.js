// Define the cities and their coordinates.
// const cities = [
//     { name: 'New York', x: 20, y: 50 },
//     { name: 'Los Angeles', x: 50, y: 120 },
//     { name: 'Chicago', x: 220, y: 120 },
//     { name: 'Houston', x: 120, y: 20 },
//     { name: 'Phoenix', x: 200, y: 50 },
// ];
//
// // Define the function to draw the map and the route.
// function drawMap(route) {
//     const canvasElement = document.getElementById('map');
//     const ctx = canvasElement.getContext('2d');
//
//     // Clear the canvas.
//     ctx.clearRect(0, 0, canvasElement.width, canvasElement.height);
//
//     // Add the markers for the cities.
//     cities.forEach(city => {
//         ctx.beginPath();
//         ctx.arc(city.x, city.y, 5, 0, 2 * Math.PI);
//         ctx.fillStyle = '#FF0000';
//         ctx.fill();
//         ctx.closePath();
//         ctx.font = 'bold 12px Arial';
//         ctx.fillText(city.name, city.x + 10, city.y + 5);
//     });
//
//     // Add the lines for the route.
//     ctx.beginPath();
//     ctx.moveTo(cities[route[0]].x, cities[route[0]].y);
//     for (let i = 1; i < route.length; i++) {
//         ctx.lineTo(cities[route[i]].x, cities[route[i]].y);
//     }
//     ctx.closePath();
//     ctx.strokeStyle = '#0000FF';
//     ctx.stroke();
// }
//
// // Generate a random route for the TSP.
// function generateRandomRoute() {
//     const route = [...cities.keys()];
//     for (let i = route.length - 1; i > 0; i--) {
//         const j = Math.floor(Math.random() * (i + 1));
//         [route[i], route[j]] = [route[j], route[i]];
//     }
//     return route;
// }
//
// // Set up the initial route and draw the map.
// let currentRoute = generateRandomRoute();
// let staticRoute = [1,2,3,4];
// drawMap(staticRoute);
//
// // Add a button to generate a new route.
// const button = document.createElement('button');
// button.textContent = 'Generate New Route';
// button.addEventListener('click', () => {
//     currentRoute = generateRandomRoute();
//     drawMap(currentRoute);
// });
// document.body.appendChild(button);
