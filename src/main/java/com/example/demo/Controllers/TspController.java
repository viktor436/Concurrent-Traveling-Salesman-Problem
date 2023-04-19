package com.example.demo.Controllers;

import com.example.demo.Models.TSP;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Models.City;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class TspController {
    private List<City> cities = new ArrayList<>();
    private int[] currentRoute = {};

    @PostMapping("/addCity")
    public String addCity(@RequestParam String name, @RequestParam int x, @RequestParam int y) {
        cities.add(new City(name, x, y));
        return "redirect:/";
    }
    @GetMapping("/")
    public String showTsp(Model model) {
        if(cities.isEmpty()) {
            cities.add(new City("Sofia", 30, 70));
            cities.add(new City("Sopot", 90, 80));
            cities.add(new City("Montana", 30, 30));
            //cities.add(new City("Burgas", 180, 90));
            //cities.add(new City("Borovec", 50, 100));
            // cities.add(new City("Teteven", 70, 60));
        }
        int[] bRoute = findBestRoute(cities);
        //List<Integer> currentRoute = generateRandomRoute(cities.size());
        //List<Integer> currentRoute = Arrays.stream(bRoute).boxed().collect(Collectors.toList());
        currentRoute = bRoute;

        model.addAttribute("cities", cities);
        model.addAttribute("currentRoute", currentRoute);
        return "index";
    }

//    @PostMapping("/generateRoute")
//    public String generateRoute(Model model) {
//        List<City> cities = Arrays.asList(
//                new City("New York", 20, 50),
//                new City("Los Angeles", 50, 120),
//                new City("Chicago", 220, 120),
//                new City("Houston", 120, 20)
//                //new City("Sofia", 100, 30)
//                );
//
//        List<Integer> currentRoute = generateRandomRoute(cities.size());
//        int[] bRoute = findBestRoute(cities);
//        List<Integer> bestRoute = Arrays.stream(bRoute).boxed().collect(Collectors.toList());
//
//
//        model.addAttribute("cities", cities);
//        model.addAttribute("currentRoute", bestRoute);
//        return "tsp :: map";
//    }

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
        TSP tsp = new TSP(distances);
        int[] bestRoute = tsp.solve();


        return bestRoute;
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

