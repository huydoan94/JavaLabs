/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import Exceptions.InvalidArgumentException;
import Exceptions.NoItemsException;
import Exceptions.OutOfBoundsException;
import LinkedLists.DoublyLinkedList;
import java.util.Scanner;

/**
 *
 * @author dangh
 */
public class FrameworkProgram {

    /*
    * To change this license header, choose License Headers in Project Properties.
    * To change this template file, choose Tools | Templates
    * and open the template in the editor.
     */
    private Scanner input;
    private DoublyLinkedList list;

    public FrameworkProgram() {
        input = new Scanner(System.in);

    }

    public static void main(String[] args) {
        FrameworkProgram prog = new FrameworkProgram();
        prog.run();
    }

    public void run() {
        input = new Scanner(System.in);
        list = new DoublyLinkedList();

        int choice = 0;
        while (choice != 9) {
            choice = displayActions();
            executeAction(choice);
        }
    }

    public int displayActions() {
        System.out.println("1. View doubly linked list");
        System.out.println("2. View list backward");
        System.out.println("3. View current");
        System.out.println("4. Insert before");
        System.out.println("5. Insert after");
        System.out.println("6. Remove current");
        System.out.println("7. Remove range");
        System.out.println("8. Move current to index");
        System.out.println("9. Quit");
        System.out.print("--> ");
        int choice = input.nextInt();
        input.nextLine();
        return choice;
    }

    public void executeAction(int choice) {
        if (choice == 1) {
            actionStub1();
        } else if (choice == 2) {
            actionStub2();
        } else if (choice == 3) {
            actionStub3();
        } else if (choice == 4) {
            actionStub4();
        } else if (choice == 5) {
            actionStub5();
        } else if (choice == 6) {
            actionStub6();
        } else if (choice == 7) {
            actionStub7();
        } else if (choice == 8) {
            actionStub8();
        } else if (choice == 9) {
            quit();
        }
    }

    public void actionStub1() {
        list.printForward();
        System.out.println("Collection size is: " + list.size());
    }

    public void actionStub2() {
        list.printBackward();
        System.out.println("Collection size is: " + list.size());
    }

    public void actionStub3() {
        try {
            System.out.println("Current data is: " + list.getCurrent());
        } catch (NoItemsException nie) {
            System.out.println(nie.getMessage());
        }
    }

    public void actionStub4() {
        System.out.print("Enter your data: ");
        String data = input.nextLine();

        list.addBefore(data);
    }

    public void actionStub5() {
        System.out.print("Enter your data: ");
        String data = input.nextLine();

        list.addAfter(data);
    }

    public void actionStub6() {
        try {
            list.remove();
        } catch (NoItemsException nie) {
            System.out.println(nie.getMessage());
        }
    }

    public void actionStub7() {
        int start;
        int end;

        System.out.print("Enter the start index: ");
        start = input.nextInt();
        input.nextLine();

        System.out.print("Enter the end index (not include): ");
        end = input.nextInt();
        input.nextLine();

        try {
            list.removeRange(start, end);
        } catch (InvalidArgumentException iae) {
            System.out.println(iae.getMessage());
        }
    }

    public void actionStub8() {
        int index;
        System.out.print("Enter the index: ");
        index = input.nextInt();
        input.nextLine();

        try {
            list.current = list.start;
            for (int i = 0; i < index; i++) {
                list.advance();
            }
        } catch (OutOfBoundsException oobe) {
            System.out.println(oobe.getMessage());
        }
    }

    public void quit() {
        System.out.println("Exiting ....");
    }
}
