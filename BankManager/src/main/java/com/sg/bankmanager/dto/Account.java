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
public abstract class Account {
    
    private final int ID;
    protected double balance = 0;
    
    public Account(int id) {
        this.ID = id;
    }

    // basic logic for depositing
    public void deposit(double money) {
        this.balance += money;
    }

    // basic logic for withdrawal
    public boolean withdrawal(double money) {
        if (money > getBalance()) {
            return false;
        } else {
            setBalance(getBalance() - money);
            return true;
        }
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getID() {
        return ID;
    }
}
