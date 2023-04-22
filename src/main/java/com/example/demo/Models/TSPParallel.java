package com.example.demo.Models;
import java.util.concurrent.RecursiveTask;
public class TSPParallel {
    private static int[][] distances;
    private static int numCities;
    int[] shortestPath;
    public TSPParallel(int[][] distances) {
        this.distances = distances;
        numCities = distances.length;
    }
    private static class TSPSolver extends RecursiveTask<int[]> {
        private int[] cities;
        private boolean[] visited;
        public TSPSolver(int[] cities, boolean[] visited) {
            this.cities = cities;
            this.visited = visited;
        }

        @Override
        protected int[] compute() {
            if (cities.length == numCities) {
                return cities;
            }

            int[] shortestPath = null;

            for (int i = 0; i < numCities; i++) {
                if (!visited[i]) {
                    int city = i;
                    int[] newPath = new int[cities.length + 1];
                    System.arraycopy(cities, 0, newPath, 0, cities.length);
                    newPath[cities.length] = city;

                    boolean[] newVisited = new boolean[numCities];
                    System.arraycopy(visited, 0, newVisited, 0, numCities);
                    newVisited[city] = true;

                    TSPSolver solver = new TSPSolver(newPath, newVisited);
                    solver.fork();

                    int[] path = solver.join();
                    int pathDistance = calculateDistance(path);

                    if (shortestPath == null || pathDistance < calculateDistance(shortestPath)) {
                        shortestPath = path;
                    }
                }
            }
            return shortestPath;
        }

        private int calculateDistance(int[] path) {
            int distance = 0;
            for (int i = 0; i < path.length - 1; i++) {
                distance += distances[path[i]][path[i + 1]];
            }
            distance += distances[path[path.length - 1]][path[0]];
            return distance;
        }
    }

    public int[]  returnResult() {
        int[] cities = {0};
        boolean[] visited = new boolean[numCities];
        visited[0] = true;

        TSPSolver solver = new TSPSolver(cities, visited);
        int[] shortestPath = solver.compute();

        System.out.print("Shortest path: ");
        for (int i = 0; i < shortestPath.length; i++) {
            System.out.print(shortestPath[i] + " ");
        }
        return shortestPath;
    }
}