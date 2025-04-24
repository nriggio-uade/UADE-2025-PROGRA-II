package org.uade.structure.implementation.dynamic;

import org.uade.exception.EmptyQueueException;
import org.uade.structure.definition.PriorityQueueADT;

public class DynamicPriorityQueue implements PriorityQueueADT {
    private Node front;
    private int size;

    private static class Node {
        int value;
        int priority;
        Node next;

        Node(int value, int priority) {
            this.value = value;
            this.priority = priority;
            this.next = null;
        }
    }

    public DynamicPriorityQueue() {
        this.front = null;
        this.size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getElement() {
        if (isEmpty()) {
            throw new EmptyQueueException("La cola está vacía, no se puede obtener el elemento.");
        }
        return front.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPriority() {
        if (isEmpty()) {
            throw new EmptyQueueException("La cola está vacía, no se puede obtener la prioridad.");
        }
        return front.priority;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int value, int priority) {
        Node newNode = new Node(value, priority);

        // Caso especial: si la cola está vacía o el nuevo nodo tiene mayor o igual prioridad que el primero (menor valor numérico)
        if (isEmpty() || priority < front.priority) { // CORRECCIÓN: Cambiado > a <
            newNode.next = front;
            front = newNode;
        } else {
            Node current = front;
            // Encontrar la posición correcta para insertar el nuevo nodo manteniendo el orden de prioridad (menor prioridad numérica al frente)
            while (current.next != null && priority >= current.next.priority) { // CORRECCIÓN: Cambiado <= a >=
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {
        if (isEmpty()) {
            throw new EmptyQueueException("La cola está vacía, no se puede eliminar.");
        }
        front = front.next;
        size--;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}