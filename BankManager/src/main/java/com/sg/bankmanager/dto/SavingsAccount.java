/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bankmanager.dto;

import java.time.LocalDate;

/**
 *
 * @author apprentice
 */
public class SavingsAccount extends Account {
    // could use a java Date object
    protected final LocalDate DATE_OPENED;
    protected double yearlyInterestRate;

    public SavingsAccount(int id, double currentBalance, LocalDate dateOpened) {
        super(id);
        this.DATE_OPENED = dateOpened;
        this.balance = currentBalance;
    }
    
    @Override
    public boolean withdrawal(double money) {
        double penalty = 0.001; //0.1% of withdrawal
        if(money+(money*penalty) > this.balance)
            return false;
        else {
            this.balance -= (money + money*penalty);
            return true;
        }
    }
    
    // when accounts are loaded, any interest is applied
    // not supported yet
    void applyInterest(int years) {
        double newBalance = this.balance;
        // compounded quarterly
        for(int i = 0; i < years*4; i++) { 
            newBalance += newBalance*(getYearlyInterestRate()/4);
        }
        this.balance = newBalance;
    }

    public LocalDate getDateOpened() {
        return this.DATE_OPENED;
    }

    public double getYearlyInterestRate() {
        return yearlyInterestRate;
    }

    public void setYearlyInterestRate(double yearlyInterestRate) {
        this.yearlyInterestRate = yearlyInterestRate;
    }
    
}
