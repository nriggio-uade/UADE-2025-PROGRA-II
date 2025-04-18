package org.uade.structure.implementation.dynamic;

import org.uade.exception.EmptyQueueException;
import org.uade.structure.definition.PriorityQueueADT;

public class DynamicPriorityQueue implements PriorityQueueADT {
    private Node front; // Referencia al primer nodo de la cola
    private int size;   // Número de elementos en la cola

    // Clase interna para representar un nodo de la cola
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

    // Constructor: Inicializa una cola vacía
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

        // Caso especial: si la cola está vacía o el nuevo nodo tiene mayor prioridad que el primero
        if (isEmpty() || priority > front.priority) {
            newNode.next = front;
            front = newNode;
        } else {
            Node current = front;
            // Encontrar la posición correcta para insertar el nuevo nodo manteniendo el orden de prioridad
            while (current.next != null && priority <= current.next.priority) {
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