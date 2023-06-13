# Concurrent implementation of the Traveling salesman problem

This is an university Spring Boot project that solves the Traveling Salesman Problem using concurrent algorithms.

## Website

You can try the project at:
https://tspparallel.azurewebsites.net/


## Project Structure
The project consists of the following:

TspController.java: This is the main controller class that handles the HTTP requests and responses. It contains the following methods:
addCity: Handles the POST request to add a new city to the problem.
showTsp: Handles the GET request to display the Traveling Salesman Problem solution on the web page.

City.java: This class represents a city and contains information about its name, x-coordinate, and y-coordinate.

TSPParallel.java: This class implements a parallel algorithm to solve the Traveling Salesman Problem. It uses the Fork/Join framework to divide the problem into smaller subproblems and solve them concurrently.

index.html: This HTML file is the Thymeleaf template that defines the structure and content of the web page. It displays a canvas with a map, allows users to add cities, and shows information about the problem and its solution.

tsp.js: This JavaScript file contains code that interacts with the HTML canvas element to draw the cities and the shortest route.

## Running the Project
To run the project, follow these steps:

1. Make sure you have Java and Maven installed on your system.
2. Clone the project repository to your local machine.
3. Open a terminal or command prompt and navigate to the project's root directory.
4. Run the following command to build the project: mvn clean install.
5. After the build is successful, run the following command to start the Spring Boot application: mvn spring-boot:run.
6. Open a web browser and visit http://localhost:8080 to see the Traveling Salesman Problem web page.
You can add cities by filling in the name, x-coordinate, and y-coordinate fields and clicking the "Add City" button.

The page will display the added cities, as well as the current shortest route between them.
Note: The project uses Thymeleaf as the template engine and assumes you have the required dependencies available. If any issues occur, please check the project's dependencies and configurations.

## License
This project is licensed under the MIT License. Feel free to use and modify it according to your needs.
