package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {
        private final T data;
        private Node<T> next;

        public Node(T e, Node<T> n) {
            data = e;
            next = n;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> n) {
            next = n;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private Node<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) {
        // TODO
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> node = tail.next;

        for (int j = i; j < size; j++) {
            node = node.next;
        }

        return node.data;
    }

    /**
     * Inserts the given element at the specified index of the list, shifting all
     * subsequent elements in the list one position further to make room.
     *
     * @param i the index at which the new element should be stored
     * @param e the new element to be stored
     */
    @Override
    public void add(int i, E e) {
        if  (i < 0 || i > size) {
            throw new IndexOutOfBoundsException();
        }

        if (i == 0) {
            addFirst(e);
        }
        else{

            Node<E> node = tail.next;
            for (int j = i; j < size; j++) {
                node = node.next;
            }

            Node<E> newNode = new Node<>(e, node.next);
            node.next = newNode;

            if (i == size){
                tail = newNode;
            }

            size++;
        }
    }

    @Override
    public E remove(int i) {
        if  (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> prev = tail;
        for (int j = i; j < size; j++) {
            prev = prev.next;
        }

        Node<E> node = prev.next;
        E wanted = node.data;

        if (node == tail){

            if (size == 1){
                tail = null;
            }
            else{
                prev.next = node.next;
                tail = prev;
            }

        }
        else{
            prev.next = node.next;
        }
        size--;
        return wanted;
    }

    public void rotate() {
        if(tail != null){
            tail = tail.next;
        }
    }

    private class CircularlyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) tail;

        @Override
        public boolean hasNext() {
            return curr != tail;
        }

        @Override
        public E next() {
            E res = curr.data;
            curr = curr.next;
            return res;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        if(isEmpty()){
            return null;
        }

        Node<E> front = tail.next;
        if (front == tail) {
            tail = null;
        }
        else{
            tail.next = front.next;
        }
        size--;
        return front.data;
    }

    @Override
    public E removeLast() {
        if (size == 1){
            return removeFirst();
        }

        Node<E> node = tail.next;
        while (node.next != tail){
            node = node.next;
        }

        E data = tail.data;
        node.next = tail.next;
        tail = node;
        size--;

        return data;
    }

    @Override
    public void addFirst(E e) {
        if (size == 0){
            tail = new Node<>(e, null);
            tail.next = tail;
        }
        else{
            Node<E> node = new Node<>(e, tail.next);
            tail.next = node;
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        addFirst(e);
        tail = tail.next;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = tail;
        do {
            curr = curr.next;
            sb.append(curr.data);
            if (curr != tail) {
                sb.append(", ");
            }
        } while (curr != tail);
        sb.append("]");
        return sb.toString();
    }


    public static void main(String[] args) {
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        for (int i = 10; i < 20; ++i) {
            ll.addLast(i);
        }

        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.rotate();
        System.out.println(ll);

        ll.removeFirst();
        ll.rotate();
        System.out.println(ll);

        ll.removeLast();
        ll.rotate();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }

    }
}
