package ArrayManager;

import Exceptions.InvalidArgumentException;
import Exceptions.NoItemsException;
import Exceptions.OutOfBoundsException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Huy
 */
public class ArrayManagerDriver {

    public static void main(String[] args) {

        ArrayManager am = new ArrayManager(5);

        try {
            try {
                am.remove(2);
            } catch (NoItemsException nie) {
                System.out.println(nie.getMessage());
            }

            am.add(12);
            am.add(34);
            am.add(54);
            am.add(13);
            am.add(9);
            am.add(99);
            am.print();
            System.out.println("---");
            am.insertAt(66, 2);
            am.print();
            System.out.println("---");
            try {
                am.remove(2);
            } catch (NoItemsException nie) {
                System.out.println(nie.getMessage());
            }
            am.print();
            System.out.println("---");
            am.printItems();
//            am.insertAt(0, 99);
            System.out.println("---");
            am.print();
            System.out.println("---");
            try {
                am.removeRange(2, 2);
                am.print();
            } catch (InvalidArgumentException iae) {
                System.out.println(iae.getMessage());
            }
            System.out.println("---");
            am.add(13);
            am.add(13);
            am.add(13);
            am.add(34);
            am.add(34);
            am.add(34);
            am.add(99);
            am.print();
            System.out.println("Counting occurances of 13: " + am.countOccurances(13));
            am.removeDuplicates();
            System.out.println("After remove dups");
            am.print();
            System.out.println("---");
            System.out.println("To linked list: ");
            am.convertToLinkedList().print();
            System.out.println("End");
        } catch (OutOfBoundsException oobe) {
            System.out.println(oobe.getMessage());
        }

//        int[] items = new int[5];
//        
//        items[0] = 12;
//        items[1] = 34;
//        items[2] = 54;
//        items[3] = 13;
//        items[4] = 9;
//        
//        for(int i = 0; i < items.length; i++) {
//            System.out.println(">>" + items[i]);
//        }
//        
//        // 1. create new array with new length
//        int[] temp = new int[items.length + 1];
//        // 2. Copy everything from original array to new array
//        System.arraycopy(items, 0, temp, 0, items.length);
//        // 3. Point the new array to the new array
//        items = temp;
//        // 4. add the new items
//        items[5] = 99;
    }
}
