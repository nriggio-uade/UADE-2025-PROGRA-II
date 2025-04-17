package org.uade.structure.implementation.fixed;

import org.uade.exception.EmptyQueueException;
import org.uade.exception.FullQueueException;
import org.uade.structure.definition.PriorityQueueADT;

public class StaticPriorityQueue implements PriorityQueueADT {
    private int[] elements; // Array para almacenar los elementos
    private int[] priorities; // Array para almacenar las prioridades correspondientes
    private int front;       // Índice del primer elemento (para la operación remove)
    private int rear;        // Índice de la siguiente posición libre (para la operación add)
    private int size;        // Cantidad actual de elementos en la cola
    private int capacity;    // Capacidad máxima de la cola

    // Constructor: Inicializa la cola con una capacidad dada
    public StaticPriorityQueue(int capacity) {
        this.capacity = capacity;
        this.elements = new int[capacity];
        this.priorities = new int[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getElement() {
        // Verificamos si la cola está vacía
        if (isEmpty()) {
            throw new EmptyQueueException("La cola está vacía, no se puede obtener el elemento.");
        }
        // El primer elemento está en la posición 'front' del array 'elements'
        return elements[front];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPriority() {
        // Verificamos si la cola está vacía
        if (isEmpty()) {
            throw new EmptyQueueException("La cola está vacía, no se puede obtener la prioridad.");
        }
        // La prioridad del primer elemento está en la posición 'front' del array 'priorities'
        return priorities[front];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int value, int priority) {
        // Verificamos si la cola está llena
        if (size == capacity) {
            throw new FullQueueException("La cola está llena, no se puede agregar más elementos.");
        }
        // Agregamos el nuevo elemento y su prioridad al final de los arrays
        elements[rear] = value;
        priorities[rear] = priority;
        // Movemos el 'rear' a la siguiente posición libre. Si llegamos al final del array, volvemos al principio (cola circular).
        rear = (rear + 1) % capacity;
        // Incrementamos el tamaño de la cola
        size++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {
        // Verificamos si la cola está vacía
        if (isEmpty()) {
            throw new EmptyQueueException("La cola está vacía, no se puede eliminar ningún elemento.");
        }
        // Simplemente movemos el 'front' a la siguiente posición. El elemento anterior se considera eliminado.
        front = (front + 1) % capacity;
        // Decrementamos el tamaño de la cola
        size--;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        // La cola está vacía si el tamaño es 0
        return size == 0;
    }
}