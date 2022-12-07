/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LinkedLists;

import Exceptions.OutOfBoundsException;

/**
 *
 * @author dangh
 */
public class DoublyLinkedListDriver {

    public static void main(String[] args) {
        DoublyLinkedList testList = new DoublyLinkedList();
        for (int i = 0; i < 20; i++) {
            testList.addAfter(i);
        }

        testList.printForward();
        System.out.println(testList.current.getData());
        System.out.println("-------------------------");

        try {
            testList.removeRange(5, 20);
        } catch (OutOfBoundsException oobe) {
            System.out.println(oobe.getMessage());
        }

        testList.printForward();
        System.out.println(testList.current.getData());
        System.out.println(testList.start.getData());
        System.out.println(testList.end.getData());
        System.out.println(testList.size());
    }
}
