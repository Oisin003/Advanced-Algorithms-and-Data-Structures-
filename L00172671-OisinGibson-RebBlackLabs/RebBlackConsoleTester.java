// Oisin Gibson
// L00172671
// Updated: 11-11-2025

public class RebBlackConsoleTester {

    public static void main(String[] args) {

        RedBlackTree<Integer> myTree = new RedBlackTree<Integer>();
        myTree.preOrderTraversal();
        myTree.insert(1);
        myTree.insert(2);
        myTree.insert(3);
        System.out.println("Pre-order traversal:");
        myTree.preOrderTraversal();

    }

}
