/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.FlooringProduct;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.TaxRates;
import com.sg.flooringmastery.ui.InputFormatter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author apprentice
 */
public class FlooringDemoDAO implements FlooringDAO {

    boolean canSave = false;
    Map<String, Map<Integer, Order>> workingOrders = new HashMap<>();

    @Override
    public int addOrder(Order newOrder) {
        int orderNumber = 0;
        if (!workingOrders.containsKey(newOrder.getDate())) {
            boolean fileFound = loadOrderSet(newOrder.getDate());
            if (!fileFound) {
                workingOrders.put(newOrder.getDate(), new HashMap<>());
            }
        }
        orderNumber = workingOrders.get(newOrder.getDate()).size() + 1;
        newOrder.setOrderNum(orderNumber);
        workingOrders.get(newOrder.getDate()).put(orderNumber, newOrder);
        return orderNumber;
    }

    @Override
    public void removeOrder(Order toRemove) {
        workingOrders.get(toRemove.getDate()).get(toRemove.getOrderNum()).setIsDeleted(true);
    }

    @Override
    public List<Order> getOrdersFromDay(String date) {
        if (!workingOrders.containsKey(date)) {
            boolean dateFileExists = loadOrderSet(date);
            if (!dateFileExists) {
                return new ArrayList<>();
            }
        }
        return workingOrders.get(date).values().stream().filter(o -> !o.isIsDeleted()).collect(Collectors.toList());
    }

    @Override
    // returns an empty order if it doesn't exist
    public Order getSingleOrder(String[] dateAndOrderNumber) {
        String date = dateAndOrderNumber[0];
        int orderNumber = Integer.parseInt(dateAndOrderNumber[1]);
        if (!workingOrders.containsKey(date)) {
            loadOrderSet(date);
        }
        if (workingOrders.containsKey(date) && workingOrders.get(date).containsKey(orderNumber) && !workingOrders.get(date).get(orderNumber).isIsDeleted()) {
            return workingOrders.get(date).get(orderNumber);
        } else {
            return new Order();
        }
    }

    @Override
    public void editOrder(Order editedOrder) {
        workingOrders.get(editedOrder.getDate()).put(editedOrder.getOrderNum(), editedOrder);
    }

    @Override
    public boolean saveOrderChanges() {
        return false;
    }
    // returns false if can't access the file

    public boolean loadOrderSet(String date) {
        Map<Integer, Order> dayOrders = new HashMap<>();
        String fileName = "textFiles/demoData/Orders_" + date + ".txt";
        Scanner orderReader;
        try {
            orderReader = new Scanner(new BufferedReader(new FileReader(fileName)));
            orderReader.nextLine();
        } catch (FileNotFoundException ex) {
            return false;
        }
        while (orderReader.hasNextLine()) {
            String[] orderInfo = orderReader.nextLine().split(",");
            Order o = new Order();
            o.setDate(date);
            // Star is the flag for a deleted order.
            // If star is present, set isDeleted to true and digest the star
            // before setting orderNumber.
            if (orderInfo[0].charAt(0) == '*') {
                o.setIsDeleted(true);
                o.setOrderNum(Integer.parseInt(orderInfo[0].substring(1)));
            } else {
                o.setOrderNum(Integer.parseInt(orderInfo[0]));
            }
            o.setCustomerName(orderInfo[1].replace("!+!", ","));
            o.setState(orderInfo[2]);
            o.setTaxRate(Double.parseDouble(orderInfo[3]));
            o.setProductType(orderInfo[4]);
            o.setArea(Double.parseDouble(orderInfo[5]));
            o.setMaterialCostPerSquareFoot(Double.parseDouble(orderInfo[6]));
            o.setLaborCostPerSqFt(Double.parseDouble(orderInfo[7]));
            o.setMaterialCost(Double.parseDouble(orderInfo[8]));
            o.setLaborCost(Double.parseDouble(orderInfo[9]));
            o.setTax(Double.parseDouble(orderInfo[10]));
            o.setTotal(Double.parseDouble(orderInfo[11]));
            dayOrders.put(o.getOrderNum(), o);
        }
        workingOrders.put(date, dayOrders);
        return true;
    }

    @Override
    public Map<String, FlooringProduct> loadInventory() {
        Map<String, FlooringProduct> inventory = new HashMap<>();
        Scanner productReader;
        try {
            productReader = new Scanner(new BufferedReader(new FileReader("textFiles/productInventory.txt")));
            productReader.nextLine();
        } catch (FileNotFoundException ex) {
            return inventory;
        }
        while (productReader.hasNextLine()) {
            String[] productInfo = productReader.nextLine().split(",");
            FlooringProduct product = new FlooringProduct();
            product.setMaterialName(productInfo[0]);
            product.setMaterialCost(Double.parseDouble(productInfo[1]));
            product.setLaborCost(Double.parseDouble(productInfo[2]));
            inventory.put(product.getMaterialName(), product);
        }
        return inventory;
    }

    @Override
    public TaxRates loadTaxRates(TaxRates emptyTRObject) {
        Map<String, Double> loadedTaxRates = new HashMap<>();
        Scanner taxReader;
        try {
            taxReader = new Scanner(new BufferedReader(new FileReader("textFiles/taxRates.txt")));
            taxReader.nextLine();         // Skip the header
        } catch (FileNotFoundException ex) {
            return emptyTRObject;
        }
        while (taxReader.hasNextLine()) {
            String[] taxInfo = taxReader.nextLine().split(",");
            loadedTaxRates.put(taxInfo[0], Double.parseDouble(taxInfo[1]));
        }
        emptyTRObject.setTaxRates(loadedTaxRates);
        return emptyTRObject;
    }

    @Override
    public boolean getCanSave() {
        return this.canSave;
    }

    public void setCanSave(boolean canSave) {
        this.canSave = canSave;
    }

    @Override
    public Map<String, String> loadStateAbbreviations() {
        Map <String, String> loadedStateNames = new HashMap<>();
        Scanner stateReader;
        try {
            stateReader = new Scanner(new BufferedReader(new FileReader("textFiles/taxRates.txt")));
            stateReader.nextLine();
        } catch (Exception ex) {
            return loadedStateNames; // return empty object
        }
        while(stateReader.hasNextLine()) {
            String[] stateInfo = stateReader.nextLine().split(",");
            loadedStateNames.put(stateInfo[2], stateInfo[0]);
        }
        return loadedStateNames;
    }

}
