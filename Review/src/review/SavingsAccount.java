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
public class SavingsAccount extends BankAccount {

    private static float ACTIVITY_PENALTY = 5;
    private static int ACTIVITY_LIMIT = 10;

    private float minBalance;
    private float withdrawCount;

    public SavingsAccount(float minBalance, float withdrawCount,
            int accountNumber, float balance, Customer customer) {
        super(accountNumber, balance, customer);
        this.minBalance = minBalance;
        this.withdrawCount = withdrawCount;
    }

    public SavingsAccount() {
    }

    public float getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(float minBalance) {
        this.minBalance = minBalance;
    }

    public float getWithdrawCount() {
        return withdrawCount;
    }

    public void setWithdrawCount(float withdrawCount) {
        this.withdrawCount = withdrawCount;
    }

    public void withdraw(float amount) throws Exception {

        float fee = 0;
        if (getBalance() < minBalance && withdrawCount > ACTIVITY_LIMIT) {
            fee = ACTIVITY_PENALTY;
        }
        if ((amount + fee) <= getBalance()) {
            setBalance(getBalance() - (amount + fee));
            withdrawCount++;
        } else {
            throw new Exception();
        }
    }
}
