package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class DoublyLinkedList<E> implements List<E> {

    private static class Node<E> {
        private E data;
        private Node<E> next;
        private  Node<E> prev;

        public Node(E e, Node<E> p, Node<E> n) {
            data = e;
            prev = p;
            next = n;
        }

        public E getData() {
            return data;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

    }

    private  Node<E> head;
    private  Node<E> tail;
    private int size = 0;

    public DoublyLinkedList() {
        head = new Node<E>(null, null, null);
        tail = new Node<E>(null, head, null);
        head.next = tail;
    }

    private void addBetween(E e, Node<E> pred, Node<E> succ) {
        Node<E> newNode = new Node<E>(e, pred, succ);

        pred.next = newNode;
        succ.prev = newNode;

        size++;
    }

    @Override
    public int size() {
        // TODO
        return size;
    }

    @Override
    public boolean isEmpty() {
        if  (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public E get(int i) {
        Node<E> p = head.next;

        for (int j = 0; j < i; j++){
            p = p.next;
        }

        return p.data;
    }

    @Override
    public void add(int i, E e) {
        // TODO
        Node<E> succ = head;

        for (int j = 0; j < i; j++){
            succ = succ.next;
        }

        Node<E> pred = succ.prev;

        Node<E> newNode = new Node<E>(e, pred, succ);

        pred.next = newNode;
        succ.prev = newNode;
        size++;
    }

    @Override
    public E remove(int i) {
        Node<E> target = head.next;

        for (int j = 0; j < i; j++){
            target = target.next;
        }

        Node<E> pred = target.prev;
        Node<E> succ = target.next;

        pred.next = succ;
        succ.prev = pred;

        size--;
        return target.data;
    }

    private class DoublyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head.next;

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
        return new DoublyLinkedListIterator<E>();
    }

    private E remove(Node<E> n) {
        Node<E> pred = n.prev;
        Node<E> succ = n.next;

        pred.next = succ;
        succ.prev = pred;
        size--;
        return n.data;
    }

    public E first() {
        if (isEmpty()) {
            return null;
        }
        return head.next.getData();
    }

    public E last() {
        if (isEmpty()) {
            return null;
        }
        return tail.prev.getData();
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        return remove(head.next);
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        return remove(tail.prev);
    }

    @Override
    public void addLast(E e) {
        // TODO
        addBetween(e, tail.prev, tail);
    }

    @Override
    public void addFirst(E e) {
        addBetween(e, head, head.next);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head.next;
        while (curr != tail) {
            sb.append(curr.data);
            curr = curr.next;
            if (curr != tail) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> ll = new DoublyLinkedList<Integer>();
        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addLast(-1);
        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }
    }
}
