package com.example.demo.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TSPParallelSolution {

    // Distance matrix for the cities
//    private static final int[][] distances = {
//            {0, 10, 15, 20},
//            {10, 0, 35, 25},
//            {15, 35, 0, 30},
//            {20, 25, 30, 0}
//    };
    private static int[][] distances;

    private static int[] bestPath;
    private static int bestDistance = Integer.MAX_VALUE;

    public TSPParallelSolution(int[][] distances) {
        this.distances = distances;
    }

    public static int[] returnResult() throws InterruptedException {
        int numCities = distances.length;
        List<Integer> cities = new ArrayList<>();
        for (int i = 1; i < numCities; i++) {
            cities.add(i);
        }

        ExecutorService executor = Executors.newFixedThreadPool(numCities - 1);
        for (int startCity = 0; startCity < numCities; startCity++) {
            List<Integer> path = new ArrayList<>();
            path.add(startCity);
            executor.execute(new TSPWorker(cities, path));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Best path: " + Arrays.toString(bestPath));
        return bestPath;
    }

    private static class TSPWorker implements Runnable {
        private final List<Integer> remainingCities;
        private final List<Integer> pathSoFar;

        public TSPWorker(List<Integer> remainingCities, List<Integer> pathSoFar) {
            this.remainingCities = remainingCities;
            this.pathSoFar = pathSoFar;
        }

        @Override
        public void run() {
            if (remainingCities.isEmpty()) {
                // All cities visited, check if this path is better
                int distance = calculateDistance(pathSoFar);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestPath = pathSoFar.stream().mapToInt(i -> i).toArray();
                }
            } else {
                // Visit all remaining cities
                for (int i = 0; i < remainingCities.size(); i++) {
                    int nextCity = remainingCities.get(i);
                    List<Integer> newRemainingCities = new ArrayList<>(remainingCities);
                    newRemainingCities.remove(i);
                    List<Integer> newPathSoFar = new ArrayList<>(pathSoFar);
                    newPathSoFar.add(nextCity);
                    TSPWorker worker = new TSPWorker(newRemainingCities, newPathSoFar);
                    worker.run();
                }
            }
        }

        private int calculateDistance(List<Integer> path) {
            int distance = 0;
            int prevCity = path.get(path.size() - 1);
            for (int i = 0; i < path.size(); i++) {
                int nextCity = path.get(i);
                distance += distances[prevCity][nextCity];
                prevCity = nextCity;
            }
            return distance;
        }
    }
}
