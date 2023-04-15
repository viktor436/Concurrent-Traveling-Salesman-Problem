package com.example.demo.Controllers;

import com.example.demo.Models.TSP;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Models.City;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TspController {

    @GetMapping("/")
    public String showTsp(Model model) {
        // Define the cities and their coordinates.
        List<City> cities = Arrays.asList(
                new City("New York", 20, 50),
                new City("Los Angeles", 50, 120),
                new City("Chicago", 220, 120),
                new City("Houston", 120, 20)
                //new City("Sofia", 20, 130)

        );

        // Generate a random route for the TSP.
        int[] bRoute = findBestRoute(cities);
        //List<Integer> currentRoute = generateRandomRoute(cities.size());
        List<Integer> currentRoute = Arrays.stream(bRoute).boxed().collect(Collectors.toList());


        model.addAttribute("cities", cities);
        model.addAttribute("currentRoute", currentRoute);
        return "tsp";
    }

    @PostMapping("/generateRoute")
    public String generateRoute(Model model) {
        // Define the cities and their coordinates.
        List<City> cities = Arrays.asList(
                new City("New York", 20, 50),
                new City("Los Angeles", 50, 120),
                new City("Chicago", 220, 120),
                new City("Houston", 120, 20)
                //new City("Sofia", 100, 30)
                );

        // Generate a new random route for the TSP.
        List<Integer> currentRoute = generateRandomRoute(cities.size());
        int[] bRoute = findBestRoute(cities);
        List<Integer> bestRoute = Arrays.stream(bRoute).boxed().collect(Collectors.toList());


        model.addAttribute("cities", cities);
        model.addAttribute("currentRoute", bestRoute);
        return "tsp :: map";
    }

    // Generate a random route for the TSP.
    private List<Integer> generateRandomRoute(int numCities) {
        List<Integer> route = new ArrayList<>();
        for (int i = 0; i < numCities; i++) {
            route.add(i);
        }

        Collections.shuffle(route);
        return route;
    }
    private int[] findBestRoute(List<City> cityList) {
        //List<Integer> route = new ArrayList<>();
        int[][] distances = calculateDistanceMatrix(cityList);
        //todo: convert points to distances
        TSP tsp = new TSP(distances);
        int[] bestRoute = tsp.solve();


        return bestRoute;
    }

    public static int[][] pointsToDistances(int[][] points) {
        int numPoints = points.length;
        int[][] distanceMatrix = new int[numPoints][numPoints];

        for (int i = 0; i < numPoints; i++) {
            for (int j = 0; j < numPoints; j++) {
                double deltaX = points[i][0] - points[j][0];
                double deltaY = points[i][1] - points[j][1];
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                distanceMatrix[i][j] = (int) Math.round(distance);
            }
        }

        return distanceMatrix;
    }

    public static int[][] calculateDistanceMatrix(List<City> cities) {
        int numCities = cities.size();
        int[][] distanceMatrix = new int[numCities][numCities];

        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                int deltaX = cities.get(i).getX() - cities.get(j).getX();
                int deltaY = cities.get(i).getY() - cities.get(j).getY();
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                distanceMatrix[i][j] = (int) Math.round(distance);
            }
        }

        return distanceMatrix;
    }


}

