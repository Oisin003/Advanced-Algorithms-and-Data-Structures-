// https://www.geeksforgeeks.org/dsa/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/

// Oisin Gibson 
// L00172671
// 04-11-2025

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class KruskalsAlgorithmWeek7Lab {
    public static int kruskalsMST(int V, int[][] edges, int[] numbers) {

        // Sort all edges based on weight
        Arrays.sort(edges, Comparator.comparingInt(e -> e[2]));

        // Traverse edges in sorted order
        DSU dsu = new DSU(V);// Create DSU
        int cost = 0, count = 0;

        System.out.println("MST Edges:");
        for (int[] e : edges) {
            int x = e[0], y = e[1], w = e[2];

            // Make sure that there is no cycle
            if (dsu.find(x) != dsu.find(y)) {
                dsu.union(x, y);
                cost += w;
                System.out.println(numbers[x] + " -- " + numbers[y] + " == " + w);
                if (++count == V - 1)
                    break;
            }
        }
        return cost;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter numbers separated by spaces: ");
        String input = scanner.nextLine();
        String[] parts = input.split("\\s+");
        int[] numbers = new int[parts.length];
        // Parse input numbers
        for (int i = 0; i < parts.length; i++) {
            numbers[i] = Integer.parseInt(parts[i]);
        }

        // Create edges from numbers (weight = absolute difference)
        int V = numbers.length;
        int edgeCount = 0;
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                edgeCount++;
            }
        }
        // Initialize edges array
        int[][] edges = new int[edgeCount][3];
        int index = 0;
        // Populate edges array
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                edges[index][0] = i;
                edges[index][1] = j;
                edges[index][2] = Math.abs(numbers[i] - numbers[j]);
                index++;
            }
        }

        System.out.println("Minimum Spanning Tree cost: " + kruskalsMST(V, edges, numbers));
        scanner.close();
    }
}

// Disjoint set data structure
class DSU {
    private int[] parent, rank;

    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int i) {
        if (parent[i] != i) {
            parent[i] = find(parent[i]);
        }
        return parent[i];
    }

    public void union(int x, int y) {
        int s1 = find(x);
        int s2 = find(y);
        if (s1 != s2) {
            if (rank[s1] < rank[s2]) {
                parent[s1] = s2;
            } else if (rank[s1] > rank[s2]) {
                parent[s2] = s1;
            } else {
                parent[s2] = s1;
                rank[s1]++;
            }
        }
    }
}