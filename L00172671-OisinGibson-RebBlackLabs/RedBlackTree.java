// Oisin Gibson - L00172671 - Updated: 11-11-2025

//package com.adsg.tree;

/** @author Oisin Gibson */
public class RedBlackTree<T extends Comparable<T>> {
    protected Node root;

    /** Inserts a value into the tree and applies Red-Black balancing. */
    public void insert(T value) {
        Node node = new Node(value);

        if (root == null) {
            root = node;
            node.nodeColourRed = false;
            return;
        }

        insertRec(root, node);
        handleRedBlack(node); // Apply Red-Black balancing
    }

    /** Recursively inserts a node into the BST and sets parent references. */
    protected void insertRec(Node subTreeRoot, Node node) {
        if (node.value.compareTo(subTreeRoot.value) < 0) {
            if (subTreeRoot.left == null) {
                subTreeRoot.left = node;
                node.parent = subTreeRoot;
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

    // Handle Red-Black violations after insertion
    void handleRedBlack(Node newNode) {
        if (newNode == root) {
            newNode.nodeColourRed = false;
            return;
        }

        Node parent = newNode.parent;
        Node grandParent = (parent != null) ? parent.parent : null;

        if (grandParent == null)
            return;

        if (!parent.nodeColourRed)
            return;

        Node uncle = uncleOnRightTree(newNode) ? getRightUncle(newNode) : getLeftUncle(newNode);

        // Red Uncle - Recolor and recurse upward
        if (uncle != null && uncle.nodeColourRed) {
            parent.nodeColourRed = false;
            uncle.nodeColourRed = false;
            grandParent.nodeColourRed = true;
            handleRedBlack(grandParent);
            return;
        } else if ((uncle == null) || !uncle.nodeColourRed) {
            // Black uncle - Apply rotation cases
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
            return;
        }
    }

    private boolean uncleOnRightTree(Node node) {
        Node parent = node.parent;
        Node grandParent = parent.parent;
        return (grandParent != null && parent == grandParent.left);
    }

    /** Returns the left uncle (grandparent's left child). */
    private Node getLeftUncle(Node node) {
        if (node == null || node.parent == null || node.parent.parent == null)
            return null;
        return node.parent.parent.left;
    }

    /** Returns the right uncle (grandparent's right child). */
    private Node getRightUncle(Node node) {
        if (node == null || node.parent == null || node.parent.parent == null)
            return null;
        return node.parent.parent.right;
    }

    /** Traverse the tree in-order. */
    public void inOrderTraversal() {
        recInOrderTraversal(root);
    }

    public void preOrderTraversal() {
        recPreOrderTraversal(root);
    }

    public void postOrderTraversal() {
        recPostOrderTraversal(root);
    }

    /** Recursively process the tree in-order. */
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

    /** Process a node - here we print it. */
    private void processNode(Node currNode) {
        System.out.println(currNode.toString());
    }

    /** @return The number of nodes in the tree */
    public int countNodes() {
        return recCountNodes(root);
    }

    /** Pre-order traversal example - count nodes recursively. */
    private int recCountNodes(Node subTreeRoot) {
        if (subTreeRoot == null)
            return 0;

        return 1 + recCountNodes(subTreeRoot.left) + recCountNodes(subTreeRoot.right);
    }

    /////////////////////////////////////////////////////////////////
    /** Node contains a value and references to left/right subtrees. */
    protected class Node {
        public T value;
        public Node left;
        public Node right;

        // Red-Black Tree properties
        public Node parent;
        public boolean nodeColourRed = true; // New nodes default to red

        public Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            String color = nodeColourRed ? "RED" : "BLACK";
            return "Node [value=" + value + ", color=" + color + "]";
        }

    }

    /** Rotates the entire tree to the left. */
    public void rotateLeft() {
        root = rotateSubTreeLeft(root);
    }

    /** Rotates the entire tree to the right. */
    public void rotateRight() {
        root = rotateSubTreeRight(root);
    }

    /** Left rotation: promotes right child, demotes original root to left. */
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

    /** Right rotation: promotes left child, demotes original root to right. */
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

    // Left-Left case: right rotate grandparent and recolor
    private Node applyLeftLeftCase(Node grandParent) {
        Node parent = grandParent.left;
        Node greatGrandParent = grandParent.parent;

        Node newRoot = rotateSubTreeRight(grandParent);
        parent.nodeColourRed = false;
        grandParent.nodeColourRed = true;

        // Update parent-child references
        updateParentReferences(grandParent, newRoot, greatGrandParent);

        return newRoot;
    }

    // Left-Right case: left rotate parent, then apply Left-Left case
    private Node applyLeftRightCase(Node parent, Node grandParent) {
        grandParent.left = rotateSubTreeLeft(parent);
        return applyLeftLeftCase(grandParent);
    }

    // Right-Right case: left rotate grandparent and recolor
    protected Node applyRightRightCase(Node grandParent) {
        Node parent = grandParent.right;
        Node greatGrandParent = grandParent.parent;

        Node newSubtreeRoot = rotateSubTreeLeft(grandParent);

        newSubtreeRoot.nodeColourRed = false;
        grandParent.nodeColourRed = true;

        updateParentReferences(grandParent, newSubtreeRoot, greatGrandParent);

        return newSubtreeRoot;
    }

    // Right-Left case: right rotate parent, then apply Right-Right case
    private Node applyRightLeftCase(Node parent, Node grandParent) {
        grandParent.right = rotateSubTreeRight(parent);
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