package org.uade.structure.implementation.dynamic;

import org.uade.structure.definition.LinkedListADT;

public class DynamicLinkedList implements LinkedListADT {
    private Node head;
    private int size;

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    public DynamicLinkedList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void add(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void insert(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites.");
        }
        Node newNode = new Node(value);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites.");
        }
        if (index == 0) {
            head = head.next;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }

    @Override
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites.");
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Método para intercalar dos listas
    public static DynamicLinkedList intercalar(DynamicLinkedList list1, DynamicLinkedList list2) {
        DynamicLinkedList result = new DynamicLinkedList();
        Node current1 = list1.head;
        Node current2 = list2.head;

        // Intercalamos elementos de ambas listas
        while (current1 != null && current2 != null) {
            result.add(current1.value);
            current1 = current1.next;
            result.add(current2.value);
            current2 = current2.next;
        }

        // Añadimos los elementos restantes de la lista más larga
        while (current1 != null) {
            result.add(current1.value);
            current1 = current1.next;
        }

        while (current2 != null) {
            result.add(current2.value);
            current2 = current2.next;
        }

        return result;
    }
}
