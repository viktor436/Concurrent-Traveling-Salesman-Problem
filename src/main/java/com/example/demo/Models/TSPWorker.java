//package com.example.demo.Models;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TSPWorker implements Runnable {
//    private static final int[][] distances = {
//            {0, 162, 105},
//            {162, 0, 198},
//            {105, 198, 0},
//    };
//    private final List<Integer> remainingCities;
//    private final List<Integer> pathSoFar;
//    private static int[] bestPath;
//    private static int bestDistance = Integer.MAX_VALUE;
//    public TSPWorker(List<Integer> remainingCities, List<Integer> pathSoFar, int[] bestPath, int bestDistance) {
//        this.remainingCities = remainingCities;
//        this.pathSoFar = pathSoFar;
//        this.bestPath = bestPath;
//        this.bestDistance = bestDistance;
//    }
//
//    @Override
//    public void run() {
//        if (remainingCities.isEmpty()) {
//            // All cities visited, check if this path is better
//            int distance = calculateDistance(pathSoFar);
//            if (distance < bestDistance) {
//                bestDistance = distance;
//                bestPath = pathSoFar.stream().mapToInt(i -> i).toArray();
//            }
//        } else {
//            // Visit all remaining cities
//            for (int i = 0; i < remainingCities.size(); i++) {
//                int nextCity = remainingCities.get(i);
//                List<Integer> newRemainingCities = new ArrayList<>(remainingCities);
//                newRemainingCities.remove(i);
//                List<Integer> newPathSoFar = new ArrayList<>(pathSoFar);
//                newPathSoFar.add(nextCity);
//                TSPWorker worker = new TSPWorker(newRemainingCities, newPathSoFar, bestPath, bestDistance);
//                worker.run();
//            }
//        }
//    }
//
//    private int calculateDistance(List<Integer> path) {
//        int distance = 0;
//        int prevCity = path.get(path.size() - 1);
//        for (int i = 0; i < path.size(); i++) {
//            int nextCity = path.get(i);
//            distance += distances[prevCity][nextCity];
//            prevCity = nextCity;
//        }
//        return distance;
//    }
//}
