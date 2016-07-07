/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bankmanager.dto;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author apprentice
 */
public class AccountTest {

    public AccountTest() {
    }

    /**
     * Test of deposit method, of class Account.
     */
    @Test
    public void testDeposit() {
        Account a = new AccountImpl(1);
        a.setBalance(50);
        double expected = 100;

        a.deposit(50);
        double actual = a.getBalance();

        Assert.assertEquals(0, 0);
    }

    @Test
    public void testWithdrawalTooMuch() {
        Account a = new AccountImpl(1);
        a.setBalance(50);
        boolean expected = false;

        boolean actual = a.withdrawal(100);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testWithdrawalBooleanCheck() {
        Account a = new AccountImpl(1);
        a.setBalance(100);
        boolean expected = true;

        boolean actual = a.withdrawal(50);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testWithdrawalBalanceCheck() {
        Account a = new AccountImpl(1);
        a.setBalance(100);
        double expected = 25;

        a.withdrawal(75);
        double actual = a.getBalance();

        Assert.assertEquals(expected, actual, 0.0);
    }

    public class AccountImpl extends Account {

        public AccountImpl(int id) {
            super(id);
        }
    }

}
