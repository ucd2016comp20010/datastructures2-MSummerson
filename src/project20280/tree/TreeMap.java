package project20280.tree;

import project20280.interfaces.Entry;
import project20280.interfaces.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


/**
 * An implementation of a sorted map using a binary search tree.
 */

public class TreeMap<K, V> extends AbstractSortedMap<K, V> {

    // ---------------- nested BalanceableBinaryTree class ----------------

    /**
     * A specialized version of the LinkedBinaryTree class with additional mutators
     * to support binary search tree operations, and a specialized node class that
     * includes an auxiliary instance variable for balancing data.
     */
    protected static class BalanceableBinaryTree<K, V> extends LinkedBinaryTree<Entry<K, V>> {
        // -------------- nested BSTNode class --------------
        // this extends the inherited LinkedBinaryTree.Node class
        protected static class BSTNode<E> extends Node<E> {
            int aux = 0;

            BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
                super(e, parent, leftChild, rightChild);
            }

            public int getAux() {
                return aux;
            }

            public void setAux(int value) {
                aux = value;
            }
        } // --------- end of nested BSTNode class ---------

        // positional-based methods related to aux field
        public int getAux(Position<Entry<K, V>> p) {
            return ((BSTNode<Entry<K, V>>) p).getAux();
        }

        public void setAux(Position<Entry<K, V>> p, int value) {
            ((BSTNode<Entry<K, V>>) p).setAux(value);
        }

        // Override node factory function to produce a BSTNode (rather than a Node)
        @Override
        protected Node<Entry<K, V>> createNode(Entry<K, V> e, Node<Entry<K, V>> parent, Node<Entry<K, V>> left,
                                               Node<Entry<K, V>> right) {
            return new BSTNode<>(e, parent, left, right);
        }

        /**
         * Relinks a parent node with its oriented child node.
         */
        private void relink(Node<Entry<K, V>> parent, Node<Entry<K, V>> child, boolean makeLeftChild) {
            child.setParent(parent);
            if (makeLeftChild) {
                parent.setLeft(child);
            }
            else {
                parent.setRight(child);
            }
        }

        /**
         * Rotates Position p above its parent. Switches between these configurations,
         * depending on whether p is a or p is b.
         *
         * <pre>
         *          b                  a
         *         / \                / \
         *        a  t2             t0   b
         *       / \                    / \
         *      t0  t1                 t1  t2
         * </pre>
         * <p>
         * Caller should ensure that p is not the root.
         */
        public void rotate(Position<Entry<K, V>> p) {
            Node<Entry<K,V>> x = validate(p);
            Node<Entry<K,V>> y = x.getParent( ); // we assume this exists
            Node<Entry<K,V>> z = y.getParent( ); // grandparent (possibly null)
            if (z == null) {
                root = x; // x becomes root of the tree
                x.setParent(null);
            }
            else{
                relink(z, x, y == z.getLeft( ));
            } // x becomes direct child of z
            // now rotate x and y, including transfer of middle subtree
            if (x == y.getLeft( )) {
                relink(y, x.getRight( ), true); // x’s right child becomes y’s left
                relink(x, y, false); // y becomes x’s right child
            }
            else {
                relink(y, x.getLeft( ), false); // x’s left child becomes y’s right
                relink(x, y, true); // y becomes left child of x
            }
        }

