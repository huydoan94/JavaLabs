/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayManager;

import Exceptions.NoItemsException;
import Exceptions.OutOfBoundsException;
import java.util.Scanner;

/**
 *
 * @author dangh
 */
public class ArrayManagerProgram {

    private Scanner input;
    ArrayManager data;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ArrayManagerProgram amp = new ArrayManagerProgram();
        amp.run();

    }

    public ArrayManagerProgram() {
        Object[] numbers = {112, 12, 45, 76, 90, 99, 87, 6, 0};
        data = new ArrayManager(numbers);
        input = new Scanner(System.in);
    }

    public void run() {
        int choice = 0;
        while (choice != 7) {
            choice = displayActions();
            executeAction(choice);
        }
        System.out.println("Bye!");
    }

    public int displayActions() {
        System.out.println("1. Display all items");
        System.out.println("2. Display all items");
        System.out.println("3. Add an item");
        System.out.println("4. Add an item at position");
        System.out.println("5. Remove an Item");
        System.out.println("6. Get item at position");
        System.out.println("7. Exit");
        System.out.println("Please enter an option");

        int choice = input.nextInt();
        return choice;
    }

    public void executeAction(int choice) {
        if (choice == 1) {
            System.out.println("Number of items: " + getSize());
        } else if (choice == 2) {
            System.out.println("Items: ");
            data.printItems();
        } else if (choice == 3) {
            addItem();
        } else if (choice == 4) {
            addItemAtPos();
        } else if (choice == 5) {
            remove();
        } else if (choice == 6) {
            getElement();
        }
    }

    public void getElement() {
        System.out.println("Enter position of the Array: ");
        int position = input.nextInt();

        try {
            int item = (int) data.getItemAt(position);
            System.out.println("Value: " + item);

        } catch (OutOfBoundsException oobe) {
            System.out.println("Error: " + oobe.getMessage());
        }
    }

    public void remove() {
        System.out.println("Position to remove");
        int position = input.nextInt();

        try {
            data.remove(position);
        } catch (NoItemsException nie) {
            System.out.println(nie.getMessage());

        } catch (OutOfBoundsException oobe) {
            System.out.println("Error: " + oobe.getMessage());
        }
    }

    public void addItemAtPos() {
        System.out.println("Item to Add");
        int item = input.nextInt();
        System.out.println("Position to Add");
        int position = input.nextInt();

        try {
            data.insertAt(item, position);
            System.out.println("Value: " + item);

        } catch (OutOfBoundsException oobe) {
            System.out.println("Error: " + oobe.getMessage());
        }

    }

    public void addItem() {
        System.out.println("Item to add: ");
        int item = input.nextInt();
        data.add(item);
    }

    public int getSize() {
        return data.size();
    }
}
