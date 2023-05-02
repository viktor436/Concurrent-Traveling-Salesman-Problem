package com.example.demo.Models;

import java.util.concurrent.*;

public class TspCdlParallel {
    private final int[][] distanceMatrix;
    private final CountDownLatch latch;
    private int[] bestPath;
    private int bestDistance = Integer.MAX_VALUE;

    public TspCdlParallel(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
        this.latch = new CountDownLatch(distanceMatrix.length);
    }

    public int[] solve() throws InterruptedException, ExecutionException {
        if (distanceMatrix.length == 3) {
            return new int[] {0, 1, 2, 0};
        }
        else {
            int numThreads = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            Future<int[]>[] futures = new Future[distanceMatrix.length];
            for (int i = 0; i < distanceMatrix.length; i++) {
                futures[i] = executor.submit(new TspTask(i));
            }
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            for (int i = 0; i < distanceMatrix.length; i++) {
                int[] path = futures[i].get();
                int distance = calculateDistance(path);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestPath = path.clone();
                }
            }
            return bestPath;
        }
    }

    private class TspTask implements Callable<int[]> {
        private final int startCity;

        public TspTask(int startCity) {
            this.startCity = startCity;
        }

        @Override
        public int[] call() {
            int[] path = new int[distanceMatrix.length];
            path[0] = startCity;
            boolean[] used = new boolean[distanceMatrix.length];
            used[startCity] = true;
            solveRecursively(path, used, 1);
            latch.countDown();
            return bestPath;
        }
    }

    private void solveRecursively(int[] path, boolean[] used, int depth) {
        if (depth == path.length) {
            int distance = calculateDistance(path);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestPath = path.clone();
            }
            return;
        }
        for (int i = 0; i < distanceMatrix.length; i++) {
            if (!used[i]) {
                used[i] = true;
                path[depth] = i;
                solveRecursively(path, used, depth + 1);
                used[i] = false;
            }
        }
    }

    public int calculateDistance(int[] path) {
        int distance = 0;
        for (int i = 0; i < path.length - 1; i++) {
            distance += distanceMatrix[path[i]][path[i+1]];
        }
        distance += distanceMatrix[path[path.length-1]][path[0]];
        return distance;
    }

}

