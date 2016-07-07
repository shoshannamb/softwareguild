/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringDAO;
import com.sg.flooringmastery.dao.FlooringDemoDAO;
import com.sg.flooringmastery.dao.FlooringProdDAO;
import com.sg.flooringmastery.dao.FlooringProdDAO;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.TaxRates;
import com.sg.flooringmastery.ui.FlooringUI;
import java.time.LocalDate;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class FlooringController {

    FlooringDAO dao;
    FlooringUI ui;
    FlooringOps ops;
    String NOT_FOUND_ERROR = "Order not found.";

    public FlooringController(FlooringDAO dao, FlooringUI ui) {
        this.dao = dao;
        this.ui = ui;
        ops = new FlooringOps(dao.loadInventory(), dao.loadTaxRates(new TaxRates()));
        ui.setSupportedStates(dao.loadStateAbbreviations());
    }

    public void run() {
        int userSelection;
        while (true) {
            userSelection = ui.displayMenu();
            switch (userSelection) {
                case 1:
                    placeOrder();
                    break;
                case 2:
                    showOrdersForDate();
                    break;
                case 3:
                    showSingleOrder();
                    break;
                case 4:
                    editOrder();
                    break;
                case 5:
                    removeOrder();
                    break;
                case 6:
                    saveProgress();
                    break;
                case 7: // quit
                    saveProgress();
                    return;
            }
        }
    }

    public void placeOrder() {
        Order newOrder = new Order();
        newOrder = ui.getNewOrder(newOrder, ops.getInventory());
        newOrder = ops.calculateOrderCosts(newOrder);
        boolean placeOrder = ui.verifyAction("Would you like to place this order (Y/N)? ", newOrder);
        if (placeOrder) {
            newOrder = ops.addCurrentDateToOrder(newOrder);
            ops.removeMaterialsFromInventory(newOrder);
            int orderNumber = dao.addOrder(newOrder);
            ui.displayOrderNumber(orderNumber);
        }
    }

    public void showOrdersForDate() {
        String date = ui.getDate();
        List<Order> orders = dao.getOrdersFromDay(date);
        if(!orders.isEmpty())
            ui.displayOrders(orders);
        else
            ui.displayError("There is no order record for that date.");
    }

    public void showSingleOrder() {
        String[] orderInfo = ui.getOrderAccessInfo();
        Order o = dao.getSingleOrder(orderInfo);
        if (o.getOrderNum() > 0) {
            ui.displaySingleOrder(o);
        } else {
            ui.displayError(NOT_FOUND_ERROR);
        }
    }

    public void editOrder() {
        String[] orderInfo = ui.getOrderAccessInfo();
        Order toEdit = dao.getSingleOrder(orderInfo);
        if(toEdit.getOrderNum() < 1) {
            ui.displayError(NOT_FOUND_ERROR);
            return;
        }
        boolean willEdit = ui.verifyAction("Is this the order you want to edit (Y/N)? ", toEdit);
        if (willEdit) {
            ops.returnMaterialsToInventory(toEdit);
            toEdit = ui.editOrder(toEdit, ops.getInventory());
            ops.removeMaterialsFromInventory(toEdit);
            toEdit = ops.calculateOrderCosts(toEdit);
            boolean confirmEdit = ui.verifyAction("Place the changed order? ", toEdit);
            if (confirmEdit) {
                dao.editOrder(toEdit);
                ui.displayActionSuccessful("Your order has been edited.");
            }
        }
    }

    public void removeOrder() {
        String[] orderInfo = ui.getOrderAccessInfo();
        Order toRemove = dao.getSingleOrder(orderInfo);
        if(toRemove.getOrderNum() < 1) {
            ui.displayError(NOT_FOUND_ERROR);
            return;
        }
        boolean remove = ui.verifyAction("Would you like to remove this order (Y/N)? ", toRemove);
        if (remove) {
            ops.returnMaterialsToInventory(toRemove);
            dao.removeOrder(toRemove);
            ui.displayActionSuccessful("Your order has been removed.");
        }
    }

    private void saveProgress() {
        if (dao.getCanSave()) {
            boolean allSaved = dao.saveOrderChanges();
            if(!allSaved)
                ui.displayError("There was a problem saving. Some of your data might not be backed up.");
            else
                ui.displayActionSuccessful("Your progress has been saved.");
        } else {
            ui.displayError("Cannot save changes in demo mode.");
        }
    }

}
