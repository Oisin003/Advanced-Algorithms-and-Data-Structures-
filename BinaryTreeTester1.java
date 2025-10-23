// Oisin Gibson
// L00172671
// Updated: 23.10.2025
// Note: Dermots code/comments have been unaltered where possible

//package com.adsg.tree.tester;

//import com.adsg.tree.BinarySearchTree;
//import com.misc.Person;

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
        /*
         * BinarySearchTree<String> myTree = new BinarySearchTree<String>();
         * myTree.insert("adc");
         * myTree.insert("qwe");
         * myTree.insert("wer");
         * myTree.insert("ert");
         * myTree.insert("rty");
         * myTree.insert("tyu");
         * myTree.insert("yui");
         * myTree.insert("uio");
         * myTree.insert("iop");
         */
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // BinarySearchTree<Person> myTree = new BinarySearchTree<Person>();
        // myTree.insert(new Person("Amelia", "Quinn", 20));
        // myTree.insert(new Person("Olivia", "McLoone", 12));
        // myTree.insert(new Person("Emily", "Thompson", 26));
        // myTree.insert(new Person("Isla", "Wright", 30));
        // myTree.insert(new Person("Ava", "Jackson", 60));
        // myTree.insert(new Person("Jack", "Gallagehr", 55));
        // myTree.insert(new Person("Oliver", "Browne", 42));
        // myTree.insert(new Person("Charlie", "Hardinge", 8));
        // myTree.insert(new Person("Jacob", "Twist", 19));

        // // Trigger a specific Rotation Cases
        // myTree.insert(new Person("Zoe", "Adams", 25)); // May trigger LL
        // myTree.insert(new Person("Liam", "Adams", 22)); // May trigger LR
        // myTree.insert(new Person("Nina", "Zimmer", 28)); // May trigger RR
        // myTree.insert(new Person("Eli", "Zimmer", 24)); // May trigger RL

        // // Week 3 Tester
        // System.out.println("In-order Traversal:");
        // myTree.inOrderTraversal(); // Should show sorted by surname

        // System.out.println("\nPre-order Traversal:");
        // myTree.preOrderTraversal(); // Shows structure and root

        // System.out.println("\nPost-order Traversal:");
        // myTree.postOrderTraversal(); // Shows leaf-first structure
        // System.out.println();
        // // End Week 3
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

        // // Test find() - should return the value if found
        // System.out.println("Find 80: " + myTree.find(80)); // Should return 80
        // System.out.println("Find 99 (not in tree): " + myTree.find(99)); // Should
        // return null

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