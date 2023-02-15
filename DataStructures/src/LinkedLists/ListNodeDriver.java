/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LinkedLists;

/**
 *
 * @author dangh
 */
public class ListNodeDriver {

    public static void main(String[] args) {
        ListNode a = new ListNode("I'm a");
        ListNode b = new ListNode("I'm b");
        ListNode c = new ListNode("I'm c");
        ListNode d = new ListNode("I'm d");
        ListNode e = new ListNode("I'm e");

        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;

        ListNode temp = a;
        while (temp != null) {
            System.out.println("Data: " + temp.getData());
            temp = temp.next;
        }

        ListNode f = new ListNode("I'm an intruder");
        f.next = d;
        c.next = f;

        System.out.println("-----------------------------");
        temp = a;
        while (temp != null) {
            System.out.println("Data: " + temp.getData());
            temp = temp.next;
        }
    }
}
