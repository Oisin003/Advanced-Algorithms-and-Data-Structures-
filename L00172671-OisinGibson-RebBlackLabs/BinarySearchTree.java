// Oisin Gibson
// L00172671
// Updated: 11-11-2025

/**
 * Generic Binary Search Tree with simple Red-Black insertion support.
 * T must implement Comparable so we can order nodes.
 */
public class BinarySearchTree<T extends Comparable<T>> {
    /**
     * Reference to the root of the tree.
     * Viewer code expects direct access to `root`, so this is public.
     */
    public Node root;

    /**
     * Public insert method used by callers.
     * - Creates a new Node (defaults to red).
     * - If tree empty, new node becomes root and is recoloured black.
     * - Otherwise insert into BST position and call red-black fixup logic.
     */
    public void insert(T value) {
        Node node = new Node(value);
        // Empty tree case
        if (root == null) {
            root = node;
            node.nodeColourRed = false;
            return;
        }

        insertRec(root, node);
        // Week 3
        handleRedBlack(node);
    }

    /**
     * Insert helper: recursively places `node` in subtree rooted at `subTreeRoot`.
     * Sets parent links which are required during red-black repairs.
     * 
     * @param subTreeRoot The SubTree to insert into
     * @param node        The Node that we wish to insert
     */
    private void insertRec(Node subTreeRoot, Node node) {
        // Standard BST insertion logic
        if (node.value.compareTo(subTreeRoot.value) < 0) {
            if (subTreeRoot.left == null) {
                subTreeRoot.left = node;
                node.parent = subTreeRoot; // Track parent for balancing
                return;
                // Recurse left
            } else {
                insertRec(subTreeRoot.left, node);
            }
            // Recurse right
        } else {
            if (subTreeRoot.right == null) {
                subTreeRoot.right = node;
                node.parent = subTreeRoot;
                return;
                // Recurse right
            } else {
                insertRec(subTreeRoot.right, node);
            }
        }
    }

    /**
     * Fix-up method for red-black properties after insertion.
     * Implements the classic cases:
     * - root recolour
     * - red-parent handling (recolour or rotations)
     */
    void handleRedBlack(Node newNode) {
        // Case 1: New node is root
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

        // Case 3: Red Uncle → Recolor and recurse upward
        if (uncle != null && uncle.nodeColourRed) {
            parent.nodeColourRed = false;
            uncle.nodeColourRed = false;
            grandParent.nodeColourRed = true;
            handleRedBlack(grandParent);
            return;
        }

        // Case 4: Black Uncle → Rotation and recoloring
        if (parent == grandParent.left) {
            // Parent is left child of grandparent
            if (newNode == parent.right) {
                rotateSubTreeLeft(parent);
                newNode = parent;
                parent = newNode.parent;
            }
            rotateSubTreeRight(grandParent);
            parent.nodeColourRed = false;
            grandParent.nodeColourRed = true;
        } else {
            // Parent is right child of grandparent
            if (newNode == parent.left) {
                rotateSubTreeRight(parent);
                newNode = parent;
                parent = newNode.parent;
            }
            rotateSubTreeLeft(grandParent);
            parent.nodeColourRed = false;
            grandParent.nodeColourRed = true;
        }
    }

    /**
     * Helper to determine which side the uncle is on.
     * Returns true if parent is left child of grandparent (so uncle is on right).
     */
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
     * Should traverse the tree "in-order." Useful for viewer layout.
     */
    public void inOrderTraversal() {
        recInOrderTraversal(root);
    }

    public void preOrderTraversal() {
        recPreOrderTraversal(root);
    }

    public void postOrderTraversal() {
        recPostOrderTraversal(root);
    }

    /**
     * Private recursive in-order traversal helper.
     */
    private void recInOrderTraversal(Node subTreeRoot) {
        if (subTreeRoot == null)
            return;

        // Traverse the left subtree
        recInOrderTraversal(subTreeRoot.left);

        // Process the current node
        processNode(subTreeRoot);

        // Traverse the right subtree
        recInOrderTraversal(subTreeRoot.right);
    }

