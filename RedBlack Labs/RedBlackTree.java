// Oisin Gibson
// L00172671
// Updated: 23.10.2025
// Note: Dermots code/comments have been unaltered where possible

//package com.adsg.tree;

/**
 * This is a "generic" Binary Search Tree - know the definition of what a BST
 * is!
 * 
 * NOTE: To allow for our objects to be inserted (and found) properly they have
 * to be COMPARED
 * to the objects in the tree. This is why we have <T extends Comparable<T>>
 * instead of
 * just <T> : We are effectively saying that the objects which can be stored
 * MUST implement
 * the Comparable interface.
 * 
 * NOTE: Our Node class is an inner class in an inner class at the bottom of the
 * code.
 * 
 * @author Oisin Gibson
 *
 * @param <T>
 */
public class RedBlackTree<T extends Comparable<T>> {
    /**
     * Reference to the root of the tree
     */
    // public Node root;
    // Week 5 - make the root protected
    protected Node root;

    /**
     * This is the public insert method, i.e. the one that the outside world will
     * invoke.
     * It then kicks off a recursive method to "walk" through down through the tree
     * - this is
     * possible because each sub-tree is itself a tree.
     * 
     * @param value Object to insert into the tree
     */
    /**
     * Inserts a new value into the tree.
     * If the tree is empty, the new node becomes the root and is colored black.
     * Otherwise, it inserts recursively and then applies Red-Black balancing.
     */
    public void insert(T value) {
        Node node = new Node(value); // New nodes default to red

        if (root == null) {
            root = node;
            node.nodeColourRed = false; // Root must always be black
            return;
        }

        insertRec(root, node); // Standard BST insertion
        // Week 3
        // Now handle any Red-Black Tree violations
        handleRedBlack(node); // Apply Red-Black balancing rules
    }

    /**
     * 
     * @param subTreeRoot The SubTree to insert into
     * @param node        The Node that we wish to insert
     */
    /**
     * Recursively inserts a node into the correct position in the BST.
     * Also sets the parent reference for Red-Black Tree logic.
     */
    protected void insertRec(Node subTreeRoot, Node node) {
        if (node.value.compareTo(subTreeRoot.value) < 0) {
            if (subTreeRoot.left == null) {
                subTreeRoot.left = node;
                node.parent = subTreeRoot; // Track parent for balancing
                return;
            } else {
                insertRec(subTreeRoot.left, node);
            }
        } else {
            if (subTreeRoot.right == null) {
                subTreeRoot.right = node;
                node.parent = subTreeRoot;
                return;
            } else {
                insertRec(subTreeRoot.right, node);
            }
        }
    }

    // Week 3
    // Checks if there is a violation i.e. the parent of the new Red node is itself
    // Red
    void handleRedBlack(Node newNode) {
        if (newNode == root) {
            newNode.nodeColourRed = false; // Root must be black
            return;
        }

        Node parent = newNode.parent;
        Node grandParent = (parent != null) ? parent.parent : null;

        if (grandParent == null)
            return; // No violation possible

        if (!parent.nodeColourRed)
            return; // No violation

        Node uncle = uncleOnRightTree(newNode) ? getRightUncle(newNode) : getLeftUncle(newNode);

        // Case 3: Red Uncle - Recolor and recurse upward
        if (uncle != null && uncle.nodeColourRed) {
            parent.nodeColourRed = false;
            uncle.nodeColourRed = false;
            grandParent.nodeColourRed = true;
            handleRedBlack(grandParent);
            return;
        } else if ((uncle == null) || !uncle.nodeColourRed) {
            // Determine which subcase we're dealing with and handle it.
            if (parent == grandParent.left && newNode == parent.left) {
                System.out.println("Left-Left case detected");
                applyLeftLeftCase(grandParent);
            } else if (parent == grandParent.left && newNode == parent.right) {
                System.out.println("Left-Right case detected");
                applyLeftRightCase(parent, grandParent);
            } else if (parent == grandParent.right && newNode == parent.right) {
                System.out.println("Right-Right case detected");
                applyRightRightCase(grandParent);
            } else if (parent == grandParent.right && newNode == parent.left) {
                System.out.println("Right-Left case detected");
                applyRightLeftCase(parent, grandParent);
            }
            // We've handled the black-uncle rotation/recolouring above — stop here.
            return;
        }
    }

    // Week 3
    private boolean uncleOnRightTree(Node node) {
        Node parent = node.parent;
        Node grandParent = parent.parent;
        return (grandParent != null && parent == grandParent.left);
    }

    /**
     * Returns the left uncle of the node (i.e. grandparent's left child).
     */
    private Node getLeftUncle(Node node) {
        if (node == null || node.parent == null || node.parent.parent == null)
            return null;
        return node.parent.parent.left;
    }

    /**
     * Returns the right uncle of the node (i.e. grandparent's right child).
     */
    private Node getRightUncle(Node node) {
        if (node == null || node.parent == null || node.parent.parent == null)
            return null;
        return node.parent.parent.right;
    }

    /**
     * Should traverse the tree "in-order." See the notes
     */
    public void inOrderTraversal() {
        // start at the root and recurse
        recInOrderTraversal(root);
    }

    public void preOrderTraversal() {
        // start at the root and recurse
        recPreOrderTraversal(root);
    }

    public void postOrderTraversal() {
        // start at the root and recurse
        recPostOrderTraversal(root);
    }

    /**
     * This allows us to recursively process the tree "in-order". Note that it is
     * private
     * 
     * @param subTreeRoot
     */
    private void recInOrderTraversal(Node subTreeRoot) {
        if (subTreeRoot == null)
            return;

        recInOrderTraversal(subTreeRoot.left);
        processNode(subTreeRoot);
        recInOrderTraversal(subTreeRoot.right);
    }

