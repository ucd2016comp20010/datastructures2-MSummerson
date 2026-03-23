package project20280.tree;

import org.w3c.dom.Node;
import project20280.interfaces.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
//import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    static java.util.Random rnd = new java.util.Random();
    /**
     * The root of the binary tree
     */
    protected Node<E> root = null; // root of the tree

    // LinkedBinaryTree instance variables
    /**
     * The number of nodes in the binary tree
     */
    private int size = 0; // number of nodes in the tree

    /**
     * Constructs an empty binary tree.
     */
    public LinkedBinaryTree() {
    } // constructs an empty binary tree

    // constructor

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.root = randomTree(null, 1, n);
        return bt;
    }

    // nonpublic utility

    public static <T extends Integer> Node<T> randomTree(Node<T> parent, Integer first, Integer last) {
        if (first > last) return null;
        else {
            Integer treeSize = last - first + 1;
            Integer leftCount = rnd.nextInt(treeSize);
            Integer rightCount = treeSize - leftCount - 1;
            Node<T> root = new Node<T>((T) ((Integer) (first + leftCount)), parent, null, null);
            root.setLeft(randomTree(root, first, first + leftCount - 1));
            root.setRight(randomTree(root, first + leftCount + 1, last));
            return root;
        }
    }

    // accessor methods (not already implemented in AbstractBinaryTree)

    public static void main(String [] args) {
//        LinkedBinaryTree<String> bt = new LinkedBinaryTree<>();
//        String[] arr = { "A", "B", "C", "D", "E", null, "F", null, null, "G", "H", null, null, null, null };
//        bt.createLevelOrder(arr);
//        System.out.println(bt.toBinaryTreeString());
//
//        int leaves = bt.countExternalNodes();
//        int left = bt.CountLeft();
//        int desc = bt.numDescendants(bt.root());
//
//        System.out.println("Number of external nodes: " + leaves);
//        System.out.println("Number of Left nodes: " + left);
//        System.out.println("Number of Descendants nodes: " + desc);


//        LinkedBinaryTree <String> bt = new LinkedBinaryTree <>();
//        String[] arr = { "A", "B", "C", "D", "E", null, "F", null, null, "G", "H", null, null, null, null };
//        bt.createLevelOrder(arr);
//        System.out.println(bt.toBinaryTreeString());


//
//        Integer[] inorder = {
//                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
//                16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
//        };
//
//        Integer[] preorder = {
//                18, 2, 1, 14, 13, 12, 4, 3, 9, 6, 5, 8, 7, 10, 11,
//                15, 16, 17, 28, 23, 19, 22, 20, 21, 24, 27, 26, 25, 29, 30
//        };
//
//        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
//
//        bt.construct(inorder, preorder);
//
//        System.out.println(bt.toBinaryTreeString());



//        LinkedBinaryTree<Integer> bt = LinkedBinaryTree.makeRandom(10);
//
//        System.out.println(bt.toBinaryTreeString());
//
//        System.out.println(bt.rootToLeafPaths());


//
//        Integer[] inorder = {
//                0, 1, 2, 3, 5, 6, 17, 10, 16, 14, 13, 12, 11
//        };
//
//        Integer[] preorder = {
//                10, 5, 2, 1, 0, 3, 6, 17, 14, 16, 12, 13, 11
//        };
//
//        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
//        bt.construct(inorder, preorder);
//
//        System.out.println("Constructed Tree:");
//        System.out.println(bt.toBinaryTreeString());
//
//        System.out.println("Diameter (Expected 13): " + bt.diameter());
//
//
//        LinkedBinaryTree<Integer> randomTree = LinkedBinaryTree.makeRandom(10);
//
//        System.out.println("\nRandom Tree:");
//        System.out.println(randomTree.toBinaryTreeString());
//
//        System.out.println("Random Tree Diameter: " + randomTree.diameter());


//        LinkedBinaryTree<String> tree = new LinkedBinaryTree<>();
//
//        String[] arr = { "A", "B", "C", "D", "E", null, "F", null, null, "G", "H", null, null, null, null };
//
//        tree.createLevelOrder(arr);
//
//        System.out.println("Binary Tree:");
//        System.out.println(tree.toBinaryTreeString());
//
//        System.out.print("Leaf nodes left-to-right: ");
//        tree.printLeaves();  // Use the method we wrote earlier
//        System.out.println();

//either i fudged something or this is a very odd trendline
        int step = 500;
        int maxSize = 10000;
        int trials = 50;
        System.out.println("Avg time in ms");

        try (java.io.FileWriter writer = new java.io.FileWriter("inOrderRand2.csv")) {
            writer.write("average time in ms\n");

            for (int n = 50; n <= maxSize; n += step) {
                double totalTime = 0;

                for (int t = 0; t < trials; t++) {
                    LinkedBinaryTree<Integer> tree = LinkedBinaryTree.makeRandom(n);

                    long start = System.nanoTime();
                    tree.inorder();
                    long end = System.nanoTime();

                    totalTime += (end - start);
                }

                double avgTimeMs = (totalTime / trials) / 1e6;

                System.out.printf("%d\t%.5f%n", n, avgTimeMs);

                writer.write(n + "," + avgTimeMs + "\n");
            }

        } catch (java.io.IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p; // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    @Override
    public Position<E> root() {
        return root;
    }

    // update methods supported by this class

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getRight();
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty()) throw new IllegalStateException("Cannot add root to an empty tree");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }

    public void insert(E e) {

        if (root == null){
            root = createNode(e, null, null, null);
            size = 1;
        }
        else{
            root = addRecursive(root, e);
        }

    }

    // recursively add Nodes to binary tree in proper position
    private Node<E> addRecursive(Node<E> p, E e) {
        if (p == null) {
            size++;
            return createNode(e, null, null, null);
        }

        Comparable<? super E> cmp = (Comparable<? super E>) e;

        int compResult = cmp.compareTo(p.element);

        if (compResult < 0) {
            p.left = addRecursive(p.left, e);
        }
        if (compResult > 0) {
            p.right = addRecursive(p.right, e);
        }
        return p;
    }

    /**
     * Creates a new left child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the left of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getLeft() != null){
            throw new IllegalArgumentException("Cannot add left node to an empty tree");
        }
        Node<E> child = createNode(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return null;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the right of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getRight() != null){
            throw new IllegalArgumentException("Cannot add left node to an empty tree");
        }
        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        size++;
        return null;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced
     * element.
     *
     * @param p the relevant Position
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }

    /**
     * Attaches trees t1 and t2, respectively, as the left and right subtree of the
     * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
     *
     * @param p  a leaf of the tree
     * @param t1 an independent tree whose structure becomes the left child of p
     * @param t2 an independent tree whose structure becomes the right child of p
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p is not a leaf
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (isInternal(p)){
            throw new IllegalArgumentException("Cannot attach internal node to an empty tree");
        }

        size += t1.size + t2.size;
        if (!t1.isEmpty()){
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if (!t2.isEmpty()){
            t2.root.setParent(node);
            node.setLeft(t2.root);
            t2.root = null;
            t2.size = 0;
        }

    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (numChildren(p) == 2) {
            throw new IllegalArgumentException("Cannot remove root from an empty tree");
        }

        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if (child != null){
            child.setParent(node.getParent());
        }
        if (node == root){
            root = child;
        }
        else{
            Node<E> parent = node.getParent();
            if (node == parent.getLeft()){
                parent.setLeft(child);
            }
            else{
                parent.setRight(child);
            }
        }
        size--;
        E temp = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);
        return temp;

    }

    public String toString() {
        return positions().toString();
    }

//    public void createLevelOrder(ArrayList<E> l) {
//        root = createLevelOrderHelper(l, null, 0);
//
//    }
//
//    private Node<E> createLevelOrderHelper(java.util.ArrayList<E> l, Node<E> p, int i) {
//        if (i >= l.size() || l.get(i) == null) return null;
//
//        Node<E> node = createNode(l.get(i), p, null, null);
//        node.setLeft(createLevelOrderHelper(l, node, 2 * i + 1));
//        node.setRight(createLevelOrderHelper(l, node, 2 * i + 2));
//
//        return node;
//    }

//    public void createLevelOrder(E[] arr) {
//        if (arr == null || arr.length == 0) return;
//
//        root = createNode(arr[0], null, null, null);
//        size = 1;
//
//        Queue<Node<E>> queue = new LinkedList<>();
//        queue.add(root);
//
//        int i = 1;
//        while (i < arr.length) {
//            Node<E> current = queue.poll();
//
//            // Left child
//            if (i < arr.length) {
//                current.left = createNode(arr[i++], null, null, null);
//                queue.add(current.left);
//                size++;
//            }
//
//            // Right child
//            if (i < arr.length) {
//                current.right = createNode(arr[i++], null, null, null);
//                queue.add(current.right);
//                size++;
//            }
//        }
//    }


    public void createLevelOrder(E[] arr) {
        if (arr == null || arr.length == 0 || arr[0] == null) {
            root = null;
            size = 0;
            return;
        }

        root = createNode(arr[0], null, null, null);
        size = 1;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;

        while (!queue.isEmpty() && i < arr.length) {
            Node<E> current = queue.poll();

            // left child
            if (i < arr.length && arr[i] != null) {
                Node<E> leftChild = createNode(arr[i], current, null, null);
                current.setLeft(leftChild);
                queue.add(leftChild);
                size++;
            }
            i++;

            // right child
            if (i < arr.length && arr[i] != null) {
                Node<E> rightChild = createNode(arr[i], current, null, null);
                current.setRight(rightChild);
                queue.add(rightChild);
                size++;
            }
            i++;
        }
    }


    public void construct(E[] inorder, E[] preorder) {
        if (inorder == null || preorder == null || inorder.length != preorder.length){
            throw new IllegalArgumentException("Invalid traversal arrays");
        }


        root = constructHelper(inorder, preorder,
                0, inorder.length - 1,
                new int[]{0},  // preorder index wrapped in array
                null);
    }

    private Node<E> constructHelper(E[] inorder, E[] preorder, int inStart, int inEnd, int[] preIndex, Node<E> parent) {

        if (inStart > inEnd) return null;

        // select our current root
        E rootElement = preorder[preIndex[0]++];
        Node<E> node = createNode(rootElement, parent, null, null);

        // if its a leaf, end
        if (inStart == inEnd)
            return node;


        //check to see if its time to starts a new subtree
        int inIndex = search(inorder, inStart, inEnd, rootElement);


        //actually take that info and make new subtrees
        node.setLeft(constructHelper(inorder, preorder, inStart, inIndex - 1, preIndex, node));

        node.setRight(constructHelper(inorder, preorder, inIndex + 1, inEnd, preIndex, node));

        return node;
    }

    //simple calculator for when to split into left and right
    private int search(E[] arr, int start, int end, E value) {
        for (int i = start; i <= end; i++) {
            if (arr[i].equals(value))
                return i;
        }
        throw new IllegalArgumentException("Element not found in inorder array");
    }



    public List<List<E>> rootToLeafPaths() {
        List<List<E>> result = new ArrayList<>();

        if (root == null){
            return result;
        }

        List<E> currentPath = new ArrayList<>();
        rootToLeafHelper(root, currentPath, result);

        return result;
    }

    private void rootToLeafHelper(Node<E> node, List<E> currentPath, List<List<E>> result) {

        if (node == null) return;

        // add current node to path
        currentPath.add(node.getElement());

        // if leaf node we store a copy of that path
        if (node.getLeft() == null && node.getRight() == null) {
            result.add(new ArrayList<>(currentPath));
        }
        else {
            rootToLeafHelper(node.getLeft(), currentPath, result);
            rootToLeafHelper(node.getRight(), currentPath, result);
        }

        currentPath.remove(currentPath.size() - 1);
    }



    //W6, Q9
    public void printLeaves() {
        printLeavesHelper(root);
    }

    private void printLeavesHelper(Node<E> node) {
        if (node == null) return;

        if (node.getLeft() == null && node.getRight() == null) {
            System.out.print(node.getElement() + " ");
            return;
        }

        printLeavesHelper(node.getLeft());
        printLeavesHelper(node.getRight());
    }

    /*
    PsuedoCode
    function DIAMETER(node):
    maxDiameter = 0

    function HEIGHT(node):
        if node is null:
            return 0

        leftHeight  = HEIGHT(node.left)
        rightHeight = HEIGHT(node.right)

        //possible diameter through this node
        currentDiameter = leftHeight + rightHeight + 1

        maxDiameter = max(maxDiameter, currentDiameter)

        return max(leftHeight, rightHeight) + 1

    HEIGHT(node)

    return maxDiameter
     */

    //calc diameter with array
    public int diameter() {
        int[] maxDiameter = new int[1];  // shared reference
        heightForDiameter(root, maxDiameter);
        return maxDiameter[0];
    }

    private int heightForDiameter(Node<E> node, int[] maxDiameter) {
        if (node == null) return 0;

        int leftHeight = heightForDiameter(node.getLeft(), maxDiameter);
        int rightHeight = heightForDiameter(node.getRight(), maxDiameter);

        // diameter passing through this node
        int currentDiameter = leftHeight + rightHeight + 1;

        maxDiameter[0] = Math.max(maxDiameter[0], currentDiameter);

        // return height of this subtree
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    /**
     * Nested static class for a binary tree node.
     */
    public static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        // accessor
        public E getElement() {
            return element;
        }

        // modifiers
        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            return sb.toString();
        }
    }
}
