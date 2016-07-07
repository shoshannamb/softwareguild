/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bankmanager.dto;

/**
 *
 * @author apprentice
 */
public class CheckingAccount extends Account {

    public CheckingAccount(int id, double currentBalance) {
        super(id);
        this.balance = currentBalance;
    }

    @Override
    public boolean withdrawal(double money) {
        if (this.balance < 0) // can't withdraw if already overdrawn
        {
            return false;
        } else if ((this.balance - money) < 0) {
            this.balance -= (money + 35); // overdraft fee of $35
        } else {
            this.balance -= money;
        }
        return true;
    }
}