    private void recPreOrderTraversal(Node subTreeRoot) {
        if (subTreeRoot == null)
            return;

        processNode(subTreeRoot);
        recPreOrderTraversal(subTreeRoot.left);
        recPreOrderTraversal(subTreeRoot.right);
    }

    private void recPostOrderTraversal(Node subTreeRoot) {
        if (subTreeRoot == null)
            return;

        recPostOrderTraversal(subTreeRoot.left);
        recPostOrderTraversal(subTreeRoot.right);
        processNode(subTreeRoot);
    }

    /**
     * Do some "work" on the node - here we just print it out
     * 
     * @param currNode
     */
    private void processNode(Node currNode) {
        System.out.println(currNode.toString());
    }

    /**
     * 
     * @return The number of nodes in the tree
     */
    public int countNodes() {
        return recCountNodes(root);
    }

    /**
     * Note: This is a practical example of a simple usage of pre-order traversal
     * 
     * @param subTreeRoot
     * @return
     */
    private int recCountNodes(Node subTreeRoot) {
        if (subTreeRoot == null)
            return 0;

        // Look at the pre-order. "Count this node and THEN count the left and right
        // subtrees recursively
        return 1 + recCountNodes(subTreeRoot.left) + recCountNodes(subTreeRoot.right);
    }

    /////////////////////////////////////////////////////////////////
    /**
     * Our Node contains a value and a reference to the left and right subtrees
     * (initially null)
     * 
     * @author Oisin Gibson
     *
     */
    protected class Node {
        public T value; // value is the actual object that we are storing
        public Node left;
        public Node right;

        // Week 3 additions for Red-Black Tree
        public Node parent; // Reference to parent node
        public boolean nodeColourRed = true; // New nodes default to red

        public Node(T value) {
            this.value = value;
        }

        // Week 3
        // Use this one for the person class tester
        @Override
        public String toString() {
            String color = nodeColourRed ? "RED" : "BLACK";
            return "Node [value=" + value + ", color=" + color + "]";
        }

    }

    // Week 2 Task 1
    /**
     * Rotates the entire tree to the left.
     * This is a test method that assumes root and root.right are not null.
     */
    public void rotateLeft() {
        root = rotateSubTreeLeft(root);
    }

    /**
     * Rotates the entire tree to the right.
     * This is a test method that assumes root and root.left are not null.
     */
    public void rotateRight() {
        root = rotateSubTreeRight(root);
    }

    // Week 2 Task 2
    /**
     * Performs a left rotation on the given subtree.
     * Makes the right child the new root of the subtree.
     *
     * @param node The root of the subtree to rotate.
     * @return The new root after rotation.
     */
    /**
     * Performs a left rotation on the given subtree.
     * Promotes the right child and demotes the original root to the left.
     */
    private Node rotateSubTreeLeft(Node node) {
        if (node == null || node.right == null)
            return node;

        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;

        // Update parent references
        newRoot.parent = node.parent;
        node.parent = newRoot;
        if (node.right != null)
            node.right.parent = node;

        return newRoot;
    }

    /**
     * Performs a right rotation on the given subtree.
     * Makes the left child the new root of the subtree.
     *
     * @param node The root of the subtree to rotate.
     * @return The new root after rotation.
     */
    /**
     * Performs a right rotation on the given subtree.
     * Promotes the left child and demotes the original root to the right.
     */
    private Node rotateSubTreeRight(Node node) {
        if (node == null || node.left == null)
            return node;

        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;

        // Update parent references
        newRoot.parent = node.parent;
        node.parent = newRoot;
        if (node.left != null)
            node.left.parent = node;

        return newRoot;
    }

    // Week 4
    private Node applyLeftLeftCase(Node grandParent) {
        Node parent = grandParent.left;
        Node greatGrandParent = grandParent.parent;

        Node newRoot = rotateSubTreeRight(grandParent);
        parent.nodeColourRed = false;
        grandParent.nodeColourRed = true;

        // Update the reference from the great-grandparent to point to the new root
        updateParentReferences(grandParent, newRoot, greatGrandParent);

        return newRoot;
    }

    private Node applyLeftRightCase(Node parent, Node grandParent) {
        // First rotation: rotate left around parent and attach back to grandParent.left
        grandParent.left = rotateSubTreeLeft(parent);
        // Then handle as a Left-Left case
        return applyLeftLeftCase(grandParent);
    }

    protected Node applyRightRightCase(Node grandParent) {
        Node parent = grandParent.right;
        Node greatGrandParent = grandParent.parent;

        // Step 1: Rotate
        Node newSubtreeRoot = rotateSubTreeLeft(grandParent);

        // Step 2: Recolor
        newSubtreeRoot.nodeColourRed = false;
        grandParent.nodeColourRed = true;

        // Step 3: Update parent references
        updateParentReferences(grandParent, newSubtreeRoot, greatGrandParent);

        return newSubtreeRoot;
    }

    private Node applyRightLeftCase(Node parent, Node grandParent) {
        // First rotation: rotate right around parent and attach back to
        // grandParent.right
        grandParent.right = rotateSubTreeRight(parent);
        // Then handle as a Right-Right case
        return applyRightRightCase(grandParent);
    }

    private void updateParentReferences(Node oldRoot, Node newRoot, Node parent) {
        if (parent == null) {
            root = newRoot;
        } else if (parent.left == oldRoot) {
            parent.left = newRoot;
        } else {
            parent.right = newRoot;
        }
        newRoot.parent = parent;
    }

}