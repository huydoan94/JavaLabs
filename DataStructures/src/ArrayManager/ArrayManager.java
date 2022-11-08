/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayManager;

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
        if (count < items.length) {
            items[count] = o;
            count++;
        } else {
            Object[] temp = new Object[items.length + 10];
            System.arraycopy(items, 0, temp, 0, count);
            items = temp;
            items[count] = o;
            count++;
        }
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

}
