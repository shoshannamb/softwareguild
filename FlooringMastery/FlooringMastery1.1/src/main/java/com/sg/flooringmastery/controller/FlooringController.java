/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringDAO;
import com.sg.flooringmastery.dao.FlooringInventory;
import com.sg.flooringmastery.dao.FlooringStateTaxes;
import com.sg.flooringmastery.dto.FlooringProduct;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.TaxRates;
import com.sg.flooringmastery.ui.FlooringUI;
import java.util.List;

/**
 *
 * @author apprentice
 */
public class FlooringController {

    FlooringDAO orderDAO;
    FlooringInventory inventory;
    FlooringStateTaxes taxes;
    FlooringUI ui;
    FlooringOps ops;
    String NOT_FOUND_ERROR = "No order found.";
    String DEMO = "Cannot save in demo mode";

    public FlooringController(FlooringDAO orderDAO, FlooringUI ui, FlooringInventory inventory, FlooringStateTaxes taxes) {
        this.orderDAO = orderDAO;
        this.ui = ui;
        this.inventory = inventory;
        this.taxes = taxes;
        ops = new FlooringOps();
        inventory.loadInventory();
        taxes.loadTaxRates();
        ui.setSupportedStates(taxes.getStateNames());
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
                    removeOrder();
                    break;
                case 3:
                    showSingleOrder();
                    break;
                case 4:
                    editOrder();
                    break;
                case 5:
                    saveProgress();
                    break;
                case 6:
                    showOrdersForDate();
                    break;
                case 7:
                    showOutOfStockProducts();
                    break;
                case 8:
                    showLowStockProducts();
                    break;
                case 9:
                    changeProductStock();
                    break;
                case 10:
                    addFlooringProductToInventory();
                    break;
                case 11:
                    editFlooringProduct();
                    break;
                case 12:
                    discontinueFlooringProduct();
                    break;
                default: // quit
                    saveProgress();
                    return;
            }
        }
    }

    public void placeOrder() {
        Order newOrder = ops.calculateOrderCosts(taxes.setOrderTaxRate(ui.getNewOrder(new Order(), inventory.getInStockProducts())));
        if (ui.verifyAction("place", newOrder)) {
            inventory.removeMaterialsFromInventory(newOrder);
            ui.displayOrderNumber(orderDAO.addOrder(newOrder));
        }
    }

    public void showOrdersForDate() {
        ui.displayOrders(orderDAO.getOrdersFromDay(ui.getDate()));
    }

    public void showSingleOrder() {
        ui.displaySingleOrder(orderDAO.getSingleOrder(ui.getOrderAccessInfo()));
    }

    public void editOrder() {
        Order toEdit = orderDAO.getSingleOrder(ui.getOrderAccessInfo());
        if (ui.verifyAction("edit", toEdit)) {
            inventory.returnMaterialsToInventory(toEdit);
            toEdit = ops.calculateOrderCosts(ui.editOrder(toEdit, inventory.getInStockProducts()));
            if (ui.verifyAction("place", toEdit)) {
                inventory.removeMaterialsFromInventory(toEdit);
                orderDAO.editOrder(toEdit);
                ui.displayActionSuccessful(true, "edit");
            }
        }
    }

    public void removeOrder() {
        Order toRemove = orderDAO.getSingleOrder(ui.getOrderAccessInfo());
        if (ui.verifyAction("remove", toRemove)) {
            inventory.returnMaterialsToInventory(toRemove);
            orderDAO.removeOrder(toRemove);
            ui.displayActionSuccessful(true, "remove");
        }
    }

    private void saveProgress() {
        if (orderDAO.getCanSave() && taxes.getCanSave() && inventory.isAbleToSave()) {
            ui.displayActionSuccessful(orderDAO.saveOrderChanges() && taxes.saveTaxRates() && inventory.save(), "save");
        } else {
            ui.displayError(DEMO);
        }
    }

    private void showOutOfStockProducts() {
        ui.displayProducts(inventory.getOutOfStockProducts());
    }

    private void showLowStockProducts() {
        ui.displayProducts(inventory.getLowSupplyProducts(ui.getUpperBound()));
    }

    private void changeProductStock() {
        inventory.editProducts(ui.changeStock(inventory.getCurrentProducts()));
    }

    private void addFlooringProductToInventory() {
        inventory.addNewProduct(ui.addFlooringProduct(new FlooringProduct(), inventory.getCompleteInventory()));
    }

    private void editFlooringProduct() {
        inventory.editProducts(ui.editProducts(inventory.getCompleteInventory()));
    }

    private void discontinueFlooringProduct() {
        inventory.removeProduct(ui.removeProduct(inventory.getCurrentProducts()));
    }

}
