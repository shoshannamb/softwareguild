/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dto.FlooringProduct;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.TaxRates;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author apprentice
 */
public class FlooringOpsTest {

    public FlooringOpsTest() {
    }

    private Order testOrder;
    private FlooringProduct carpet;
    private Map<String, FlooringProduct> inventory = new HashMap<>();
    private TaxRates taxRates = new TaxRates();

    @Before
    public void setUp() {
        carpet = new FlooringProduct();
        carpet.setMaterialCost(2.25);
        carpet.setLaborCost(2.10);
        carpet.setMaterialName("Carpet");

        inventory.put("Carpet", carpet);

        testOrder = new Order();
        testOrder.setCustomerName("Shoshanna Barnett");
        testOrder.setArea(12.2);
        testOrder.setProductType("Carpet");
        testOrder.setState("AL");

        Map<String, Double> taxMap = new HashMap<>();
        taxMap.put("AL", 8.97);
        taxRates.setTaxRates(taxMap);
    }

    /**
     * Test of calculateOrderCosts method, of class FlooringOps.
     */
    @Test
    public void testCalculateOrderCosts() {
        // Arrange
        FlooringOps fo = new FlooringOps(inventory, taxRates);
        TaxRates tr = new TaxRates();
        Map<String, Double> taxRates = new HashMap<>();
        taxRates.put("AL", 8.97);
        taxRates.put("AK", 1.78);
        tr.setTaxRates(taxRates);
        fo.setInventory(new HashMap<>());
        fo.getInventory().put("Carpet", carpet);
        double[] expected = {4.76, 25.62, 27.45, 57.83};
        // Act
        testOrder = fo.calculateOrderCosts(testOrder);
        double[] actual = {testOrder.getTax(),
            testOrder.getLaborCost(),
            testOrder.getMaterialCost(),
            testOrder.getTotal()};

        Assert.assertArrayEquals(expected, actual, 0.01);
    }

}
