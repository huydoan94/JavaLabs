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
public class DoublyLinkedList {

    public ListNode2Way start;
    public ListNode2Way current;
    public ListNode2Way end;
    public int count;

    public DoublyLinkedList() {
    }

    public DoublyLinkedList(Object[] items) {
        for (int i = 0; i < items.length; i++) {
            addBefore(items[i]);
        }
    }

    public void addBefore(Object item) {
        ListNode2Way newItem = new ListNode2Way();
        newItem.setData(item);

        if (count == 0) {
            start = newItem;
            current = newItem;
            end = newItem;
        } else {
            newItem.previous = current.previous;
            newItem.next = current;

            // if previous of this new item is null
            // meaning that this is the start of the list
            // set start to this item
            if (newItem.previous == null) {
                start = newItem;
            } else {
                newItem.previous.next = newItem;
            }

            // if next of this new item is null
            // meaning that this is the end of the list
            // set end to this item
            if (newItem.next == null) {
                end = newItem;
            } else {
                newItem.next.previous = newItem;
            }

            try {
                previous();
            } catch (OutOfBoundsException oobe) {
                System.out.println(oobe.getMessage());
            }
        }

        count++;
    }

    public void addAfter(Object item) {
        ListNode2Way newItem = new ListNode2Way();
        newItem.setData(item);

        if (count == 0) {
            start = newItem;
            current = newItem;
            end = newItem;
        } else {
            newItem.next = current.next;
            newItem.previous = current;

            // if previous of this new item is null
            // meaning that this is the start of the list
            // set start to this item
            if (newItem.previous == null) {
                start = newItem;
            } else {
                newItem.previous.next = newItem;
            }

            // if next of this new item is null
            // meaning that this is the end of the list
            // set end to this item
            if (newItem.next == null) {
                end = newItem;
            } else {
                newItem.next.previous = newItem;
            }

            try {
                advance();
            } catch (OutOfBoundsException oobe) {
                System.out.println(oobe.getMessage());
            }

        }

        count++;
    }

    public int size() {
        return count;
    }

    public Object getCurrent() throws NoItemsException {
        if (count == 0) {
            throw new NoItemsException("The list is empty!");
        }

        return current.getData();
    }

    public void remove() throws NoItemsException {
        if (count == 0) {
            throw new NoItemsException("The list is empty!");
        }

        ListNode2Way prevItem = current.previous;
        ListNode2Way nextItem = current.next;

        // Unlink current out of List
        current.next = null;
        current.previous = null;

        if (prevItem != null) {
            prevItem.next = nextItem;
            current = prevItem;
        }

        if (nextItem != null) {
            nextItem.previous = prevItem;
            current = nextItem;
        }

        if (current.next == null) {
            end = current;
        }

        if (current.previous == null) {
            start = current;
        }

        count--;
    }

    public void removeRange(int start, int end) throws OutOfBoundsException {
        // This mean delete whole list
        if (start == 0 && end == count) {
            count = 0;
            this.start = null;
            current = null;
            this.end = null;
            return;
        }

        // Invalid arguments
        if (start < 0 || end > count || end <= start) {
            throw new OutOfBoundsException("Invalid range!");
        }

        ListNode2Way pointer = this.start;
        ListNode2Way prevItem = null;
        ListNode2Way nextItem = null;

        for (int cursor = 0; cursor < end; cursor++) {
            if (cursor == start) {
                prevItem = pointer.previous;
            }

            if (cursor == end - 1) {
                nextItem = pointer.next;
            }

            try {
                // if current inside remove range, point current to the left of removed data
                if (cursor >= start && pointer.getData().equals(getCurrent())) {
                    current = prevItem;
                }
            } catch (NoItemsException nie) {
                System.out.println(nie.getMessage());
            }

            pointer = pointer.next;
        }

        // This mean this is start of list
        if (prevItem == null) {
            this.start = nextItem;
        } else {
            prevItem.next = nextItem;
        }

        // This mean this is end of list
        if (nextItem == null) {
            this.end = prevItem;
        } else {
            nextItem.previous = prevItem;
        }

        // In case current was moved out of bounds
        if (current == null) {
            current = this.start;
        }

        count -= (end - start);
    }

    public void advance() throws OutOfBoundsException {
        if (current.next != null) {
            current = current.next;
        } else {
            throw new OutOfBoundsException("Current pointer is at the end of list!");
        }
    }

    public void previous() throws OutOfBoundsException {
        if (current.previous != null) {
            current = current.previous;
        } else {
            throw new OutOfBoundsException("Current pointer is at the start of list!");
        }
    }

    public void printForward() {
        ListNode2Way pointer = start;
        while (pointer != null) {
            System.out.println("Item data: " + pointer.getData());
            pointer = pointer.next;
        }
    }

    public void printBackward() {
        ListNode2Way pointer = end;
        while (pointer != null) {
            System.out.println("Item data: " + pointer.getData());
            pointer = pointer.previous;
        }
    }
}
