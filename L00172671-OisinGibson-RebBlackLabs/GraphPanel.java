// Oisin Gibson
// L00172671
// Updated: 11-11-2025

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.*;

/**
 * This code is based on a class by John B. Matthews.
 * See https://sites.google.com/site/drjohnbmatthews/graphpanel
 * 
 * 
 */
public class GraphPanel extends JComponent {

    private static final int WIDE = 640;
    private static final int HIGH = 480;
    private static final int RADIUS = 35;
    private static final Random rnd = new Random();
    private ControlPanel control = new ControlPanel();
    private int radius = RADIUS;
    private Kind kind = Kind.Circular;
    private List<DisplayNode> nodes = new ArrayList<DisplayNode>();
    private List<DisplayNode> selected = new ArrayList<DisplayNode>();
    private List<Edge> edges = new ArrayList<Edge>();
    private List<Integer> insertionsInOrder = new ArrayList<Integer>();

    private Point mousePt = new Point(WIDE / 2, HIGH / 2);
    private Rectangle mouseRect = new Rectangle();
    private boolean selecting = false;

    private BinarySearchTreeViewCapable<Integer> myTree = new BinarySearchTreeViewCapable<Integer>();

    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFrame f = new JFrame("RedBlackTree");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GraphPanel gp = new GraphPanel();
                f.add(gp.control, BorderLayout.NORTH);
                f.add(new JScrollPane(gp), BorderLayout.CENTER);