    /**
     * Private recursive pre-order traversal helper.
     */
    private void recPreOrderTraversal(Node subTreeRoot) {
        if (subTreeRoot == null)
            return;
        processNode(subTreeRoot);
        recPreOrderTraversal(subTreeRoot.left);
        recPreOrderTraversal(subTreeRoot.right);
    }

    /**
     * Private recursive post-order traversal helper.
     */
    private void recPostOrderTraversal(Node subTreeRoot) {
        if (subTreeRoot == null)
            return;
        recPostOrderTraversal(subTreeRoot.left);
        recPostOrderTraversal(subTreeRoot.right);
        processNode(subTreeRoot);
    }

    private void processNode(Node currNode) {
        System.out.println(currNode.toString());
    }

    /**
     * Return the number of nodes in the tree.
     */
    public int countNodes() {
        return recCountNodes(root);
    }

    private int recCountNodes(Node subTreeRoot) {
        if (subTreeRoot == null)
            return 0;
        return 1 + recCountNodes(subTreeRoot.left) + recCountNodes(subTreeRoot.right);
    }

    /////////////////////////////////////////////////////////////////

    /**
     * Node inner class: stores value, left/right children, parent, and colour.
     * Public visibility on fields simplifies viewer code access.
     */
    public class Node {
        public T value; // The stored value (key)
        public Node left; // Left subtree
        public Node right; // Right subtree

        // Red-Black bookkeeping
        public Node parent; // Parent reference (used by rotations and fix-up)
        public boolean nodeColourRed = true; // New nodes default to red per insertion rule

        public Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node [value=" + value + ", colour=" + (nodeColourRed ? "RED" : "BLACK") + "]";
        }
    }

    /////////////// Utility methods for tasks ///////////////////////

    /**
     * Find maximum (rightmost) value in the tree.
     */
    public T findMaximum() {
        Node current = root;
        if (current == null)
            return null; // Tree is empty

        // Traverse right until the last node
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    /**
     * Find minimum (leftmost) value in the tree.
     */
    public T findMinimum() {
        return recFindMinimum(root);
    }

    private T recFindMinimum(Node subTreeRoot) {
        if (subTreeRoot == null)
            return null;
        if (subTreeRoot.left == null)
            return subTreeRoot.value;
        return recFindMinimum(subTreeRoot.left);
    }

    // Placeholder method (left unimplemented in original)
    public String find(int i) {
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }

    /////////////// Rotation helpers ////////////////////////////////

    /**
     * Performs a left rotation on the entire tree (around the current root).
     * 
     * After rotation:
     * - The root's right child becomes the new root.
     * - The previous root becomes the left child of the new root.
     * 
     */
    public void rotateLeft() {
        root = rotateSubTreeLeft(root);
    }

    /**
     * Performs a right rotation on the entire tree (around the current root).
     * 
     * After rotation:
     * - The root's left child becomes the new root.
     * - The previous root becomes the right child of the new root.
     * 
     */
    public void rotateRight() {
        root = rotateSubTreeRight(root);
    }

    private Node rotateSubTreeLeft(Node node) {
        if (node == null || node.right == null)
            return node;
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;

        newRoot.parent = node.parent; // newRoot takes node’s old parent
        node.parent = newRoot; // node’s new parent is newRoot

        // If node’s new right child exists, update its parent reference
        if (node.right != null)
            node.right.parent = node;
        return newRoot;
    }

    /**
     * Right-rotate the subtree rooted at `node`.
     * Returns the new subtree root (the original node's left child).
     */
    private Node rotateSubTreeRight(Node node) {
        if (node == null || node.left == null)
            return node;

        Node newRoot = node.left;

        node.left = newRoot.right;

        newRoot.right = node;

        newRoot.parent = node.parent;
        node.parent = newRoot;

        if (node.left != null)
            node.left.parent = node;
        return newRoot;
    }
}