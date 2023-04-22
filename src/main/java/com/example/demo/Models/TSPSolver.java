package com.example.demo.Models;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class TSPSolver {
    private int[][] graph;
    private int[] tour;
    private int n;
    private int minDist;
    private CountDownLatch latch;

    public TSPSolver(int[][] graph) {
        this.graph = graph;
        this.n = graph.length;
        this.tour = new int[n];
        this.minDist = Integer.MAX_VALUE;
        this.latch = new CountDownLatch(n);
    }

    public int[] solve() throws InterruptedException {
        Thread[] threads = new Thread[n];
        for (int i = 0; i < n; i++) {
            threads[i] = new Thread(new Solver(i, 0));
            threads[i].start();
        }
        latch.await();
        int[] route = new int[n];
        for (int i = 0; i < n; i++) {
            route[i] = tour[i];
        }
        System.out.println(minDist);
        return route;
    }

    private class Solver implements Runnable {
        private int start;
        private int end;

        public Solver(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void run() {
            int[] path = new int[n];
            boolean[] visited = new boolean[n];
            path[0] = start;
            visited[start] = true;
            dfs(path, visited, 1, start, 0);
            latch.countDown();
        }

        private void dfs(int[] path, boolean[] visited, int depth, int curr, int dist) {
            if (depth == n) {
                if (graph[curr][end] != 0 && dist + graph[curr][end] < minDist) {
                    minDist = dist + graph[curr][end];
                    System.arraycopy(path, 0, tour, 0, n);
                }
                return;
            }
            for (int i = 0; i < n; i++) {
                if (!visited[i] && graph[curr][i] != 0) {
                    path[depth] = i;
                    visited[i] = true;
                    dfs(path, visited, depth + 1, i, dist + graph[curr][i]);
                    visited[i] = false;
                }
            }
        }
    }
}





//import java.util.Arrays;
//import java.util.concurrent.CountDownLatch;
//
//public class TspSolver {
//    private final int n;
//    private final int[][] distance;
//    private final int numThreads;
//    private int[] bestPath;
//    private int bestCost;
//
//    public TspSolver(int[][] distance, int numThreads) {
//        this.n = distance.length;
//        this.distance = distance;
//        this.numThreads = numThreads;
//        this.bestPath = null;
//        this.bestCost = Integer.MAX_VALUE;
//    }
//
//    public int[] solve() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(numThreads);
//        int batchSize = (int) Math.ceil(n / (double) numThreads);
//        for (int i = 0; i < numThreads; i++) {
//            int start = i * batchSize;
//            int end = Math.min(start + batchSize, n);
//            new Thread(new TspTask(start, end, latch)).start();
//        }
//        latch.await();
//        return bestPath;
//    }
//
//    private class TspTask implements Runnable {
//        private final int start;
//        private final int end;
//        private final CountDownLatch latch;
//
//        public TspTask(int start, int end, CountDownLatch latch) {
//            this.start = start;
//            this.end = end;
//            this.latch = latch;
//        }
//
//        @Override
//        public void run() {
//            int[] path = new int[n];
//            path[0] = start;
//            boolean[] visited = new boolean[n];
//            visited[start] = true;
//            permute(path, visited, 1);
//            latch.countDown();
//        }
//
//        private void permute(int[] path, boolean[] visited, int depth) {
//            if (depth == end - start) {
//                int cost = computeCost(path);
//                if (cost < bestCost) {
//                    bestCost = cost;
//                    bestPath = Arrays.copyOf(path, n);
//                }
//                return;
//            }
//            for (int i = start; i < end; i++) {
//                if (!visited[i]) {
//                    path[depth] = i;
//                    visited[i] = true;
//                    permute(path, visited, depth + 1);
//                    visited[i] = false;
//                }
//            }
//        }
//
//        private int computeCost(int[] path) {
//            int cost = 0;
//            for (int i = 0; i < n - 1; i++) {
//                cost += distance[path[i]][path[i + 1]];
//            }
//            cost += distance[path[n - 1]][path[0]];
//            return cost;
//        }
//    }
//}
