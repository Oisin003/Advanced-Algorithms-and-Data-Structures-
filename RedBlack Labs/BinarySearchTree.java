// Oisin Gibson
// L00172671
// Updated: 04/11/2025
// Note: Dermots code/comments have been unaltered where possible

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
        Node node = new Node(value); // New nodes default to red
        // Empty tree case
        if (root == null) {
            root = node;
            node.nodeColourRed = false; // Root must always be black
            return;
        }

        insertRec(root, node); // Standard BST insertion
        // Week 3
        handleRedBlack(node); // Apply Red-Black balancing rules
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

    // Week 3
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
                rotateSubTreeLeft(parent); // Left-Right case
                newNode = parent;
                parent = newNode.parent;
            }
            rotateSubTreeRight(grandParent); // Left-Left case
            parent.nodeColourRed = false;
            grandParent.nodeColourRed = true;
        } else {
            // Parent is right child of grandparent
            if (newNode == parent.left) {
                rotateSubTreeRight(parent); // Right-Left case
                newNode = parent;
                parent = newNode.parent;
            }
            rotateSubTreeLeft(grandParent); // Right-Right case
            parent.nodeColourRed = false;
            grandParent.nodeColourRed = true;
        }
    }

    // Week 3
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
        // If node, parent, or grandparent is null, there’s no left uncle
        if (node == null || node.parent == null || node.parent.parent == null)
            return null;

        // Return the left child of the grandparent (the left uncle)
        return node.parent.parent.left;
    }

    /**
     * Returns the right uncle of the node (i.e. grandparent's right child).
     */
    private Node getRightUncle(Node node) {
        // If node, parent, or grandparent is null, there’s no right uncle
        if (node == null || node.parent == null || node.parent.parent == null)
            return null;

        // Return the right child of the grandparent (the right uncle)
        return node.parent.parent.right;
    }

    /**
     * Should traverse the tree "in-order." Useful for viewer layout.
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

        // Process the current node
        processNode(subTreeRoot);

        // Traverse the left subtree
        recPreOrderTraversal(subTreeRoot.left);

        // Traverse the right subtree
        recPreOrderTraversal(subTreeRoot.right);
    }

    /**
     * Private recursive post-order traversal helper.
     */
    private void recPostOrderTraversal(Node subTreeRoot) {
        if (subTreeRoot == null)
            return;

        // Traverse the left subtree
        recPostOrderTraversal(subTreeRoot.left);

        // Traverse the right subtree
        recPostOrderTraversal(subTreeRoot.right);

        // Process the current node
        processNode(subTreeRoot);
    }

    /**
     * Do some "work" on the node - here we just print it out
     */
    private void processNode(Node currNode) {
        System.out.println(currNode.toString());
    }

    /**
     * Return the number of nodes in the tree.
     */
    public int countNodes() {
        return recCountNodes(root);
    }

    /**
     * Practical example of pre-order counting.
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
            return null; // Base case: empty subtree
        if (subTreeRoot.left == null)
            return subTreeRoot.value; // Leftmost node found
        return recFindMinimum(subTreeRoot.left); // Recurse left
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
        // Cannot rotate if node is null or has no right child
        if (node == null || node.right == null)
            return node;

        // The right child becomes the new root of this subtree
        Node newRoot = node.right;

        // Move newRoot's left child to become node's right child
        node.right = newRoot.left;

        // Make node the left child of newRoot
        newRoot.left = node;

        // Update parent references
        newRoot.parent = node.parent; // newRoot takes node’s old parent
        node.parent = newRoot; // node’s new parent is newRoot

        // If node’s new right child exists, update its parent reference
        if (node.right != null)
            node.right.parent = node;

        // Return the new root after rotation
        return newRoot;
    }

    /**
     * Right-rotate the subtree rooted at `node`.
     * Returns the new subtree root (the original node's left child).
     */
    private Node rotateSubTreeRight(Node node) {
        // If the node is null or has no left child, a right rotation isn't possible
        if (node == null || node.left == null)
            return node;

        // The left child will become the new root of this subtree
        Node newRoot = node.left;

        // Move newRoot's right child to be node's left child
        node.left = newRoot.right;

        // Set node as the right child of newRoot
        newRoot.right = node;

        // Update parent references
        newRoot.parent = node.parent; // newRoot takes node's old parent
        node.parent = newRoot; // node's parent is now newRoot

        // If node's left child exists, update its parent to node
        if (node.left != null)
            node.left.parent = node;

        // Return the new root after rotation
        return newRoot;
    }
}