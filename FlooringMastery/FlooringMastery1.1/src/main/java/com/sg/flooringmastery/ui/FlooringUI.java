/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.FlooringProduct;
import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author apprentice
 */
public interface FlooringUI {
    public void setSupportedStates(Map<String, String> abbreviations);

    public int displayMenu();

    public Order getNewOrder(Order blankOrder, List<FlooringProduct> inventory);

    public int getProductSelection(List<FlooringProduct> inventory);

    public Order editOrder(Order toEdit, List<FlooringProduct> inventory);

    public String[] getOrderAccessInfo();

    public void displayOrders(List<Order> orderCollection);

    public boolean displaySingleOrder(Order toShow);

    public void displayError(String errorMessage);

    public boolean verifyAction(String action, Order toVerify);

    public String getDate();

    public void displayOrderNumber(int orderNumber);

    public void displayActionSuccessful(boolean wasSuccessful, String action);

    public void displayProducts(List<FlooringProduct> products);

    public double getUpperBound();

    public List<FlooringProduct> changeStock(List<FlooringProduct> currentProducts);

    public FlooringProduct addFlooringProduct(FlooringProduct flooringProduct, List<FlooringProduct> completeInventory);

    public List<FlooringProduct> editProducts(List<FlooringProduct> completeInventory);

    public String removeProduct(List<FlooringProduct> currentProducts);

}
