package com.example.demo.Models.No;

import java.util.concurrent.CountDownLatch;
//note: not optimal solution
public class TSPCDL {
    private int[][] graph;
    private int[] tour;
    private int n;
    private int minDist;
    private CountDownLatch latch;

    public TSPCDL(int[][] graph) {
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
