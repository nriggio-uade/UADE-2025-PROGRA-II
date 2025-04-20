package org.uade.structure.implementation.fixed;

import org.uade.structure.definition.LinkedListADT;
/**
 * Implementación Estática de la interfaz LinkedListADT.
 * Utilizamos un array de tamaño fijo para guardar elementos.
 */
public class StaticLinkedList implements LinkedListADT {

    // --- Atributos ---
    private int[] data; // array donde vamos a guardar elementos
    private int count; // Contador para saber cuántos elementos guardamos
    private static final int MAX_SIZE = 10; // tamaño del array como una constante. Lo inicializo en 10 como ejemplo

    // --- Constructor ---
    public StaticLinkedList() {
        this.data = new int[MAX_SIZE];  // Creo el array con el tamaño MAX_SIZE.
        this.count = 0; //Inicializo en xq no hay elementos guardados
    }

    // --- Métodos de la Interfaz ---

    @Override
    public void add(int value) {
        if (this.count >= MAX_SIZE) { //Revisamos si el array está lleno
            throw new RuntimeException("La lista está llena. No se puede agregar el elemento."); // Si está llena, no podemos agregar elementos. Lanzamos un error.
        }
        this.data[this.count] = value; // Si hay espacio, agregamos el elemento en la posición 'count'.
        this.count++; //sumamos el contador del array
    }

    @Override
    public void insert(int index, int value) {
        if (this.count >= MAX_SIZE) { //Si el array está lleno, lanzamos error.
            throw new RuntimeException("La lista está llena. No se puede insertar el elemento.");
        }
        if (index < 0 || index > this.count) { //Si el índice NO ESTÁ entre 0 y count, lanzo error.
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
        for (int i = this.count - 1; i >= index; i--) { //Muevo los elementos a la derecha, para hacer el espacio para el elemento nuevo
            this.data[i + 1] = this.data[i]; //muevo a i+1 el valor que está en i
        }
        this.data[index] = value; // Ya hicimos el espacio en la posición 'index', asignamos el nuevo valor ahí.
        this.count++; //incremento count ya que ahora tienen un elemento mas
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= this.count) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }

        // Para "eliminar" el elemento en 'index', movemos todos los elementos
        // que están a la derecha de 'index', una posición hacia la izquierda.
        for (int i = index; i < this.count - 1; i++) {
            // Movemos el elemento de 'i + 1' a la posición 'i'.
            this.data[i] = this.data[i + 1];
        }
        this.count--;//decremento count ya que ahora tienen un elemento menos
    }

    @Override
    public int get(int index) { //Si el índice NO ESTÁ entre 0 y count, lanzo error.
        if (index < 0 || index >= this.count) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
        return this.data[index]; //devuelvo valor que está en esa posición
    }

    @Override
    public int size() {
        return this.count; // El contador 'count' tiene la cantidad de elementos del array.
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0; // La lista está vacía si el contador de elementos es 0.
    }
}
