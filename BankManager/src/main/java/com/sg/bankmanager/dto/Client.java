/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bankmanager.dto;

import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */
public class Client {
    private final int ID;
    private int pin;
    private boolean isLocked;
    private List<Account> accounts;
    
    public Client(int id, int pin, List<Account> accounts) {
        this.ID = id;
        this.pin = pin;
        this.accounts = accounts;
        this.isLocked = false;
    }

    public int getId() {
        return this.ID;
    }

    public int getPin() {
        return pin;
    }
    
    public void setPin(int pin) {
        this.pin = pin;
    }

    public boolean isIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Account getAccount(int accountID) {
        for(Account a:this.getAccounts()) {
            if(a.getID() == accountID)
                return a;
        }
        return null;
    }
    
    public List<Account> getAccounts() {
        return accounts;
    }
}
