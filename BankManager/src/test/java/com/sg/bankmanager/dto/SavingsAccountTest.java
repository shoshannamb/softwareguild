/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bankmanager.dto;

import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Assert;

/**
 *
 * @author apprentice
 */
public class SavingsAccountTest {

    public SavingsAccountTest() {
    }

    /**
     * Test of withdrawal method, of class SavingsAccount.
     */
    @Test
    public void testWithdrawalOverdraw() {
        SavingsAccount sa = new SavingsAccount(1, 100, LocalDate.now());
        boolean expected = false;
        boolean actual = sa.withdrawal(200);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testWithdrawalNoOverdraw() {
        SavingsAccount sa = new SavingsAccount(1, 200, LocalDate.now());
        double expected = 200 - 100.10;
        sa.withdrawal(100);
        double actual = sa.getBalance();
        
        Assert.assertEquals(expected, actual, 0.001);
    }

}
