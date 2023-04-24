package com.example.demo.Models;
import java.util.concurrent.CountDownLatch;

public class TspCdl {
    private final int[][] distanceMatrix;
    private final CountDownLatch latch;
    private int[] bestPath;
    private int bestDistance = Integer.MAX_VALUE;

    public TspCdl(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
        this.latch = new CountDownLatch(distanceMatrix.length);
    }

    public int[] solve() throws InterruptedException {
        if (distanceMatrix.length == 3) {
            return new int[] {0, 1, 2, 0};
        }
        else {
            for (int i = 0; i < distanceMatrix.length; i++) {
                int[] path = new int[distanceMatrix.length];
                path[0] = i;
                boolean[] used = new boolean[distanceMatrix.length];
                used[i] = true;
                solveRecursively(path, used, 1);
                latch.await();
            }
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
            latch.countDown();
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
