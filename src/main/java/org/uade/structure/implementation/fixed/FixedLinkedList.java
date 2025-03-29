package org.uade.structure.implementation.fixed;

import org.uade.structure.definition.LinkedListADT;

public class FixedLinkedList implements LinkedListADT {
    private int[] elements;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public FixedLinkedList() {
        this.elements = new int[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public void add(int value) {
        if (size >= elements.length) {
            throw new ArrayIndexOutOfBoundsException("Lista llena. No se puede agregar más elementos.");
        }
        elements[size++] = value;
    }

    @Override
    public void insert(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites.");
        }
        if (size >= elements.length) {
            throw new ArrayIndexOutOfBoundsException("Lista llena. No se puede insertar el valor.");
        }
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = value;
        size++;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites.");
        }
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
    }

    @Override
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites.");
        }
        return elements[index];
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
    public static FixedLinkedList intercalar(FixedLinkedList list1, FixedLinkedList list2) {
        FixedLinkedList result = new FixedLinkedList();
        int i = 0, j = 0;

        // Intercalamos elementos de ambas listas
        while (i < list1.size() && j < list2.size()) {
            result.add(list1.get(i++));
            result.add(list2.get(j++));
        }

        // Añadimos elementos restantes de la lista más larga
        while (i < list1.size()) {
            result.add(list1.get(i++));
        }

        while (j < list2.size()) {
            result.add(list2.get(j++));
        }

        return result;
    }
}