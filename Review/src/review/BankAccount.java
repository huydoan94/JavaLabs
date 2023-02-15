/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package review;

/**
 *
 * @author Huy
 */
public abstract class BankAccount {

    private int accountNumber;
    private float balance;
    private Customer customer;

    public BankAccount(int accountNumber, float balance, Customer customer) {
        setAccountNumber(accountNumber);
        setBalance(balance);
        setCustomer(customer);
    }

    public BankAccount() {
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void deposit(float amount) {
        balance += amount;
    }

    public void transfer(BankAccount other, float amount) throws Exception {

        try {
            this.withdraw(amount);
            other.deposit(amount);
        } catch (Exception e) {

            //System.out.println("insufficient funds. ");
            throw e;
        }

    }

    public abstract void withdraw(float amount) throws Exception;

}
