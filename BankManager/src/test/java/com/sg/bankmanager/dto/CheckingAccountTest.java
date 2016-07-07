/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bankmanager.dto;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Assert;

/**
 *
 * @author apprentice
 */
public class CheckingAccountTest {
    
    public CheckingAccountTest() {
    }

    /**
     * Test of withdrawal method, of class CheckingAccount.
     */
    @Test
    public void testWithdrawalAlreadyOverdrawn() {
        CheckingAccount ca = new CheckingAccount(1, -100);
        boolean expected = false;
        
        boolean actual = ca.withdrawal(200);
        
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void testWithdrawalOverdraw() {
        CheckingAccount ca = new CheckingAccount(1, 50);
        double expected = -70;
        
        ca.withdrawal(85);
        double actual = ca.getBalance();
        
        Assert.assertEquals(expected, actual, 0.0);
    }
    
    @Test
    public void testWithdrawalNoOverdraw() {
        CheckingAccount ca = new CheckingAccount(1, 100);
        double expected = 75;
        
        ca.withdrawal(25);
        double actual = ca.getBalance();
        
        Assert.assertEquals(expected, actual, 0.0);
    }
    
}
