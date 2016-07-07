/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.FlooringProduct;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.TaxRates;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author apprentice
 */
public class FlooringDemoDAOTest {

    public FlooringDemoDAOTest() {
    }

    @Test
    public void testLoadOrderSet() {
        FlooringDemoDAO dao = new FlooringDemoDAO();
        int expected = 94;
        dao.loadOrderSet("01142014");
        int actual = dao.workingOrders.get("01142014").size();
        Assert.assertEquals(expected, actual);
    }

}
