// Oisin Gibson
// L00172671
// Updated: 23.10.2025
// Note: Dermots code/comments have been unaltered where possible

public class BinaryTreeTester1 {

    public static void main(String[] args) {
        BinarySearchTree<Integer> myTree = new BinarySearchTree<Integer>();

        myTree.insert(40);// Note the use of primitive ints - they are auto-boxed into Integer objects.
        myTree.insert(32);
        myTree.insert(37);
        myTree.insert(34);
        myTree.insert(26);
        myTree.insert(29);
        myTree.insert(18);
        myTree.insert(20);
        myTree.insert(10);
        myTree.insert(49);
        myTree.insert(60);
        myTree.insert(70);
        myTree.insert(80);
        myTree.insert(75);
        myTree.insert(55);

        System.out.println("In-order Traversal:");
        myTree.inOrderTraversal();
        System.out.println();

        System.out.println("Pre-order Traversal:");
        myTree.preOrderTraversal();
        System.out.println();

        System.out.println("Post-order Traversal:");
        myTree.postOrderTraversal();
        System.out.println();

        System.out.println("Tree contains " + myTree.countNodes() + " nodes");
        System.out.println();

        // Test findMinimum() - should return the smallest value in the tree
        System.out.println("Find Minimum: " + myTree.findMinimum());

        // Test findMaximum() - should return the largest value in the tree
        System.out.println("Find Maximum: " + myTree.findMaximum());

        // Week 2 Tester
        System.out.println();

        System.out.println("Before Left Rotation (In-order):");
        myTree.inOrderTraversal();

        myTree.rotateLeft();

        System.out.println();

        System.out.println("After Left Rotation (In-order):");
        myTree.inOrderTraversal();

        myTree.rotateRight();

        System.out.println();

        System.out.println("After Right Rotation (In-order):");
        myTree.inOrderTraversal();

    }

}