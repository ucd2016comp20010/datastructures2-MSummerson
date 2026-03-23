package project20280.tree;

import project20280.interfaces.BinaryTree;
import project20280.interfaces.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base class providing some functionality of the BinaryTree interface.
 * <p>
 * The following five methods remain abstract, and must be implemented
 * by a concrete subclass: size, root, parent, left, right.
 */
public abstract class AbstractBinaryTree<E> extends AbstractTree<E>
        implements BinaryTree<E> {

    /**
     * Returns the Position of p's sibling (or null if no sibling exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the sibling (or null if no sibling exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> sibling(Position<E> p) {
        Position<E> parent = parent(p);

        if (parent == null) {
            return null;
        }
        if (p == left(parent)){
            return right(parent);
        }
        else{
            return left(parent);
        }
    }

    /**
     * Returns the number of children of Position p.
     *
     * @param p A valid Position within the tree
     * @return number of children of Position p
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public int numChildren(Position<E> p) {
        int count = 0;

        if (left(p) != null) {
            count++;
        }
        if (right(p) != null) {
            count++;
        }
        return count;
    }

    /**
     * Returns an iterable collection of the Positions representing p's children.
     *
     * @param p A valid Position within the tree
     * @return iterable collection of the Positions of p's children
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Iterable<Position<E>> children(Position<E> p) {
        List<Position<E>> snapshot = new ArrayList<>(2);    // max capacity of 2
        if (left(p) != null)
            snapshot.add(left(p));
        if (right(p) != null)
            snapshot.add(right(p));
        return snapshot;
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using an inorder traversal
     *
     * @param p        Position serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void inorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        if (left(p) != null){
            inorderSubtree(left(p), snapshot);
        }
        snapshot.add(p);
        if (right(p) != null){
            inorderSubtree(right(p), snapshot);
        }
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in inorder.
     *
     * @return iterable collection of the tree's positions reported in inorder
     */
    public Iterable<Position<E>> inorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty())
            inorderSubtree(root(), snapshot);   // fill the snapshot recursively
        return snapshot;
    }

    /**
     * Returns an iterable collection of the positions of the tree using inorder traversal
     *
     * @return iterable collection of the tree's positions using inorder traversal
     */
    @Override
    public Iterable<Position<E>> positions() {
        return inorder();
    }

    public int countExternalNodes() {
        if (isEmpty()) {
            return 0;
        }
        return countExternal(root());
    }

    private int countExternal(Position<E> p) {
        if (numChildren(p) == 0) {
            return 1;  // p is a leaf (external node)
        }

        int count = 0;

        if (left(p) != null) {
            count += countExternal(left(p));
        }

        if (right(p) != null) {
            count += countExternal(right(p));
        }

        return count;
    }

/*
Check the left child: If it exists and is a leaf, increment the count. If it's not a leaf, recurse into it.

Check the right child: If it exists, recurse into it (but don't count it as a leaf, as it is a right child).


pseudo code

Algorithm countLeftExternal(p):
    Input: A Position p in a Binary Tree
    Output: The number of left external nodes in the subtree rooted at p

    count = 0

    Check the left child
    if left(p) is not null then
        if isExternal(left(p)) then
            count = count + 1
        else
            count = count + countLeftExternal(left(p))

    Check the right child
    if right(p) is not null then
        count = count + countLeftExternal(right(p))

    return count
 */
    public int CountLeft (){

        if (isEmpty()){
            return 0;
        }


        return CountLeftExternal(root());
    }

    public int CountLeftExternal(Position<E> p) {

        int count = 0;
        Position<E> l = left(p);
        Position<E> r = right(p);

        if ( l != null){
            if (numChildren(l) == 0) {
                count++;
            }
            else{
                count += CountLeftExternal(l);
            }
        }

        if ( r != null){
            count += CountLeftExternal(r);
        }

        return count;
    }
    /*
    To count the number of descendants of a specific node p in a binary tree, you must count all nodes in the subtree rooted at p,
    excluding node $p$ itself. The most efficient way to do this is a recursive traversal.

    The total number of nodes in a subtree is 1 + (nodes in left subtree) + (nodes in right subtree). Therefore,
    the number of descendants is simply the size of that subtree minus 1.

    pseudo code
    Algorithm countDescendants(T, p):
    Input: A binary tree T and a position p within T
    Output: The total number of descendants of p

    if p is null then
        return 0

    The descendants of p are the nodes in its subtrees
    return countSubtreeSize(T, p) - 1

    Algorithm countSubtreeSize(T, p):
    if p is null then
        return 0

    size = 1  // Count the current node p

    if T.left(p) is not null then
        size = size + countSubtreeSize(T, T.left(p))

    if T.right(p) is not null then
        size = size + countSubtreeSize(T, T.right(p))

    return size
     */


    public int numDescendants(Position<E> p) {
        if (p == null) return 0;
        // Subtract 1 because a node is not its own descendant
        return subtreeSize(p) - 1;
    }

    private int subtreeSize(Position<E> p) {
        int size = 1;
        for (Position<E> child : children(p)) {
            size += subtreeSize(child);
        }
        return size;
    }

}

