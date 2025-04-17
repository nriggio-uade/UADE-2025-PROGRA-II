package org.uade.structure.implementation.fixed;

import org.uade.structure.definition.StackADT;

/**
 * Implementación Estática de la interfaz StackADT
 * Utiliza un array de tamaño fijo.
 */
public class StaticStack implements StackADT {

    // --- Atributos ---

    private int[] data;
    private int top;
    private static final int MAX_SIZE = 100;

    // --- Constructor ---

    public StaticStack() {
        this.data = new int[MAX_SIZE];
        this.top = -1;
    }

    // --- Métodos de la Interfaz ---

    @Override
    public void add(int value) {
        if (this.top >= MAX_SIZE - 1) {
            throw new RuntimeException("La pila está llena (Stack Overflow). No se puede agregar.");
        }
        this.top++;
        this.data[this.top] = value;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía (Stack Underflow). No se puede remover.");
        }
        this.top--;
    }

    @Override
    public int getElement() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía. No se puede obtener el elemento.");
        }
        return this.data[this.top];
    }

    @Override
    public boolean isEmpty() {
        return this.top == -1;
    }
}