                // This will be set to the Insert Button (hitting [Enter] will invoke it)
                f.getRootPane().setDefaultButton(gp.control.newDefaultButton);
                f.pack();
                f.setLocationByPlatform(true);
                f.setVisible(true);
            }
        });
    }

    public GraphPanel() {
        this.setOpaque(true);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDE, HIGH);
    }

    @Override
    public void paintComponent(Graphics g) {

        // NB:1
        // We use an inOrderTraversal to find out and populate the
        // node positions this is an overridden method in the subclass
        myTree.inOrderTraversal();

        g.setColor(new Color(0x00f0f0f0));
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Edge e : edges) {
            e.draw(g);
        }
        for (DisplayNode n : nodes) {
            n.draw(g);
        }

        String nodeDisplayString = "Insertion Order of Nodes: ";
        for (Integer currNodeVal : insertionsInOrder) {
            nodeDisplayString += (currNodeVal + ",");
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 16));
        g.drawString(nodeDisplayString, 5, 20);

        // This is not really relevant to Red Black display
        if (selecting) {
            g.setColor(Color.darkGray);
            g.drawRect(mouseRect.x, mouseRect.y,
                    mouseRect.width, mouseRect.height);
        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter {

        Point delta = new Point();

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selecting) {
                mouseRect.setBounds(
                        Math.min(mousePt.x, e.getX()),
                        Math.min(mousePt.y, e.getY()),
                        Math.abs(mousePt.x - e.getX()),
                        Math.abs(mousePt.y - e.getY()));
                DisplayNode.selectRect(nodes, mouseRect);
            } else {
                delta.setLocation(
                        e.getX() - mousePt.x,
                        e.getY() - mousePt.y);
                DisplayNode.updatePosition(nodes, delta);
                mousePt = e.getPoint();
            }
            e.getComponent().repaint();
        }
    }

    public JToolBar getControlPanel() {
        return control;
    }

    private class ControlPanel extends JToolBar {

        private Action clearAll = new ClearAction("Clear");
        private Action kind = new KindComboAction("Kind");
        private JTextField insertValue = new JTextField();
        private Action insert = new InsertAction("Insert", insertValue);

        private JButton newDefaultButton = new JButton(insert);
        private JComboBox<Kind> kindCombo = new JComboBox<Kind>();

        ControlPanel() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setBackground(Color.lightGray);

            this.add(new JButton(clearAll));
            this.add(kindCombo);

            // Our Insert Button is the default
            this.add(newDefaultButton);

            insertValue.setColumns(5);
            this.add(insertValue);

            JMenu subMenu = new JMenu("Kind");
            for (Kind k : Kind.values()) {
                kindCombo.addItem(k);
                subMenu.add(new JMenuItem(new KindItemAction(k)));
            }

            kindCombo.addActionListener(kind);
        }

        class KindItemAction extends AbstractAction {

            private Kind k;

            public KindItemAction(Kind k) {
                super(k.toString());
                this.k = k;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                kindCombo.setSelectedItem(k);

            }
        }
    }

    /**
     * 
     * @author Oisin Gibson
     *         Action class for Insert button
     *         Use the insert() method (which, in turn, will call handleRedBlack())
     *         to
     *         perform the insertion.
     *
     */

    private class InsertAction extends AbstractAction {
        private JTextField insertVal; // Reference to the text field where user enters the value

        /**
         * Constructor to create an InsertAction linked to a specific JTextField.
         *
         * @param name      The name of the action (can be used for buttons/menus)
         * @param insertVal The JTextField from which the value will be read
         */
        public InsertAction(String name, JTextField insertVal) {
            super(name);
            this.insertVal = insertVal;
        }

        /**
         * Handles the action event (e.g., button click or Enter key press).
         */
        public void actionPerformed(ActionEvent e) {
            try {

                Integer valToInsert = Integer.valueOf(insertVal.getText().trim());

                insertionsInOrder.add(valToInsert);

                myTree.insert(valToInsert);

                myTree.computeNodePositions();

                insertVal.setText("");

                repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid integer");
            }
        }
    }

    /**
     * 
     * @author Oisin Gibson
     *         Action class for Clear button
     *         Clears the tree and resets the GUI
     *
     */
    private class ClearAction extends AbstractAction {

        /**
         * Constructor to create a ClearAction with a given name.
         *
         * @param name The name of the action (used for buttons/menus)
         */
        public ClearAction(String name) {
            super(name); // Call superclass constructor with action name
        }

        /**
         * Handles the action event (e.g., button click) to clear the tree.
         */
        public void actionPerformed(ActionEvent e) {
            // Remove the tree by setting root references to null
            myTree.root = null;
            myTree.myRoot = null;

            // Clear the lists storing nodes and edges for GUI rendering
            nodes.clear();
            edges.clear();

            // Clear the list that tracks insertion order
            insertionsInOrder.clear();

            repaint();
        }
    }

    /**
     * 
     * @author Oisin Gibson
     *         Action class for Kind combo box
     *         Changes the shape of selected nodes
     *
     */

    private class KindComboAction extends AbstractAction {

        public KindComboAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            JComboBox combo = (JComboBox) e.getSource();
            kind = (Kind) combo.getSelectedItem();
            DisplayNode.updateKind(nodes, kind);
            repaint();
        }
    }

    /**
     * The kinds of node in a graph.
     */
    private enum Kind {

        Circular, Rounded, Square;
    }

    /**
     * An Edge is a pair of Nodes.
     */
    private static class Edge {

        private DisplayNode n1;
        private DisplayNode n2;

        public Edge(DisplayNode n1, DisplayNode n2) {
            this.n1 = n1;
            this.n2 = n2;
        }

        public void draw(Graphics g) {
            Point p1 = n1.getLocation();
            Point p2 = n2.getLocation();
            g.setColor(Color.darkGray);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    /**
     * A DisplayNode is a node with a position, size, color, and shape.
     */
    private static class DisplayNode {

        private Point p;
        private int r;
        private Color color;
        private Kind kind;
        private boolean selected = false;
        private Rectangle b = new Rectangle();

        private Integer nodeValue;
        private boolean hasNodeValue;

        /**
         * Construct a new node. This came with the code and had no
         * value/key
         */
        public DisplayNode(Point p, int r, Color color, Kind kind) {
            this.p = p;
            this.r = r;
            this.color = color;
            this.kind = kind;
            hasNodeValue = false;
            setBoundary(b);
        }

        /**
         * Construct a new node. Overloaded to include a value
         */
        public DisplayNode(Point p, int r, Color color, Kind kind, Integer value) {
            this.p = p;
            this.r = r;
            this.color = color;
            this.kind = kind;
            this.nodeValue = value;
            hasNodeValue = true;
            setBoundary(b);
        }

        /**
         * Calculate this node's rectangular boundary.
         */
        private void setBoundary(Rectangle b) {
            b.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
        }

        /**
         * Draw this node.
         */
        public void draw(Graphics g) {
            g.setColor(this.color);
            if (this.kind == Kind.Circular) {
                g.fillOval(b.x, b.y, b.width, b.height);
            } else if (this.kind == Kind.Rounded) {
                g.fillRoundRect(b.x, b.y, b.width, b.height, r, r);
            } else if (this.kind == Kind.Square) {
                g.fillRect(b.x, b.y, b.width, b.height);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier", Font.BOLD, 24));
            if (hasNodeValue) {
                int numDigits = nodeValue.toString().length();

                g.drawString(nodeValue.toString(),
                        p.x - GraphPanel.BinarySearchTreeViewCapable.NodeConstants.X_CHAROFFSET * numDigits,
                        p.y + GraphPanel.BinarySearchTreeViewCapable.NodeConstants.Y_CHAROFFSET);
            }
            if (selected) {
                g.setColor(Color.darkGray);
                g.drawRect(b.x, b.y, b.width, b.height);
            }
        }

        /**
         * Return this node's location.
         */
        public Point getLocation() {
            return p;
        }

        /**
         * Return true if this node contains p.
         */
        public boolean contains(Point p) {
            return b.contains(p);
        }

        /**
         * Return true if this node is selected.
         */
        public boolean isSelected() {
            return selected;
        }

        /**
         * Mark this node as selected.
         */
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        /**
         * Collected all the selected nodes in list.
         */
        public static void getSelected(List<DisplayNode> list, List<DisplayNode> selected) {
            selected.clear();
            for (DisplayNode n : list) {
                if (n.isSelected()) {
                    selected.add(n);
                }
            }
        }

        /**
         * Select no nodes.
         */
        public static void selectNone(List<DisplayNode> list) {
            for (DisplayNode n : list) {
                n.setSelected(false);
            }
        }

        /**
         * Select a single node; return true if not already selected.
         */
        public static boolean selectOne(List<DisplayNode> list, Point p) {
            for (DisplayNode n : list) {
                if (n.contains(p)) {
                    if (!n.isSelected()) {
                        DisplayNode.selectNone(list);
                        n.setSelected(true);
                    }
                    return true;
                }
            }
            return false;
        }

        /**
         * Select each node in r.
         */
        public static void selectRect(List<DisplayNode> list, Rectangle r) {
            for (DisplayNode n : list) {
                n.setSelected(r.contains(n.p));
            }
        }

        /**
         * Toggle selected state of each node containing p.
         */
        public static void selectToggle(List<DisplayNode> list, Point p) {
            for (DisplayNode n : list) {
                if (n.contains(p)) {
                    n.setSelected(!n.isSelected());
                }
            }
        }

        /**
         * Update each node's position by d (delta).
         */
        public static void updatePosition(List<DisplayNode> list, Point d) {
            for (DisplayNode n : list) {
                if (n.isSelected()) {
                    n.p.x += d.x;
                    n.p.y += d.y;
                    n.setBoundary(n.b);
                }
            }
        }

        /**
         * Update each node's radius r.
         */
        public static void updateRadius(List<DisplayNode> list, int r) {
            for (DisplayNode n : list) {
                if (n.isSelected()) {
                    n.r = r;
                    n.setBoundary(n.b);
                }
            }
        }

        /**
         * Update each node's color.
         */
        public static void updateColor(List<DisplayNode> list, Color color) {
            for (DisplayNode n : list) {
                if (n.isSelected()) {
                    n.color = color;
                }
            }
        }

        /**
         * Update each node's kind.
         */
        public static void updateKind(List<DisplayNode> list, Kind kind) {
            for (DisplayNode n : list) {
                if (n.isSelected()) {
                    n.kind = kind;
                }
            }
        }
    }

    public class BinarySearchTreeViewCapable<T extends Comparable<T>> extends RedBlackTree<T> {
        private MyNode myRoot;

        public int totalNodes = 0;
        public int maxHeight = 0;

        public int treeHeight(Node t) {
            if (t == null)
                return -1;
            else
                return 1 + max(treeHeight(t.left), treeHeight(t.right));
        }

        public int max(int a, int b) {
            if (a > b)
                return a;
            else
                return b;
        }

        /**
         * This method uses an inorder traversal to figure out the relative x and y
         * positions of
         * each node.
         */
        public void computeNodePositions() {
            int depth = 1;
            totalNodes = 0;
            nodes.clear();
            edges.clear();
            recInOrderTraversal(myRoot, depth);
        }

        private void recInOrderTraversal(MyNode subTreeRoot, int depth) {

            if (subTreeRoot == null)
                return;

            recInOrderTraversal((MyNode) subTreeRoot.left, depth + 1);
            processNode(subTreeRoot, depth);
            recInOrderTraversal((MyNode) subTreeRoot.right, depth + 1);
        }

        /**
         * This performs the actual calculation of the relative x and y positions for
         * the node.
         * It creates a data structure necessary to display the node.
         * It also creates an edge using the current node and the parent node.
         * 
         * @param node
         * @param depth
         */
        // @Override
        private void processNode(MyNode node, int depth) {
            Color nodeColour = (node.nodeColourRed) ? Color.red : Color.black;

            node.xpos = totalNodes++;
            node.ypos = depth;
            Point p = new Point(NodeConstants.X_OFFSET + node.xpos * NodeConstants.X_OFFSET,
                    NodeConstants.Y_OFFSET + node.ypos * NodeConstants.Y_OFFSET);

            DisplayNode currDisplayNode = new DisplayNode(p, 30, nodeColour, kind, (Integer) node.value);
            node.refToDisplayNode = currDisplayNode;

            if (node.parent != null) {

                DisplayNode parentDisplayNode = ((MyNode) node.parent).refToDisplayNode;
                Edge currEdge = new Edge(currDisplayNode, parentDisplayNode);
                edges.add(currEdge);
            }
            nodes.add(currDisplayNode);
        }

        /*
         * @Override
         * public void inOrderTraversal()
         * {
         * 
         * }
         * 
         * @Override
         * protected void recInOrderTraversal(Node subTreeRoot)
         * {
         * if(subTreeRoot == null) return;
         * 
         * recInOrderTraversal(subTreeRoot.left);
         * processNode(subTreeRoot);
         * //subTreeRoot.xpos = totalNodes++;
         * //subTreeRoot.ypos = depth;
         * recInOrderTraversal(subTreeRoot.right);
         * }
         */

        @Override
        public void insert(T value) {
            MyNode node = new MyNode(value); // Create the Node to add

            // Special case that cannot be handled recursively
            if (myRoot == null) {
                myRoot = node;
            } else {
                super.insertRec(myRoot, node);
            }

            super.root = myRoot;
            super.handleRedBlack(node);
            myRoot = (BinarySearchTreeViewCapable<T>.MyNode) root;
            myTree.computeNodePositions();
            myTree.maxHeight = myTree.treeHeight(myTree.root);

        }

        public final class NodeConstants {
            public static final int X_OFFSET = 100;
            public static final int Y_OFFSET = 50;

            public static final int X_CHAROFFSET = 5;
            public static final int Y_CHAROFFSET = 5;

        }

        public class MyNode extends RedBlackTree<T>.Node {
            public int xpos;
            public int ypos;

            public DisplayNode refToDisplayNode;

            public MyNode(T value) {
                super(value);
            }

            @Override
            public String toString() {
                return super.toString() + " xpos: " + xpos + " ypos: " + ypos;
            }

        }
    }

    /*
     * public class MyInteger extends Integer implements Comparable<Integer>
     * {
     * 
     * @Override
     * public int compareTo(java.lang.Integer arg0) {
     * // TODO Auto-generated method stub
     * return 0;
     * }
     * 
     * }
     */
}