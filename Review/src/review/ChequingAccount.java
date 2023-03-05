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
public class ChequingAccount extends BankAccount {

    private float overDraftPenalty;
    private float overDraftLimit;

    public ChequingAccount(float overDraftPenalty, float overDraftLimit,
            int accountNumber, float balance, Customer customer) {
        super(accountNumber, balance, customer);
        this.overDraftPenalty = overDraftPenalty;
        this.overDraftLimit = overDraftLimit;
    }

    public ChequingAccount() {
    }

    public float getOverDraftPenalty() {
        return overDraftPenalty;
    }

    public void setOverDraftPenalty(float overDraftPenalty) {
        this.overDraftPenalty = overDraftPenalty;
    }

    public float getOverDraftLimit() {
        return overDraftLimit;
    }

    public void setOverDraftLimit(float overDraftLimit) {
        this.overDraftLimit = overDraftLimit;
    }

    public void withdraw(float amount) throws Exception {

        if (amount <= getBalance()) {
            setBalance(getBalance() - amount);
        } else {
            if (amount <= getBalance() + overDraftLimit + overDraftPenalty) {
                setBalance(getBalance() - (amount + overDraftPenalty));
            } else {
                throw new Exception();
            }
        }

    }
}
