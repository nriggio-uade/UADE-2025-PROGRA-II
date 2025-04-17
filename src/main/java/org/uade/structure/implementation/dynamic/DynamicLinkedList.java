package org.uade.structure.implementation.dynamic;

import org.uade.structure.definition.LinkedListADT;

/**
 * Implementación Dinámica (basada en Nodos Enlazados) de la interfaz LinkedListADT.
 * Utiliza nodos para conectar entre sí los elementos.
 */
public class DynamicLinkedList implements LinkedListADT {

    // --- Clase interna Nodo ---
    private static class Node {
        int value;      // Valor que guardamos en el nodo.
        Node next;      // adonde apunta al siguiente nodo.

        // Constructor del nodo
        Node(int value) {
            this.value = value;
            this.next = null; // no apuntamos a ningún lado.
        }
    }

    // --- Atributos ---

    private Node head; //apunta al nodo anterior
    private int count; //contador para saber la cantidad de elementos (o nodos) de la lista

    // --- Constructor ---
    public DynamicLinkedList() {
        this.head = null; // No hay nodos al principio.
        this.count = 0;   // La lista está vacía.
    }

    // --- Métodos de la Interfaz ---

    @Override
    public void add(int value) { //el add agrega un nodo al final de la lista
        Node newNode = new Node(value); //Creamos el nuevo nodo con el value dado

        if (this.head == null) { // Si no hay un nodo inicial, lo asignamos como inicial
            this.head = newNode;
        } else { //Si ya hay nodos, tenemos que encontrar el último y apuntarlo al nuevo nodo
            Node current = this.head; //recorremos los nodos desde el primero
            while (current.next != null) { //si el "next" no es nulo, está apuntando a otro nodo
                current = current.next; // Pasamos al siguiente nodo
            }
            current.next = newNode; //apuntamos el último nodo, al nuevo último.
        }
        this.count++; //incrementamos conut ya que hay un nuevo elemento en la lista
    }

    @Override
    public void insert(int index, int value) {
        if (index < 0 || index > this.count) { //Si el array está lleno, lanzamos error.
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }

        Node newNode = new Node(value); //creamos el nuevo nodo a insertar

        if (index == 0) { //en caso de agregarlo al inicio
            newNode.next = this.head; //al next del nuevo nodo, le pasamos el nodo que era el inicial.
            this.head = newNode; //y el nuevo nodo lo convertimos en el inicial
        } else {
            Node previous = this.head; //tenemos que apuntar el nodo anterior al nuevo
            for (int i = 0; i < index - 1; i++) {
                previous = previous.next;
            }
            newNode.next = previous.next;
            previous.next = newNode;
        }
        this.count++;
    }

    @Override
    public void remove(int index) {

        if (index < 0 || index >= this.count) { //Si el array está lleno, lanzamos error.
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
        if (index == 0) { //en caso de eliminar el primer elemento
            this.head = this.head.next; //el nuevo elemento inicial es el nodo que seguía al primero
        } else {
            Node previous = this.head;

            for (int i = 0; i < index - 1; i++) {//Necesitamos llegar al nodo anterior al que queremos borrar
                                                 // Avanzamos 'index - 1' veces.
                previous = previous.next;
            }
            previous.next = previous.next.next; // Ahora 'previous' es el nodo justo antes del que queremos eliminar.       // El nodo a eliminar es 'previous.next'.
                                                // Hacemos que 'previous' se salte el nodo a eliminar y apunte directamente al siguiente de éste.
                                                // (Desenganchamos el vagón 'previous.next' y enganchamos 'previous' con 'previous.next.next').
        }
        this.count--;  //Decrementamos el contador.
    }

    @Override
    public int get(int index) {
        if (index < 0 || index >= this.count) {//Si el array está lleno, lanzamos error.
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }

        Node current = this.head;  // Empezamos desde el primer nodo

        for (int i = 0; i < index; i++) {// Avanzamos 'index' veces para llegar al nodo deseado.
            current = current.next; // Pasamos al siguiente nodo.
        }
        // Cuando salimos del bucle, 'current' es el nodo en la posición 'index'.
        // Devolvemos el valor guardado en ese nodo.
        return current.value;
    }

    @Override
    public int size() {
        return this.count; // El contador 'count' siempre lleva la cuenta.
    }


    @Override
    public boolean isEmpty() {
        // La lista está vacía si 'head' es null (o si count es 0).
        return this.head == null;
    }
}
