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
public class LinkedList {

    private ListNode start;
    private ListNode current;
    private int count;

    public LinkedList() {
    }

    public Object getCurrent() {
        return current.getData();
    }

    public void setCurrent(ListNode current) {
        this.current = current;
    }

    public void add(Object item) {
        ListNode node = new ListNode(item);
        if (start == null) {
            start = node;
            current = start;
        } else {
            current.next = node;
            current = current.next;
        }

        count++;
    }

    public void print() {
        ListNode temp = start;
        while (temp != null) {
            System.out.println("Data: " + temp.getData());
            temp = temp.next;
        }
    }

    public void reversePrint() {
        String[] printouts = new String[count];
        ListNode temp = start;
        int index = 0;
        while (temp != null) {
            printouts[index] = ("Data: " + temp.getData());
            temp = temp.next;
            index++;
        }
        for (int i = count - 1; i >= 0; i--) {
            System.out.println(printouts[i]);
        }
    }

    public int size() {
        return count;
    }

    public void start() {
        current = start;
    }

    public void advance() throws OutOfBoundsException {
        if (current.next == null) {
            throw new OutOfBoundsException("You have gone outside the bounds of the list");
        }
        current = current.next;
    }

    public void addAfter(Object item) {
        ListNode node = new ListNode(item);
        if (start == null) {
            start = node;
            current = node;
        } else {
            node.next = current.next;
            current.next = node;
            current = current.next;
        }
        count++;
    }

    public void removeCurrent() throws NoItemsException {
        if (start == null) {
            throw new NoItemsException("No Items in Collection to Delete");
        } else if (current == start) {
            start = start.next;
            count--;
        } else {
            ListNode temp = start;
            while (temp.next != current) {
                temp = temp.next;
            }
            temp.next = temp.next.next;
            current = temp;
            count--;
        }
    }

    public void addBefore(Object item) {
        ListNode node = new ListNode(item);

        if (start == null) {
            start = node;
            current = start;
        } else if (current == start) {
            node.next = current;
            start = node;
            current = node;
        } else {
            ListNode temp = start;
            while (temp.next != current) {
                temp = temp.next;
            }
            node.next = current;
            temp.next = node;
        }
        count++;
    }

    public Object getItemAt(int position) throws OutOfBoundsException {
        if (position < 0 || position > count) {
            throw new OutOfBoundsException("OutAbounds!!!");
        }

        ListNode temp = start;
        for (int i = 0; i < position; i++) {
            temp = temp.next;
        }
        return temp.getData();
    }

    public Object find(Object o) {
        ListNode temp = start;
        while (temp != null) {
            if (temp.getData().equals(o)) {
                return temp.getData();
            }

            temp = temp.next;
        }

        return null;
    }

    public Object findLargest() {
        Object largest = null;
        ListNode temp = start;
        while (temp != null) {
            if (largest == null) {
                largest = temp.getData();
            } else {
                Comparable c = (Comparable) largest;

                if (c.compareTo(temp.getData()) > 0) {
                    largest = temp.getData();
                }
            }

            temp = temp.next;
        }

        return largest;
    }
}
