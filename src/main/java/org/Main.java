package org;

import org.uade.structure.implementation.dynamic.DynamicLinkedList;

public class Main {
    public static void main(String[] args) {
        // Crear las listas
        DynamicLinkedList list1 = new DynamicLinkedList();
        list1.add(1);
        list1.add(3);
        list1.add(5);

        DynamicLinkedList list2 = new DynamicLinkedList();
        list2.add(2);
        list2.add(4);
        list2.add(6);
        list2.add(8);

        // Intercalar las listas
        DynamicLinkedList result = DynamicLinkedList.intercalar(list1, list2);

        // Mostrar los resultados
        Node current = result.head;
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
    }
}