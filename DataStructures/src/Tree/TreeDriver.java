/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tree;

/**
 *
 * @author dangh
 */
public class TreeDriver {

    public static void main(String[] args) {
        Tree t = new Tree();
        for (int i = 0; i < 30; i++) {
            int num = (int) (Math.random() * 200);
            System.out.println(num + " ");
            t.add(num);
        }

        System.out.println("\nPrintTree:");
        t.printTree();

        System.out.println("-----------------\n");
        System.out.println("Max is: " + t.findMax());
    }
}
