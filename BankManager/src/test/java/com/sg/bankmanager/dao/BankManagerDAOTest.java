/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bankmanager.dao;

import com.sg.bankmanager.dto.CDAccount;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Assert;

/**
 *
 * @author apprentice
 */
public class BankManagerDAOTest {

    public BankManagerDAOTest() {
    }

    /**
     * Test of clientExists method, of class BankManagerDAO.
     */
    @Test
    public void testClientExistsTrue() {
        BankManagerDAO dao = new BankManagerDAO();
        boolean expected = true;
        boolean actual = dao.clientExists(1001);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testClientExistsFalse() {
        BankManagerDAO dao = new BankManagerDAO();
        boolean expected = false;
        boolean actual = dao.clientExists(120932);
        Assert.assertEquals(expected, actual);
    }

    /**
     * Test of checkPIN method, of class BankManagerDAO.
     */
    @Test
    public void testCheckPINTrue() {
        BankManagerDAO dao = new BankManagerDAO();
        boolean expected = true;
        boolean actual = dao.checkPIN(1001, 1234);
        Assert.assertEquals(expected, actual);
    }

    /**
     * Test of lockClient method, of class BankManagerDAO.
     */
    @Test
    public void testLockClientFalse() {
        BankManagerDAO dao = new BankManagerDAO();
        boolean expected = false;
        boolean actual = dao.checkPIN(1001, 5667);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void testGetOverdrawn() {
        BankManagerDAO dao = new BankManagerDAO();
        dao.withdraw(1001, 100101, 1000);
        List overdrawn = dao.getOverdrawnAccounts(1001);
        int expected = 1;
        int actual = overdrawn.size();
        assertEquals(expected, actual);
    }
    
    @Test
    public void getTotalInAccounts() {
        BankManagerDAO dao = new BankManagerDAO();
        double expected = 5022;
        double actual = dao.getTotalInAccounts(1002);
        assertEquals(expected, actual, 0.0);
    }
    
    @Test
    public void testGetCDAccountInfo() {
        BankManagerDAO dao = new BankManagerDAO();
        int expected = 1;
        List<CDAccount>[] cds = dao.getCDAccounts(1001);
        int actual = cds[0].size();
        assertEquals(expected, actual);
    }


}
