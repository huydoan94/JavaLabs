/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LinkedLists;

/**
 *
 * @author dangh
 */
public class LinkedListDriver {

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        System.out.println(list.size());
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.print();
        System.out.println(list.size());
        System.out.println("------------------------");
        list.start();
        System.out.println("Current: " + list.getCurrent());
        list.advance();
        System.out.println("Current: " + list.getCurrent());
    }
}
