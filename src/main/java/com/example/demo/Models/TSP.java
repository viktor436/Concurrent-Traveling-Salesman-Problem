package com.example.demo.Models;
import java.util.concurrent.Phaser;

public class TSP {
    private int[][] distances;
    private int numCities; //брой градове
    private int[] bestRoute;
    private int bestDistance = Integer.MAX_VALUE;
    private Phaser phaser = new Phaser(0);

    public TSP(int[][] distances) {
        this.distances = distances;
        this.numCities = distances.length;
    }

    public int[] solve() {
        int[] initialRoute = new int[numCities];
        for (int i = 0; i < numCities; i++) {
            initialRoute[i] = i;
        }
        bestRoute = initialRoute;
        bestDistance = computeDistance(initialRoute);

        phaser.bulkRegister(numCities - 1);
        for (int i = 1; i < numCities; i++) {
            new Thread(new Solver(i, initialRoute)).start();
        }

        phaser.arriveAndAwaitAdvance();

        System.out.println("Best distance: " + bestDistance);
        return bestRoute;
    }

    private class Solver implements Runnable {
        private int level;
        private int[] route;

        public Solver(int level, int[] route) {
            this.level = level;
            this.route = route;
        }

        public void run() {
            branchAndBound(route, level);
            if (!phaser.isTerminated()) {
                phaser.arrive(); //error
            }
        }
    }

    private void branchAndBound(int[] route, int level) {
        if (level == numCities - 1) {
            int distance = computeDistance(route);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestRoute = route.clone();
            }
        } else {
            for (int i = level; i < numCities; i++) {
                swap(route, level, i);
                int lowerBound = computeLowerBound(route, level);
                if (lowerBound < bestDistance) {
                    branchAndBound(route, level + 1);
                }
                swap(route, level, i);
            }
        }
    }

    private void swap(int[] route, int i, int j) {
        int temp = route[i];
        route[i] = route[j];
        route[j] = temp;
    }

    private int computeLowerBound(int[] route, int level) {
        int lowerBound = computeDistance(route, level);
        for (int i = level + 1; i < numCities; i++) {
            lowerBound += computeMinDistance(route, i);
        }
        return lowerBound;
    }

    private int computeDistance(int[] route, int level) {
        int distance = 0;
        for (int i = 0; i < level; i++) {
            distance += distances[route[i]][route[i + 1]];
        }
        distance += distances[route[level]][route[0]];
        return distance;
    }

    private int computeDistance(int[] route) {
        return computeDistance(route, numCities - 1);
    }

    private int computeMinDistance(int[] route, int i) {
        int minDistance = Integer.MAX_VALUE;
        for (int j = i + 1; j < numCities; j++) {
            int distance = distances[route[i]][route[j]];
            if (distance < minDistance) {
                minDistance = distance;
            }
        }
        return minDistance;
    }
}
