/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LinkedLists;

import Exceptions.NoItemsException;
import Exceptions.OutOfBoundsException;

/**
 *
 * @author dangh
 */
public class LinkedListDriver {

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        System.out.println(list.size());
        list.addAfter("a-1");
        list.add("a");
        list.add("b");
        list.addAfter("b.5");
        try {
            list.removeCurrent();
        } catch (NoItemsException nie) {
            System.out.println("ERROR: " + nie.getMessage());
        }
        list.add("c");
        list.add("d");
        list.add("e");
        list.addAfter("f");
        list.print();
        System.out.println(list.size());
        System.out.println("------------------------");
        list.start();
        System.out.println("Current: " + list.getCurrent());
        try {
            list.advance();
        } catch (OutOfBoundsException oobe) {
            System.out.println("ERROR: " + oobe.getMessage());
        }
        System.out.println("Current: " + list.getCurrent());
    }
}
