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

@Controller
public class TspController {

    @GetMapping("/")
    public String showTsp(Model model) {
        // Define the cities and their coordinates.
        List<City> cities = Arrays.asList(
                new City("New York", 20, 50),
                new City("Los Angeles", 50, 120),
                new City("Chicago", 220, 120),
                new City("Houston", 120, 20),
                new City("Sofia", 20, 130)

        );

        // Generate a random route for the TSP.
        List<Integer> currentRoute = generateRandomRoute(cities.size());

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
                new City("Houston", 120, 20),
                new City("Sofia", 100, 30)
                );

        // Generate a new random route for the TSP.
        List<Integer> currentRoute = generateRandomRoute(cities.size());

        model.addAttribute("cities", cities);
        model.addAttribute("currentRoute", currentRoute);
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
    private List<Integer> findBestRoute(int numCities) {
        List<Integer> route = new ArrayList<>();
        //todo: convert points to distances
        TSP tsp = new TSP(distances);
        long start = System.currentTimeMillis();
        int[] bestRoute = tsp.solve();


        return route;
    }
}

