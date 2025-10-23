// Oisin Gibson
// L00172671
// Updated: 23.10.2025
// Note: Dermots code/comments have been unaltered where possible

public class RebBlackConsoleTester {

    public static void main(String[] args) {
        // Week 3
        // Basic Test for Red Uncle Case (recolouring - no rotations)

        // Week 4
        // Basic Test for Black Uncle Case Right-Right case
        RedBlackTree<Integer> myTree = new RedBlackTree<Integer>();
        // Week 3
        // myTree.insert(10);
        // myTree.insert(5);
        // myTree.insert(15);
        // myTree.insert(20);
        // Node.toString() amended to output colour info
        myTree.preOrderTraversal();

        // Week 4
        myTree.insert(1);
        myTree.insert(2);
        myTree.insert(3); // Should trigger the RR case
        // Node.toString() amended to output colour info
        System.out.println("Pre-order traversal:");
        myTree.preOrderTraversal();

    }

}
