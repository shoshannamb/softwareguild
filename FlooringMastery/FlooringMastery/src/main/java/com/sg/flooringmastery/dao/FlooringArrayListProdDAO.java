/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.FlooringProduct;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.TaxRates;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
public class FlooringArrayListProdDAO implements FlooringDAO {

    private boolean canSave = true;
    private List<Order> workingOrders = new ArrayList<>();

    @Override
    public int addOrder(Order newOrder) {
        if (!workingOrders.stream().anyMatch(o -> o.getDate().equals(newOrder.getDate()))) {
            loadOrderSet(newOrder.getDate());
        }
        int orderNumber = (int) workingOrders.stream().filter(o -> o.getDate().equals(newOrder.getDate())).count() + 1;
        newOrder.setOrderNum(orderNumber);
        workingOrders.add(newOrder);
        return orderNumber;
    }

    @Override
    public void removeOrder(Order toRemove) {
        workingOrders.stream().filter(o -> o.getDate().equals(toRemove.getDate()))
                .filter(o -> o.getOrderNum() == toRemove.getOrderNum())
                .forEach(o -> o.setIsDeleted(true));
    }

    @Override
    // returns an empty list if date not found.
    public List<Order> getOrdersFromDay(String date) {
        if (workingOrders.stream().filter(o -> o.getDate().equals(date)).count() == 0) {
            loadOrderSet(date);
        }
        return workingOrders.stream().filter(o -> o.getDate().equals(date)).filter(o -> !o.isIsDeleted()).collect(Collectors.toList());
    }

    @Override
    public Order getSingleOrder(String[] dateAndOrderNumber) {
        String date = dateAndOrderNumber[0];
        int orderNumber = Integer.parseInt(dateAndOrderNumber[1]);
        // look for order date in workingOrders
        // if not there, try to load.
        if (workingOrders.stream().filter(o -> o.getDate().equals(date)).count() == 0) {
            loadOrderSet(date);
        }
        List<Order> matchingOrders = workingOrders.stream().
                filter(o -> o.getDate().equals(date)).
                filter(o -> o.getOrderNum() == orderNumber).collect(Collectors.toList());

        if (matchingOrders.isEmpty()) {
            return new Order();
        } else {
            return matchingOrders.get(0);
        }
    }

    @Override
    public void editOrder(Order editedOrder) {
        workingOrders.stream().filter(o -> o.getDate().equals(editedOrder.getDate()))
                .filter(o -> o.getOrderNum() == editedOrder.getOrderNum())
                .forEach(o -> workingOrders.set(workingOrders.indexOf(o), editedOrder));
    }

    @Override
    public boolean saveOrderChanges() {
        boolean allSaved = true;
        List<String> workingDates = workingOrders.stream().map(Order::getDate).distinct().collect(Collectors.toList());
        for (String date : workingDates) {
            String fileName = "textFiles/demoData/Orders_" + date + ".txt";
            try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
                // add header
                out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
                workingOrders.stream().filter(o -> o.getDate().equals(date)).forEach(o -> out.println(encodeOrder(o)));
            } catch (IOException ex) {
                allSaved = false;
            }
        }
        return allSaved;
    }

    private String encodeOrder(Order order) {
        String encoded = "";
        // add deleted flag
        if (order.isIsDeleted()) {
            encoded += "*";
        }
        encoded += order.getOrderNum() + ","
                // replace any commas in names with "!+!" to maintain delimiting
                + order.getCustomerName().replace(",", "!+!") + ","
                + order.getState() + ","
                + order.getTaxRate() + ","
                + order.getProductType() + ","
                + order.getArea() + ","
                + order.getMaterialCostPerSquareFoot() + ","
                + order.getLaborCostPerSqFt() + ","
                + order.getMaterialCost() + ","
                + order.getLaborCost() + ","
                + order.getTax() + ","
                + order.getTotal();
        return encoded;
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
            product.setAmountInStock(Double.parseDouble(productInfo[3]));
            inventory.put(product.getMaterialName(), product);
        }
        return inventory;
    }

    public boolean loadOrderSet(String date) {
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
            workingOrders.add(o);
        }
        return true;
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
        Map<String, String> loadedStateNames = new HashMap<>();
        Scanner stateReader;

        try {
            stateReader = new Scanner(new BufferedReader(new FileReader("textFiles/taxRates.txt")));
            stateReader.nextLine();
        } catch (Exception ex) {
            return loadedStateNames; // return empty object
        }

        while (stateReader.hasNextLine()) {
            String[] stateInfo = stateReader.nextLine().split(",");
            loadedStateNames.put(stateInfo[2], stateInfo[0]);
        }
        return loadedStateNames;
    }

}
