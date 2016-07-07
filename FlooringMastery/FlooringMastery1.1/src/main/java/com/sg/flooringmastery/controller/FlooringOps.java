/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dto.FlooringProduct;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.TaxRates;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author apprentice
 */
public class FlooringOps {

    public Order calculateOrderCosts(Order order) {
        // convert to BigDecimal for math!
        BigDecimal matCostSqFt = BigDecimal.valueOf(order.getMaterialCostPerSquareFoot());
        BigDecimal laborCostSqFt = BigDecimal.valueOf(order.getLaborCostPerSqFt());
        BigDecimal area = BigDecimal.valueOf(order.getArea());
        BigDecimal taxRate = BigDecimal.valueOf(order.getTaxRate() / 100);
        BigDecimal matCost = matCostSqFt.multiply(area);
        BigDecimal laborCost = laborCostSqFt.multiply(area);
        BigDecimal subtotal = matCost.add(laborCost);
        BigDecimal tax = subtotal.multiply(taxRate);
        BigDecimal total = subtotal.add(tax);

        // perform half-up rounding after calculations
        matCost = matCost.setScale(2, BigDecimal.ROUND_HALF_UP);
        laborCost = laborCost.setScale(2, BigDecimal.ROUND_HALF_UP);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
        total = total.setScale(2, BigDecimal.ROUND_HALF_UP);

        // reset to doubles for placement in Order object
        order.setMaterialCost(matCost.doubleValue());
        order.setLaborCost(laborCost.doubleValue());
        order.setTax(tax.doubleValue());
        order.setTotal(total.doubleValue());
        return order;
    }

}
