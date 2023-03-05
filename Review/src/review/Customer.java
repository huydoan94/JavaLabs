/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package review;

import review.Person;

/**
 *
 * @author Huy
 */
public class Customer extends Person {

    private int CustomerID;

    public Customer(String firstName, String lastName, char middleInit, int customerID) {
        super(firstName, lastName, middleInit);
        setCustomerID(customerID);
    }

    public Customer() {
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

}
