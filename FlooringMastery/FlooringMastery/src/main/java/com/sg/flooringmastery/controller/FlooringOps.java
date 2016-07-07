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

    private TaxRates taxRates;
    private Map<String, FlooringProduct> inventory;

    public FlooringOps(Map<String, FlooringProduct> inventory, TaxRates taxRates) {
        this.inventory = inventory;
        this.taxRates = taxRates;
    }

    public Order addCurrentDateToOrder(Order order) {
        String dateString = LocalDate.now().toString(); //YYYY-MM-DD
        order.setDate(dateString.substring(5, 7) + dateString.substring(8) + dateString.substring(0, 4));
        return order;
    }

    public void returnMaterialsToInventory(Order order) {
        double currentStock = inventory.get(order.getProductType()).getAmountInStock();
        inventory.get(order.getProductType()).setAmountInStock(currentStock + order.getArea());
    }

    public void removeMaterialsFromInventory(Order order) {
        double currentStock = inventory.get(order.getProductType()).getAmountInStock();
        inventory.get(order.getProductType()).setAmountInStock(currentStock - order.getArea());
    }

    public Order calculateOrderCosts(Order order) {
        FlooringProduct product = inventory.get(order.getProductType());
        order.setMaterialCostPerSquareFoot(product.getMaterialCost());
        order.setLaborCostPerSqFt(product.getLaborCost());
        order.setTaxRate(taxRates.getTaxRates().get(order.getState()));

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

    public TaxRates getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(TaxRates taxRates) {
        this.taxRates = taxRates;
    }

    public Map<String, FlooringProduct> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, FlooringProduct> inventory) {
        this.inventory = inventory;
    }
}
