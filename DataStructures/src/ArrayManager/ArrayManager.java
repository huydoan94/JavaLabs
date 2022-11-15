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
            resize();
            items[count] = o;
            count++;
        }
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
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void insertAt(Object o, int pos) {
        if (count >= items.length) {
            resize();
        }

        System.arraycopy(items, pos, items, pos + 1, count - pos);
        items[pos] = o;
        count++;
    }

}
