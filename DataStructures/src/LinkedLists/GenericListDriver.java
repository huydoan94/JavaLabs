/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LinkedLists;

/**
 *
 * @author dangh
 */
public class GenericListDriver {

    public static void main(String[] args) {
        GenericLinkedList<Integer> list = new GenericLinkedList<Integer>();

        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        list.add(11);

        list.print();
    }
}
