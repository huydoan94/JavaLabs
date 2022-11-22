/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package review;

import java.util.Scanner;

/**
 *
 * @author Huy
 */
public class Review {

    private Scanner input;
    private BankAccount[] accounts;
    private Customer[] customers;

    public static void main(String[] args) {
        Review bank = new Review();
        bank.initData();
        bank.run();
    }

    public Review() {
        input = new Scanner(System.in);
    }

    public void initData() {

        customers = new Customer[3];
        customers[0] = new Customer("Bob", "Smith", 'J', 123);
        customers[1] = new Customer("Sue", "Shard", 'S', 234);
        customers[2] = new Customer("Pat", "Jones", 'R', 345);

        accounts = new BankAccount[4];
        accounts[0] = new ChequingAccount(2, 500, 111, 1100.50f, customers[0]);
        accounts[1] = new ChequingAccount(2, 500, 222, 1400.55f, customers[1]);
        accounts[2] = new SavingsAccount(100, 8, 333, 1370.55f, customers[0]);
        accounts[3] = new SavingsAccount(200, 5, 444, 1899.55f, customers[2]);

    }

    public Customer getCustomerById(int id) {

        Customer c = null;
        for (int i = 0; i < customers.length; i++) {
            if (customers[i].getCustomerID() == id) {

                c = customers[i];
            }
        }
        return c;

    }

    public void displayAccountsForCustomer(Customer c) {

        int numFound = 0;
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].getCustomer().getCustomerID() == c.getCustomerID()) {
                System.out.println("Account Number: " + accounts[i].getAccountNumber());
                numFound++;
            }
        }
        if (numFound == 0) {
            System.out.println("No accounts found for this customer");
        }
    }

    public BankAccount getBankAccountByAccountNumber(int acctId) {

        BankAccount b = null;
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].getAccountNumber() == acctId) {
                b = accounts[i];
            }
        }
        return b;
    }

    public int showActionMenu() {

        int option = 0;
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. Add Customer");
        System.out.println("4. Quit");
        System.out.print("->");
        option = input.nextInt();
        return option;
    }

    public void executeAction(int action, BankAccount ba) {
        if (action == 1) {
            System.out.print("Deposit Amount: ");
            float amount = input.nextFloat();
            ba.deposit(amount);
            System.out.print("End Balance:" + ba.getBalance());
        } else if (action == 2) {
            System.out.print("Withdraw Amount: ");
            float amount = input.nextFloat();
            try {
                ba.withdraw(amount);  //Polymorphic method invocation
            } catch (Exception e) {
                System.out.println("Insufficient funds");
            }
            System.out.print("End Balance:" + ba.getBalance());
        } else if (action == 3) {
            System.out.print("Enter target account no.: ");
            int acctID = input.nextInt();
            BankAccount targetBa = getBankAccountByAccountNumber(acctID);
            if (targetBa != null) {
                System.out.println("Target Balance: " + targetBa.getBalance());
                System.out.print("How much to transfer to this account: ");
                float amount = input.nextFloat();

                try {
                    ba.transfer(targetBa, amount);
                } catch (Exception e) {
                    System.out.println("Insufficient funds to transfer");
                }

                System.out.print("End Source Balance:" + ba.getBalance());
                System.out.print("End Target Balance:" + targetBa.getBalance());
            }
        }
    }

    public void run() {

        System.out.print("Enter customer Id:");
        int custId = input.nextInt();
        Customer c = getCustomerById(custId);
        System.out.println("Customer: " + c.getLastName() + ", " + c.getFirstName());

        int action = -1;
        while (action != 4) {
            displayAccountsForCustomer(c);
            System.out.print("Select an account:");
            int acctId = input.nextInt();
            BankAccount b = getBankAccountByAccountNumber(acctId);
            System.out.println("Current Balance: " + b.getBalance());

            //executeAction(showActionMenu(), b);
            action = showActionMenu();
            executeAction(action, b);
        }
    }

}
