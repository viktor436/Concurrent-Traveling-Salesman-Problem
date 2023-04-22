package com.example.demo.Controllers;

import com.example.demo.Models.*;
//import com.example.demo.Models.TspSolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


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
            cities.add(new City("Burgas", 180, 91));
            cities.add(new City("Borovec", 130, 260));
            cities.add(new City("Teteven", 70, 60));

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


        //new----------------------------------------------------
//        int[] bestPath = new int[distances.length];// PROBLEMO
//        int bestDistance = Integer.MAX_VALUE;//
//        int numCities = distances.length;
//        List<Integer> cities = new ArrayList<>();
//        for (int i = 1; i < numCities; i++) {
//            cities.add(i);
//        }
//
//        ExecutorService executor = Executors.newFixedThreadPool(numCities - 1);
//        //TSPParallelSolution tsp = new TSPParallelSolution();
//        for (int startCity = 0; startCity < numCities; startCity++) {
//            List<Integer> path = new ArrayList<>();
//            path.add(startCity);
//            executor.execute(new TSPWorker(cities, path, bestPath, bestDistance));
//        }
//        executor.shutdown();
//        executor.awaitTermination(1, TimeUnit.MINUTES);
//        System.out.println("Best path: " + Arrays.toString(bestPath));
        //new-----------------------------------------------------

        //old keep
        //TSP tsp = new TSP(distances);
        //TSPSolver tsp = new TSPSolver(distances);
        //int[] bestRoute = tsp2.run();
        //old keep
//        TSPParallelSolution tspp = new TSPParallelSolution(distances);
//        int[] res =tspp.returnResult();
          TSPParallel ysppp = new TSPParallel(distances);
          int[] res = ysppp.returnResult();
        //ysppp.main();
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

