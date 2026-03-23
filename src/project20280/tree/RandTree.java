package project20280.tree;

import project20280.tree.LinkedBinaryTree;
import java.io.FileWriter;
import java.io.IOException;

public class RandTree {

    public static void main(String[] args) {
        int step = 50;          // step in n
        int maxSize = 5000;     // max n
        int trials = 100;       // number of random trees per size

        try (FileWriter writer = new FileWriter("tree_heights1.csv")) {
            writer.write("n,average_height\n"); // CSV header

            for (int n = 50; n <= maxSize; n += step) {
                double sumHeights = 0;

                for (int t = 0; t < trials; t++) {
                    LinkedBinaryTree<Integer> tree = LinkedBinaryTree.makeRandom(n);
                    sumHeights += tree.height();  // uses the height() method in LinkedBinaryTree
                }

                double avgHeight = sumHeights / trials;
                System.out.printf("n = %d, avg height = %.2f%n", n, avgHeight);

                // write to CSV
                writer.write(n + "," + avgHeight + "\n");
            }

            System.out.println("Simulation complete. Results saved to tree_heights.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}