/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bankmanager.dto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author apprentice
 */
public class CDAccount extends SavingsAccount {

    LocalDate maturity;

    public CDAccount(int id, double currentBalance, LocalDate dateOpened, LocalDate maturity) {
        super(id, currentBalance, dateOpened);
        this.maturity = maturity;
        this.yearlyInterestRate = 0.02;
    }

    @Override
    // can't withdraw money until mature
    public boolean withdrawal(double money) {
        if (ChronoUnit.DAYS.between(DATE_OPENED, LocalDate.now()) < ChronoUnit.DAYS.between(DATE_OPENED, maturity)) {
            return false;
        } else { // typical savings account withdrawal penalty
            double penalty = 0.001; //0.1% of withdrawal
            if (money + (money * penalty) > this.balance) {
                return false;
            } else {
                this.balance -= (money + money * penalty);
                return true;
            }
        }
    }
    
    public LocalDate getMaturityDate() {
        return this.maturity;
    }

}
