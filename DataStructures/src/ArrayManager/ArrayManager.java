/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayManager;

import Exceptions.InvalidArgumentException;
import Exceptions.NoItemsException;
import Exceptions.OutOfBoundsException;
import LinkedLists.LinkedList;

/**
 *
 * @author Huy
 */
public class ArrayManager {

    private Object[] items;
    private int count;

    public ArrayManager() {
        items = new Object[10];
        count = 0;
    }

    public ArrayManager(Object[] items) {
        this.items = items;
        count = items.length;
    }

    public ArrayManager(int size) {
        items = new Object[size];
        count = 0;
    }

    public void add(Object o) {
        if (count >= items.length) {
            resize();
        }
        items[count] = o;
        count++;
    }

    public void resize() {
        Object[] temp = new Object[items.length + 10];
        System.arraycopy(items, 0, temp, 0, count);
        items = temp;
    }

    public void print() {
        for (int i = 0; i < count; i++) {
            System.out.println(items[i]);
        }
    }

    public void printItems() {
        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i]);
        }
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void insertAt(Object o, int pos) throws OutOfBoundsException {
        if (pos < 0 || pos > count) {
            throw new OutOfBoundsException("Cannot retrieve item outside of collection bounds");
        }

        if (count >= items.length) {
            resize();
        }

        System.arraycopy(items, pos, items, pos + 1, count - pos);
        items[pos] = o;
        count++;
    }

    public void remove(int pos) throws NoItemsException, OutOfBoundsException {
        if (count == 0) {
            throw new NoItemsException("Remove Failed. The collection is empty");
        }

        if (pos < 0 || pos > count) {
            throw new OutOfBoundsException("Cannot retrieve item outside of collection bounds");
        }

        System.arraycopy(items, pos + 1, items, pos, count - pos);
        count--;
    }

    public Object getItemAt(int pos) throws OutOfBoundsException {
        if (pos < 0 || pos > count) {
            throw new OutOfBoundsException("Cannot retrieve item outside of collection bounds");
        }

        return items[pos];
    }

    public int find(Object o) {
        for (int i = 0; i < count; i++) {
            if (items[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    public int findLargest() {
        int pos = 0;
        for (int i = 0; i < count; i++) {

            Comparable c = (Comparable) items[i];

            if (c.compareTo(items[pos]) > 0) {
                pos = i;
            }
        }
        return pos;
    }

    public void removeRange(int start, int end) throws InvalidArgumentException {
        if (start < 0 || end > count || end < start) {
            throw new InvalidArgumentException("Invalid range!");
        }
        int newLength = count - (end - start) - 1;
        Object[] newArr = new Object[newLength + 10];
        System.arraycopy(items, 0, newArr, 0, start);
        System.arraycopy(items, end + 1, newArr, start, count - end - 1);
        items = newArr;
        count = newLength;
    }

    public int countOccurances(Object item) {
        int occurances = 0;
        for (int i = 0; i < count; i++) {
            if (items[i].equals(item)) {
                occurances++;
            }
        }
        return occurances;
    }

    public void removeDuplicates() {
        ArrayManager newArr = new ArrayManager();
        for (int i = 0; i < count; i++) {
            if (newArr.find(items[i]) == -1) {
                newArr.add(items[i]);
            }
        }
        items = newArr.items;
        count = newArr.count;
    }

    public LinkedList convertToLinkedList() {
        LinkedList list = new LinkedList();
        for (int i = 0; i < count; i++) {
            list.add(items[i]);
        }
        return list;
    }
}
