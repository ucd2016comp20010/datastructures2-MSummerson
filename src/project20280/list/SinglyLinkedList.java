package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class SinglyLinkedList<E> implements List<E> {

    public SinglyLinkedList<E> sortedMerge(SinglyLinkedList<E> listed){

        SinglyLinkedList<E> result = new SinglyLinkedList<>();

        Node<E> current = head;
        Node<E> current2 = (Node<E>) listed.head;

        Node<E> temp = new Node<E>(null, null);
        Node<E> tail = temp;

        while(current2 != null && current != null ){
            Comparable<E> value = (Comparable<E>) current.getElement();
            E value2 = current2.getElement();

            if (value.compareTo(value2) <= 0){
                tail.next = new Node<E>(current.getElement(), null);
                current = current.next;
            }
            else{
                tail.next = new Node<E>(current2.getElement(), tail);
                current2  = current2.next;
            }

            tail = tail.next;
            result.size++;
        }


        while (current != null){
            tail.next = new Node<E>(current.getElement(), null);
            current = current.next;
            tail = tail.next;
            result.size++;
        }

        while (current2 != null){
            tail.next = new Node<E>(current2.getElement(), null);
            current2 = current2.next;
            tail = tail.next;
            result.size++;
        }

        result.head = temp.next;
        return result;

    }

    public SinglyLinkedList<E> cloner() {

        SinglyLinkedList<E> newList = new SinglyLinkedList<>();

        Node<E> current = head;

        while (current != null){
            newList.addLast(current.getElement());

            current = current.next;
        }

        return newList;

    }

    private static class Node<E> {

        private final E element;            // reference to the element stored at this node

        /**
         * A reference to the subsequent node in the list
         */
        private Node<E> next;         // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            // TODO
            this.element = e;
            this.next = n;
        }

        // Accessor methods

        /**
         * Returns the element stored at the node.
         *
         * @return the element stored at the node
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         *
         * @return the following node
         */
        public Node<E> getNext() {
            // TODO
            return next;
        }

        // Modifier methods

        /**
         * Sets the node's next reference to point to Node n.
         *
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {
            // TODO
            this.next = n;
        }
    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)


    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() {
        this.head = head;
        this.size = 0;
    }              // constructs an initially empty list

    //@Override
    public int size() {
        // TODO
        return size;
    }

    //@Override
    public boolean isEmpty() {
        // TODO
        if  (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public E get(int position) {
        // TODO
        if(position < 0 || position >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> temp = head;

        for (int i = 0; i < position; i++) {
            temp = temp.next;
        }

        return temp.element;
    }

    @Override
    public void add(int position, E e) {
        // TODO
        if (position == 0){
            addFirst(e);
            return;
        }

        Node<E> temp = head;

        for (int i = 0; i < position - 1; i++) {
            if (temp == null) {
                throw new IndexOutOfBoundsException();
            }
            temp = temp.next;
        }

        Node<E> newNode  =  new Node<E>(e, temp.next);
        temp.next = newNode;
        size++;
    }


    @Override
    public void addFirst(E e) {
        // TODO
        Node<E> newNode = new Node<>(e, head);

        head = newNode;

        size++;

    }

    @Override
    public void addLast(E e) {
        // TODO
        Node<E> newNode = new Node<>(e, null);

        if (head == null) {
            head = newNode;
        }
        else{
            Node<E> temp = head;

            while(temp.next!=null){
                temp = temp.next;
            }
            temp.next = newNode;
        }
        size++;
    }

    @Override
    public E remove(int position) {
        // TODO
        if (head == null) {
            return null;
        }

        if (position == 0) {
            E removedDate = head.element;
            head = head.next;
            size--;
            return removedDate;
        }

        Node<E> temp = head;

        for (int i = 0; i < position - 1; i++) {
            if (temp == null || temp.next == null) {
                throw new IndexOutOfBoundsException();
            }
            temp = temp.next;
        }
        Node<E> wanted = temp.next;

        if (wanted == null) {
            throw new IndexOutOfBoundsException();
        }

        E removedData = wanted.element;

        temp.next = wanted.next;

        size--;
        return removedData;


    }

    @Override
    public E removeFirst() {
        if (head == null){
            return null;
        }

        E removedData = head.element;
        head = head.next;

        size--;
        return removedData;
    }

    @Override
    public E removeLast() {
        // TODO
        if (head == null){
            return null;
        }

        if (head.next == null){
            E data = head.element;
            head = null;
            size--;
            return data;
        }

        Node<E> temp = head;

        while (temp.next.next != null){
            temp = temp.next;
        }

        E removedDate = temp.next.element;

        temp.next = null;

        size--;
        return removedDate;
    }

    public void reverse(){
        Node<E> prev = null;
        Node<E> current = head;
        Node<E> next = null;

        while (current != null){
            next = current.next;
            current.next = prev;

            prev = current;
            current = next;

        }

        head = prev;

    }

    //@Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = curr.getElement();
            curr = curr.next;
            return res;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        // 1. Create and populate the first sorted list
        SinglyLinkedList<Integer> l1 = new SinglyLinkedList<>();
        l1.addLast(2);
        l1.addLast(6);
        l1.addLast(20);
        l1.addLast(24);
        System.out.println("List 1: " + l1);

        System.out.println("reversed list 1: ");
        SinglyLinkedList<Integer> l3 = l1.cloner();
        l3.reverse();
        System.out.println(l3);

        // 2. Create and populate the second sorted list
        SinglyLinkedList<Integer> l2 = new SinglyLinkedList<>();
        l2.addLast(1);
        l2.addLast(3);
        l2.addLast(5);
        l2.addLast(8);
        l2.addLast(12);
        l2.addLast(19);
        l2.addLast(25);
        System.out.println("List 2: " + l2);

        // 3. Merge them
        SinglyLinkedList<Integer> result = l1.sortedMerge(l2);

        // 4. Print the result
        System.out.println("Merged Result: " + result);
        System.out.println("Result Size: " + result.size());
    }
}
