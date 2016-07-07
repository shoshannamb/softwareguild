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
import java.time.LocalDate;
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
public class FlooringArrayListDemoDAO implements FlooringDAO {

    private boolean canSave = false;
    private List<Order> workingOrders = new ArrayList<>();

    @Override
    public int addOrder(Order newOrder) {
        Order orderToAdd = addCurrentDateToOrder(newOrder);
        if (!workingOrders.stream().anyMatch(o -> o.getDate().equals(orderToAdd.getDate()))) {
            loadOrderSet(orderToAdd.getDate());
        }
        int orderNumber = (int) workingOrders.stream().filter(o -> o.getDate().equals(orderToAdd.getDate())).count() + 1;
        orderToAdd.setOrderNum(orderNumber);
        workingOrders.add(orderToAdd);
        return orderNumber;
    }

    private Order addCurrentDateToOrder(Order order) {
        String dateString = LocalDate.now().toString(); //YYYY-MM-DD
        order.setDate(dateString.substring(5, 7) + dateString.substring(8) + dateString.substring(0, 4));
        return order;
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
            Order empty = new Order();
            empty.setProductType("");
            return empty;
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
        return false;
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
                orderInfo[0] = orderInfo[0].substring(1);
            }
            if (orderInfo[0].charAt(0) == '%') {
                o.setFulfilled(true);
                orderInfo[0] = orderInfo[0].substring(1);
            }
            
            o.setOrderNum(Integer.parseInt(orderInfo[0]));
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
    public boolean getCanSave() {
        return this.canSave;
    }

    public void setCanSave(boolean canSave) {
        this.canSave = canSave;
    }
}
