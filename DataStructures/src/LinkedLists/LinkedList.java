/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LinkedLists;

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

    public int size() {
        return count;
    }

    public void start() {
        current = start;
    }

    public void advance() {
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
    }
}
