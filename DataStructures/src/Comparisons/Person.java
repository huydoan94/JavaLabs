/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comparisons;

/**
 *
 * @author dangh
 */
public class Person {

    private String firstName;
    private String lastName;
    private char middleInit;

    public Person() {
    }

    public Person(String firstName, String lastName, char middleInit) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInit = middleInit;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public char getMiddleInit() {
        return middleInit;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleInit(char middleInit) {
        this.middleInit = middleInit;
    }

    public String fullName() {
        return firstName + " " + middleInit + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        Person p = (Person) o;
        if (lastName.equals(p.getLastName()) && firstName.equals(p.getFirstName()) && middleInit == p.getMiddleInit()) {
            return true;
        } else {
            return false;
        }
    }
}
