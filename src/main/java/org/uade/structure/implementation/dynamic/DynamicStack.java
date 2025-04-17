package org.uade.structure.implementation.dynamic;

import org.uade.structure.definition.StackADT;

public class DynamicStack implements StackADT {

    // --- Clase interna Nodo ---

    private static class Node {
        int value;
        Node next;


        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    // --- Atributos de DynamicStack ---

    private Node top;

    // --- Constructor ---

    public DynamicStack() {
        this.top = null;
    }

    // --- Métodos de la Interfaz ---

    @Override
    public void add(int value) {
        Node newNode = new Node(value);
        newNode.next = this.top;
        this.top = newNode;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía (Stack Underflow). No se puede remover.");
        }
        this.top = this.top.next;
    }

    @Override
    public int getElement() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía. No se puede obtener el elemento.");
        }
        return this.top.value;
    }

    @Override
    public boolean isEmpty() {
        return this.top == null;
    }
}