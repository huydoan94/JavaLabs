/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LinkedLists;

/**
 *
 * @author dangh
 * @param <T>
 */
public class GenericLinkedList<T> {

    private GNode<T> items;
    private GNode<T> current;
    private GNode<T> printCurrent;

    public void add(T item) {
        if (items == null) {
            items = new GNode(item);
            current = items;
        } else {
            current.next = new GNode(item);
            current = current.next;
        }
    }

    public void print() {
        GNode<T> temp = items;

        while (temp != null) {
            System.out.println(temp.getData());
            temp = temp.next;
        }
    }

    public void recursivePrint() {
        if (printCurrent == null) {
            printCurrent = items;
        } else {
            printCurrent = printCurrent.next;
        }

        if (printCurrent == null) {
            return;
        }

        System.out.println(printCurrent.getData());
        recursivePrint();
    }
}

final class GNode<T> {

    private T data;
    public GNode<T> next;

    public GNode() {
    }

    public GNode(T data) {
        this.setData(data);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public GNode<T> getNext() {
        return next;
    }

    public void setNext(GNode<T> next) {
        this.next = next;
    }
}