        /**
         * Returns the Position that becomes the root of the restructured subtree.
         * <p>
         * Assumes the nodes are in one of the following configurations:
         *
         * <pre>
         *     z=a                 z=c           z=a               z=c
         *    /  \                /  \          /  \              /  \
         *   t0  y=b             y=b  t3       t0   y=c          y=a  t3
         *      /  \            /  \               /  \         /  \
         *     t1  x=c         x=a  t2            x=b  t3      t0   x=b
         *        /  \        /  \               /  \              /  \
         *       t2  t3      t0  t1             t1  t2            t1  t2
         * </pre>
         * <p>
         * The subtree will be restructured so that the node with key b becomes its
         * root.
         *
         * <pre>
         *           b
         *         /   \
         *       a       c
         *      / \     / \
         *     t0  t1  t2  t3
         * </pre>
         * <p>
         * Caller should ensure that x has a grandparent.
         */
        public Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
            Position<Entry<K, V>> y = parent(x);
            Position<Entry<K, V>> z = parent(y);
            if ((x == right(y)) == (y == right(z))) { // matching alignments
                rotate(y); // single rotation (of y)
                return y; // y is new subtree root
            } else { // opposite alignments
                rotate(x); // double rotation (of x)
                rotate(x);
                return x; // x is new subtree root
            }
        }
    } // ----------- end of nested BalanceableBinaryTree class -----------

    // We reuse the LinkedBinaryTree class. A limitation here is that we only use
    // the key.
    // protected LinkedBinaryTree<Entry<K, V>> tree = new LinkedBinaryTree<Entry<K,
    // V>>();
    protected BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();

    /**
     * Constructs an empty map using the natural ordering of keys.
     */
    public TreeMap() {
        super(); // the AbstractSortedMap constructor
        tree.addRoot(null); // create a sentinel leaf as root
    }

    /**
     * Constructs an empty map using the given comparator to order keys.
     *
     * @param comp comparator defining the order of keys in the map
     */
    public TreeMap(Comparator<K> comp) {
        super(comp); // the AbstractSortedMap constructor
        tree.addRoot(null); // create a sentinel leaf as root
    }

    /**
     * Returns the number of entries in the map.
     *
     * @return number of entries in the map
     */
    @Override
    public int size() {
        return (tree.size() - 1) / 2; // only internal nodes have entries
    }

    protected Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
        return tree.restructure(x);
    }

    /**
     * Rebalances the tree after an insertion of specified position. This version of
     * the method does not do anything, but it can be overridden by subclasses.
     *
     * @param p the position which was recently inserted
     */
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        // LEAVE EMPTY
    }

    /**
     * Rebalances the tree after a child of specified position has been removed.
     * This version of the method does not do anything, but it can be overridden by
     * subclasses.
     *
     * @param p the position of the sibling of the removed leaf
     */
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        // LEAVE EMPTY
    }

    /**
     * Rebalances the tree after an access of specified position. This version of
     * the method does not do anything, but it can be overridden by a subclasses.
     *
     * @param p the Position which was recently accessed (possibly a leaf)
     */
    protected void rebalanceAccess(Position<Entry<K, V>> p) {
        // LEAVE EMPTY
    }

    /**
     * Utility used when inserting a new entry at a leaf of the tree
     */
    private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
        tree.set(p, entry);
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }

    // Some notational shorthands for brevity (yet not efficiency)
    protected Position<Entry<K, V>> root() {
        return tree.root();
    }

    protected Position<Entry<K, V>> parent(Position<Entry<K, V>> p) {
        return tree.parent(p);
    }

    protected Position<Entry<K, V>> left(Position<Entry<K, V>> p) {
        return tree.left(p);
    }

    protected Position<Entry<K, V>> right(Position<Entry<K, V>> p) {
        return tree.right(p);
    }

    protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) {
        return tree.sibling(p);
    }

    protected boolean isRoot(Position<Entry<K, V>> p) {
        return tree.isRoot(p);
    }

    protected boolean isExternal(Position<Entry<K, V>> p) {
        return tree.isExternal(p);
    }

    protected boolean isInternal(Position<Entry<K, V>> p) {
        return tree.isInternal(p);
    }

    protected void set(Position<Entry<K, V>> p, Entry<K, V> e) {
        tree.set(p, e);
    }

    protected Entry<K, V> remove(Position<Entry<K, V>> p) {
        return tree.remove(p);
    }

    /**
     * Returns the position in p's subtree having the given key (or else the
     * terminal leaf).
     *
     * @param key a target key
     * @param p   a position of the tree serving as root of a subtree
     * @return Position holding key, or last node reached during search
     */
    private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
        if (isExternal(p)){
            return p;
        }
        int comp = compare(key, p.getElement());
        if (comp == 0) {
            return p;
        }
        else if (comp < 0) {
            return treeSearch(left(p), key);
        }
        else {
            return treeSearch(right(p), key);
        }
    }

    /**
     * Returns position with the minimal key in the subtree rooted at Position p.
     *
     * @param p a Position of the tree serving as root of a subtree
     * @return Position with minimal key in subtree
     */
    protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
        Position<Entry<K,V>> walk = p;
        while (isInternal(walk)) {
            walk = left(walk);
        }
        return parent(walk); // we want the parent of the leaf
    }

    /**
     * Returns the position with the maximum key in the subtree rooted at p.
     *
     * @param p a Position of the tree serving as root of a subtree
     * @return Position with maximum key in subtree
     */
    protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
        Position<Entry<K,V>> walk = p;
        while (isInternal(walk)) {
            walk = right(walk);
        }
        return parent(walk); // we want the parent of the leaf
    }

    /**
     * Returns the value associated with the specified key, or null if no such entry
     * exists.
     *
     * @param key the key whose associated value is to be returned
     * @return the associated value, or null if no such entry exists
     */
    @Override
    public V get(K key) throws IllegalArgumentException {
        checkKey(key); //has a chance to throw illegal
        Position<Entry<K, V>> p = treeSearch(root(), key);
        rebalanceAccess(p); //hook for balancing
        if(isExternal(p)){ //unsucsseful search
            return null;
        }
        return p.getElement().getValue(); //match found
    }

    /**
     * Associates the given value with the given key. If an entry with the key was
     * already in the map, this replaced the previous value with the new one and
     * returns the old value. Otherwise, a new entry is added and null is returned.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the key (or null, if no such
     * entry)
     */
    @Override
    public V put(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K, V> newEntry = new MapEntry<>(key, value);
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if(isExternal(p)){ //key exsists
            expandExternal(p, newEntry);
            rebalanceInsert(p); //hook for the balanced tree
            return null;
        }
        else{ //replacing a key
            V old = p.getElement().getValue();
            set(p, newEntry);
            rebalanceAccess(p); //hook for the balanced tree
            return old;
        }
    }

    /**
     * Removes the entry with the specified key, if present, and returns its
     * associated value. Otherwise does nothing and returns null.
     *
     * @param key the key whose entry is to be removed from the map
     * @return the previous value associated with the removed key, or null if no
     * such entry exists
     */
    @Override
    public V remove(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if(isExternal(p)){
            rebalanceAccess(p);
            return null;
        }
        else{
            V old = p.getElement().getValue();
            if(isInternal(left(p)) && isInternal(right(p))){
                Position<Entry<K, V>> replacement = treeMax(left(p));
                set(p, replacement.getElement());
                p = replacement;
            }
            Position<Entry<K, V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
            Position<Entry<K, V>> sib = sibling(leaf);
            remove(leaf);
            remove(p);
            rebalanceDelete(sib);
            return old;
        }
    }

    // additional behaviors of the SortedMap interface

    /**
     * Returns the entry having the least key (or null if map is empty).
     *
     * @return entry with least key (or null if map is empty)
     */
    @Override
    public Entry<K, V> firstEntry() {
        if (isEmpty())
            return null;
        return treeMin(root()).getElement();
    }

    /**
     * Returns the entry having the greatest key (or null if map is empty).
     *
     * @return entry with greatest key (or null if map is empty)
     */
    @Override
    public Entry<K, V> lastEntry() {
        if (isEmpty())
            return null;
        return treeMax(root()).getElement();
    }

    /**
     * Returns the entry with least key greater than or equal to given key (or null
     * if no such key exists).
     *
     * @return entry with least key greater than or equal to given (or null if no
     * such entry)
     * @throws IllegalArgumentException if the key is not compatible with the map
     */
    @Override
    public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (isInternal(p)){
            return p.getElement();
        }
        while (!isRoot(p) && p == right(parent(p))){
            p = parent(p);
        }
        if (isRoot(p)){
            return null;
        }
        return parent(p).getElement();
    }

    /**
     * Returns the entry with greatest key less than or equal to given key (or null
     * if no such key exists).
     *
     * @return entry with greatest key less than or equal to given (or null if no
     * such entry)
     * @throws IllegalArgumentException if the key is not compatible with the map
     */
    @Override
    public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
        checkKey(key); // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root( ), key);
        if (isInternal(p)) {
            return p.getElement(); // exact match
        }
        while (!isRoot(p)) {
            if (p == right(parent(p))) {
                return parent(p).getElement(); // parent has next lesser key
            }
            else {
                p = parent(p);
            }
        }
    return null; // no such floor exists
    }

    /**
     * Returns the entry with greatest key strictly less than given key (or null if
     * no such key exists).
     *
     * @return entry with greatest key strictly less than given (or null if no such
     * entry)
     * @throws IllegalArgumentException if the key is not compatible with the map
     */
    @Override
    public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
        checkKey(key); // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root( ), key);
         if (isInternal(p) && isInternal(left(p))){
          return treeMax(left(p)).getElement( ); // this is the predecessor to p
        } // otherwise, we had failed search, or match with no left child
        while (!isRoot(p)) {
            if (p == right(parent(p))) {
                return parent(p).getElement(); // parent has next lesser key
            }
            else{
                p = parent(p);
            }
        }
    return null; // no such lesser key exists
    }

    /**
     * Returns the entry with least key strictly greater than given key (or null if
     * no such key exists).
     *
     * @return entry with least key strictly greater than given (or null if no such
     * entry)
     * @throws IllegalArgumentException if the key is not compatible with the map
     */
    @Override
    public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
        checkKey(key); // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root( ), key);
        if (isInternal(p) && isInternal(right(p))) {
            return treeMin(right(p)).getElement(); // exact match
        }
        while (!isRoot(p) && p == right(parent(p))) {
            p = parent(p);
        }
        if (isRoot(p)) {
            return null;
        }
        return parent(p).getElement();
    }

    // Support for iteration

    /**
     * Returns an iterable collection of all key-value entries of the map.
     *
     * @return iterable collection of the map's entries
     */
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
        for (Position<Entry<K, V>> p : tree.inorder()) {
            if (isInternal(p)) {
                buffer.add(p.getElement());
            }
        }
        return buffer;
    }

    public String toString() {
        ArrayList<K> keys = new ArrayList<>();
        for (Entry<K, V> entry : entrySet()) {
            keys.add(entry.getKey());
        }
        return keys.toString();
    }

    /**
     * Returns an iterable containing all entries with keys in the range from
     * <code>fromKey</code> inclusive to <code>toKey</code> exclusive.
     *
     * @return iterable with keys in desired range
     * @throws IllegalArgumentException if <code>fromKey</code> or
     *                                  <code>toKey</code> is not compatible with
     *                                  the map
     */
    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size( ));
        if (compare(fromKey, toKey) < 0) { // ensure that fromKey < toKey
            subMapRecurse(fromKey, toKey, root(), buffer);
        }
        return buffer;
    }

    private void subMapRecurse(K fromKey, K toKey, Position<Entry<K,V>> p,
        ArrayList<Entry<K,V>> buffer) {
        if (isInternal(p)) {
            if (compare(p.getElement(), fromKey) < 0) {
                // p's key is less than fromKey, so any relevant entries are to the right
                subMapRecurse(fromKey, toKey, right(p), buffer);
            }
            else{
                subMapRecurse(fromKey, toKey, left(p), buffer); // first consider left subtree
                if (compare(p.getElement(), toKey) < 0) { // p is within range
                    buffer.add(p.getElement()); // so add it to buffer, and consider
                    subMapRecurse(fromKey, toKey, right(p), buffer); // right subtree as well
                }
            }
        }
    }

    protected void rotate(Position<Entry<K, V>> p) {
        tree.rotate(p);
    }

    // remainder of class is for debug purposes only

    /**
     * Prints textual representation of tree structure (for debug purpose only).
     */
    protected void dump() {
        dumpRecurse(root(), 0);
    }

    /**
     * This exists for debugging only
     */
    private void dumpRecurse(Position<Entry<K, V>> p, int depth) {
        String indent = (depth == 0 ? "" : String.format("%" + (2 * depth) + "s", ""));
        if (isExternal(p))
            System.out.println(indent + "leaf");
        else {
            System.out.println(indent + p.getElement());
            dumpRecurse(left(p), depth + 1);
            dumpRecurse(right(p), depth + 1);
        }
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<Entry<K, V>> btp = new BinaryTreePrinter<>(this.tree);
        return btp.print();
    }

    public static void main(String[] args) {
//        TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();
//
//        Random rnd = new Random();
//        int n_max = 50;
//        int n = 100;
//        rnd.ints(1, n_max).limit(n).distinct().boxed().forEach(x -> treeMap.put(x, x));
////
////        Consumer<Integer> modify = x -> {
////            if (rnd.nextFloat() > 0.5)
////                treeMap.put(x, 0);
////            else
////                treeMap.remove(x);
////        };
//        BinaryTreePrinter<Entry<Integer, Integer>> btp = new BinaryTreePrinter<>(treeMap.tree);
//        System.out.println(btp.print());
//
////        rnd.ints(1, n_max).limit(10000000).boxed().forEach(modify);
//        System.out.println(btp.print());
//
//        AVLTreeMap<Integer, Integer> avl = new AVLTreeMap<Integer, Integer>();
//        for (Position<Entry<Integer, Integer>> i : treeMap.tree.inorder()) {
//            if (i.getElement() != null) {
//                avl.put(i.getElement().getKey(), 0);
//            }
//        }
//        System.out.println(avl.toBinaryTreeString());


//        //Q2
//        // initialize the treemap and Random vals
//        TreeMap<Integer, Integer> bst = new TreeMap<>();
//        Random rnd = new Random();
//        int n_max = 50;
//        int n = 20;
//
//        // populate the tree with random integers
//        rnd.ints(1, n_max)
//                .distinct()
//                .limit(n)
//                .boxed()
//                .forEach(x -> bst.put(x, x));
//
//        // use the BinaryTreePrinter to see the structure
//        System.out.println("Tree Structure:");
//        System.out.println(bst.toBinaryTreeString());
//
//        // print the traversal to check sorting
//        System.out.println("Inorder Traversal (Should be sorted):");
//
//        // we now check the actual tree and call its inorder method
//        System.out.println("\nInorder Traversal (Sorted):");
//        for (var entry : bst.entrySet()) {
//            System.out.print(entry.getKey() + " ");
//        }


//        //Q3
//        TreeMap<Integer, Integer> bst = new TreeMap<>();
//        Random rnd = new Random();
//        int n_max = 200;
//        int n = 100;
//
//        rnd.ints(1, 1000)
//                .distinct()
//                .limit(n)
//                .boxed()
//                .forEach(x -> bst.put(x, x));
//
//        //System.out.println("Initial Height: " + bst.tree.height(bst.root()));
//        System.out.println("Tree Structure:");
//        System.out.println(bst.toBinaryTreeString());
//        System.out.println("Height wihtout root = " + bst.tree.height());
//        System.out.println("Current Size = " + bst.size());
//
//        // ensure we put() a node which doesn't already exist in the tree
//        // ensure we remove() a node which does exist in the tree
//        int n_trials = 10000;
//        for (int i = 0; i < n_trials; ++i) {
//            var keyset = bst.keySet();
//            List<Integer> target = new ArrayList<>();
//            keyset.forEach(target::add);
//            if (rnd.nextFloat() > 0.5) {
//                int newI = rnd.nextInt(2000);
//                bst.put(newI, newI);
//            } else {
//                if (!target.isEmpty()) {
//                    Integer targetI = target.get(rnd.nextInt(target.size()));
//                    bst.remove(targetI);
//                }
//            }
//        }
//        //System.out.println("Final Height after " + n_trials + " trials: " + bst.tree.height(bst.root()));
//        System.out.println("Tree Structure after removal:");
//        System.out.println(bst.toBinaryTreeString());
//        System.out.println("Height wihtout root = " + bst.tree.height());
//        System.out.println("Current Size = " + bst.size());


//        //Q4
//        int[] sizes = {1000, 5000, 10000, 20000};
//        Random rnd = new Random();
//
//        for (int n : sizes) {
//            List<Integer> data = new ArrayList<>();
//            for (int i = 0; i < n; i++) data.add(rnd.nextInt(n * 10));
//
//            System.out.println("Testing n = " + n );
//
//            // unbalanced treemap
//            long start = System.nanoTime();
//            TreeMap<Integer, Integer> bst = new TreeMap<>();
//            for (int x : data) bst.put(x, x);
//            var entries1 = bst.entrySet();
//            System.out.println("Custom TreeMap:  " + (System.nanoTime() - start) / 1_000_000.0 + " ms");
//
//            // balance AVL
//            start = System.nanoTime();
//            AVLTreeMap<Integer, Integer> avl = new AVLTreeMap<>();
//            for (int x : data) avl.put(x, x);
//            var entries2 = avl.entrySet();
//            System.out.println("Custom AVLTree:  " + (System.nanoTime() - start) / 1_000_000.0 + " ms");
//
//            //using only javautil
//            start = System.nanoTime();
//            java.util.TreeMap<Integer, Integer> jTree = new java.util.TreeMap<>();
//            for (int x : data) jTree.put(x, x);
//            var entries3 = jTree.entrySet();
//            System.out.println("Java Util Tree:  " + (System.nanoTime() - start) / 1_000_000.0 + " ms");
//            System.out.println();
//
//        }


        // Treap test
        int[] sizes = {1000, 10000, 50000};
        Random rnd = new Random();

        System.out.printf("%-15s | %-12s | %-10s | %-8s\n", "Size", "Tree Type", "Time (ms)", "Height");
        System.out.println("------------------------------------------------------------");

        for (int n : sizes) {
            List<Integer> data = new ArrayList<>();
            for (int i = 0; i < n; i++) data.add(rnd.nextInt(n * 10));

            // Define the trees to test
            TreeMap<Integer, Integer>[] trees = new TreeMap[]{
                    new TreeMap<>(),    // Unbalanced
                    new AVLTreeMap<>(), // AVL
                    new Treap<>()       // Your New Treap
            };
            String[] labels = {"BST", "AVL", "Treap"};

            for (int i = 0; i < trees.length; i++) {
                long start = System.nanoTime();
                for (int x : data) trees[i].put(x, x);
                long end = System.nanoTime();

                double ms = (end - start) / 1_000_000.0;
                int height = trees[i].tree.height();

                System.out.printf("%-15d | %-12s | %-10.2f | %-8d\n", n, labels[i], ms, height);
            }
        }

    }
}
