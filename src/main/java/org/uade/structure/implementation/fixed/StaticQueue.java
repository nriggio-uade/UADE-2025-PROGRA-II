package org.uade.structure.implementation.fixed;

import org.uade.structure.definition.QueueADT;

public class StaticQueue implements QueueADT {

    private int[] data;
    private int front;
    private int rear;
    private int count;
    private static final int MAX_SIZE = 100;

    public StaticQueue() {
        this.data = new int[MAX_SIZE];
        this.front = 0;
        this.rear = 0;
        this.count = 0;
    }

    @Override
    public void add(int value) {
        if (this.count >= MAX_SIZE) {
            throw new RuntimeException("La cola está llena (Queue Overflow). No se puede agregar.");
        }
        this.data[this.rear] = value;
        this.rear = (this.rear + 1) % MAX_SIZE;
        this.count++;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new RuntimeException("La cola está vacía (Queue Underflow). No se puede remover.");
        }
        this.front = (this.front + 1) % MAX_SIZE;
        this.count--;
    }

    @Override
    public int getElement() {
        if (isEmpty()) {
            throw new RuntimeException("La cola está vacía. No se puede obtener el elemento.");
        }
        return this.data[this.front];
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    public boolean isFull() {
        return this.count == MAX_SIZE;
    }
}