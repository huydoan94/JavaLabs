/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Recursion;

import LinkedLists.GenericLinkedList;
import LinkedLists.LinkedList;

/**
 *
 * @author dangh
 */
public class Recursion {

    public static void main(String[] args) {
        System.out.println("Sum: " + sumAll(5));
        System.out.println("Factorial: " + factorial(5));
        System.out.println("My Power: " + myPow(2, 6));
        System.out.println("Palindrome: " + isPalindrome("racecara"));

        GenericLinkedList<String> list = new GenericLinkedList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.recursivePrint();

        LinkedList testList = new LinkedList();
        testList.add(12);
        testList.add(23);
        testList.add(34);
        testList.reversePrint();
        
        System.out.println(myFib(12));
    }

    public static int sumAll(int n) {
        if (n == 1) {
            return n;
        } else {
            return n + sumAll(n - 1);
        }
    }

    public static int factorial(int n) {
        if (n == 1) {
            return n;
        } else {
            return n * factorial(n - 1);
        }
    }

    public static int myPow(int a, int b) {
        if (b == 0) {
            return 1;
        } else if (b == 1) {
            return a;
        } else {
            return a * myPow(a, b - 1);
        }
    }

    public static boolean isPalindrome(String s) {
        return isPalindrome(s, 0, s.length() - 1);
    }

    public static boolean isPalindrome(String s, int start, int end) {
        if (s.charAt(start) != s.charAt(end)) {
            return false;
        } else if (start > end) {
            return true;
        } else {
            return isPalindrome(s, start + 1, end - 1);
        }
    }

    public static int fibonacci(int max_n) {
        int n = 1;
        int val_minus1 = 1;
        int val_minus2 = 0;
        if (max_n == 0) {
            System.out.println("0");
            return 0;
        } else {
            System.out.print("0, 1, ");
            if (max_n == 1) {
                System.out.println();
                return 1;
            }
            return fibonacci(n, val_minus1, val_minus2, max_n, 1);
        }
    }

    public static int fibonacci(int n, int val_minus1, int val_minus2, int max_n, int value) {
        if (n == max_n) {
            return value;
        } else {
            value = val_minus1 + val_minus2;
            System.out.print(value + ", ");
            n++;
            return fibonacci(n, value, val_minus1, max_n, value);
        }
    }
    
    public static int myFib(int n) {
        switch (n) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return myFib(n - 1) + myFib(n - 2);
        }
    }

    public static void indentoTron(int n) {
        indentoTron(n, 1);
    }

    public static void indentoTron(int n, int i) {
        if (i > n) {
            return;
        } else {
            printSpaces(i);
            System.out.println("this was wtritten by call number " + i);
            indentoTron(n, i + 1);
            printSpaces(i);
            System.out.println("This was Also written by Call number " + i);
        }
    }

    public static void printSpaces(int spaces) {
        if (spaces == 0) {
            return;
        } else {
            System.out.print(" ");
            printSpaces(spaces - 1);
        }
    }
}
