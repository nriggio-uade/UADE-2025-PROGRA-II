package org.uade.structure.implementation.dynamic;

import org.uade.structure.definition.QueueADT;

public class DynamicQueue implements QueueADT {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;

    public DynamicQueue() {
        this.front = null;
        this.rear = null;
    }

    @Override
    public void add(int value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            this.front = newNode;
            this.rear = newNode;
        } else {
            this.rear.next = newNode;
            this.rear = newNode;
        }
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new RuntimeException("La cola está vacía (Queue Underflow). No se puede remover.");
        }
        this.front = this.front.next;
        if (this.front == null) {
            this.rear = null;
        }
    }

    @Override
    public int getElement() {
        if (isEmpty()) {
            throw new RuntimeException("La cola está vacía. No se puede obtener el elemento.");
        }
        return this.front.value;
    }

    @Override
    public boolean isEmpty() {
        return this.front == null;
    }
}