package com.example.demo.Controllers;

import com.example.demo.Models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String showTsp(Model model) throws InterruptedException {
        if(cities.isEmpty()) {
            cities.add(new City("Sofia", 81, 185));
            cities.add(new City("Sopot", 240, 210));
            cities.add(new City("Montana", 90, 80));
//            cities.add(new City("Kozloduy", 180, 91));
//            cities.add(new City("Borovec", 130, 260));
//            cities.add(new City("Lom", 70, 60));
//            cities.add(new City("Burgas", 490, 240));
//            cities.add(new City("Varna", 520, 140));
//            cities.add(new City("Zlatograd", 300, 390));

        }
        currentRoute = findBestRoute(cities);

        model.addAttribute("cities", cities);
        model.addAttribute("currentRoute", currentRoute);
        return "index";
    }

    private List<Integer> generateRandomRoute(int numCities) {
        List<Integer> route = new ArrayList<>();
        for (int i = 0; i < numCities; i++) {
            route.add(i);
        }

        Collections.shuffle(route);
        return route;
    }
    private int[] findBestRoute(List<City> cityList) throws InterruptedException {
        List<Integer> route = new ArrayList<>();
        int[][] distances = calculateDistanceMatrix(cityList);
          TSPParallel tspSolver = new TSPParallel(distances);
          int[] res = tspSolver.returnResult();
        return res;
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

