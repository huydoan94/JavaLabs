/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tree;

import java.util.Scanner;

/**
 *
 * @author dangh
 */
public class TreeDriver {

    public static void main(String[] args) {
        Tree t = new Tree();
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < 30; i++) {
            int num = (int) (Math.random() * 200);
            System.out.println(num + " ");
            t.add(num);
        }

        System.out.println("\nPrintTree:");
        t.printTree();

    }
}
