/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comparisons;

import ArrayManager.ArrayManager;

/**
 *
 * @author dangh
 */
public class ComparisonDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        Person p1 = new Person("John", "Doe", 'T');
//        Person p2 = new Person("John", "Doe", 'T');
//        if(p1.equals(p2)){
//            System.out.println("Equal");
//        }
//        else{
//            System.out.println("Not Equal");
//        }
//        
//        System.out.println("p1:" + p1);
//        System.out.println("p2:" + p2);
//        
//        if(p1==p2){
//            System.out.println("Equal");
//        }
//        else{
//            System.out.println("Not Equal");
//        }
//        
//        System.out.println("p1:" + p1);
//        System.out.println("p2:" + p2);
        Widget[] widgets = new Widget[4];
        widgets[0] = new Widget(1, "Thingy", 12);
        widgets[1] = new Widget(2, "Dohickey", 222);
        widgets[2] = new Widget(3, "Thingamajing", 112);
        widgets[3] = new Widget(4, "Stuff", 6);

        ArrayManager am = new ArrayManager(widgets); // create array and passing widgets array

        Widget item = new Widget(3, "Thingamajig", 112); // item is the widget 3

        System.out.println("Item found at the position: " + am.find(item)); // call that item and it serach in widgets array
        System.out.println("Largest Item found at position: " + am.findLargest());
    }
}
