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
public class FlooringCustomerUI implements FlooringUI {

    ConsoleIO con = new ConsoleIO();
    InputFormatter formatter = new InputFormatter();

    @Override
    public void setSupportedStates(Map<String, String> abbreviations) {
        formatter.setStateAbbreviations(abbreviations);
    }

    @Override
    public int displayMenu() {
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        con.display("                          MAIN MENU                          ");
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        con.display("1. Place an order");
        con.display("2. Remove an order");
        con.display("3. View an order");
        con.display("4. Edit an order");
        con.display("5. Save your progress");
        con.nl();
        con.display("Press 0 to save and quit");
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        int selection = con.promptIntInRange("> ", 0, 5);
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        return selection;
    }

    @Override
    public Order getNewOrder(Order blankOrder, List<FlooringProduct> inventory) {
        int productIndex = getProductSelection(inventory);
        // !+! is part of encoding and not allowed.
        double area = con.promptDoubleInRange("Enter the flooring area in Sq. Ft.: ", 0, inventory.get(productIndex).getAmountInStock());
        String name = con.promptStringWithoutRegex("Enter your name: ", "!+!");
        String state = validateState("What state are you in? ");
        blankOrder.setCustomerName(name);
        blankOrder.setArea(area);
        blankOrder.setProductType(inventory.get(productIndex).getMaterialName());
        blankOrder.setMaterialCostPerSquareFoot(inventory.get(productIndex).getMaterialCost());
        blankOrder.setLaborCostPerSqFt(inventory.get(productIndex).getLaborCost());
        blankOrder.setState(state);
        return blankOrder; // no longer blank!
    }

    @Override
    public int getProductSelection(List<FlooringProduct> inventory) {
        con.display("                     Available Products                     ");
        con.display("Material / Material Cost Per SqFt / Labor Cost Per SqFt / Amount in stock (SqFt)");
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        for (int i = 0; i < inventory.size(); i++) {
            con.display(i + 1 + ". " + inventory.get(i).getMaterialName()
                    + " / $" + inventory.get(i).getMaterialCost()
                    + " / $" + inventory.get(i).getLaborCost()
                    + " / " + inventory.get(i).getAmountInStock());
        }
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        return con.promptIntInRange("Which material would you like? ", 1, inventory.size()) - 1; // return index of selection
    }

    @Override
    public Order editOrder(Order toEdit, List<FlooringProduct> inventory) {
        con.display("Current Name: " + toEdit.getCustomerName());
        boolean editField = con.promptYesOrNo("Edit name (Y/N)? ");
        if (editField) {
            toEdit.setCustomerName(con.promptStringWithoutRegex("New Name: ", "!+!"));
        }
        con.display("Current Flooring Material: " + toEdit.getProductType());
        editField = con.promptYesOrNo("Edit Flooring Material (Y/N)? ");
        if (editField) {
            int productIndex = getProductSelection(inventory);
            toEdit.setProductType(inventory.get(productIndex).getMaterialName());
            toEdit.setMaterialCostPerSquareFoot(inventory.get(productIndex).getMaterialCost());
            toEdit.setLaborCostPerSqFt(inventory.get(productIndex).getLaborCost());
        }
        con.display("Current Flooring Area: " + toEdit.getArea());
        editField = con.promptYesOrNo("Edit Area (Y/N)? ");
        if (editField) {
            double amountAvailable = inventory.stream().filter(p -> p.getMaterialName().equals(toEdit.getProductType())).collect(Collectors.toList()).get(0).getAmountInStock();
            toEdit.setArea(con.promptDoubleInRange("New Area (Sq. Ft.): ", 0, amountAvailable));
        }
        con.display("Current State: " + toEdit.getState());
        editField = con.promptYesOrNo("Edit State (Y/N)? ");
        if (editField) {
            toEdit.setState(validateState("New State: "));
        }
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        return toEdit;
    }

    @Override
    public String[] getOrderAccessInfo() {
        String date = getDate();
        String orderNum = Integer.toString(con.promptInt("What is the order number? "));
        con.nl();
        String[] orderInfo = {date, orderNum};
        return orderInfo;
    }

    @Override
    public void displayOrders(List<Order> orderCollection) {
        if (!orderCollection.isEmpty()) {
            orderCollection.stream().forEach(o -> con.display("Order Number: " + o.getOrderNum() + "\n" + o.toString() + "\n"));
        } else {
            con.display("No orders found.");
        }
    }

    @Override
    public boolean displaySingleOrder(Order toShow) {
        if (toShow.getProductType().length() == 0 || toShow.isIsDeleted()) {
            con.display("Order not found.");
            return false;
        } else {
            con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            con.display(toShow);
        }
        con.nl();
        return true;
    }

    @Override
    public void displayError(String errorMessage) {
        con.display(errorMessage);
    }

    @Override
    public boolean verifyAction(String action, Order toVerify) {
        if (displaySingleOrder(toVerify)) {
            con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            String prompt;
            switch (action) {
                case "place":
                    prompt = "Would you like to place this order (Y/N)? ";
                    break;
                case "remove":
                case "edit":
                    prompt = "Is this the order you would like to " + action + " (Y/N)? ";
                    break;
                default:
                    prompt = action + " (Y/N)?";
            }
            return con.promptYesOrNo(prompt);
        } else {
            return false; // return false if no or if order is empty or deleted.
        }
    }

    @Override
    public String getDate() {
        LocalDate date = con.promptDate("What date was your order placed? ");
        String dateString = date.toString(); //YYYY-MM-DD
        return dateString.substring(5, 7) + dateString.substring(8) + dateString.substring(0, 4);
    }

    @Override
    public void displayOrderNumber(int orderNumber) {
        String dateString = LocalDate.now().toString();
        String dateFormatted = dateString.substring(5, 7) + "-" + dateString.substring(8) + "-" + dateString.substring(0, 4);
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        con.display("Your order number is " + orderNumber + " and today's date is " + dateFormatted);
        con.display("Please save this information for your records.");
    }

    private String validateState(String prompt) {
        boolean validState;
        String stateInput;
        do {
            stateInput = con.promptString(prompt);
            validState = formatter.stateExists(stateInput);
            if (!validState) {
                con.display("Please enter a valid state name or abbreviation.");
            }
        } while (!validState);
        if (stateInput.length() != 2) {
            return formatter.convertToStateAbbreviation(stateInput);
        } else {
            return stateInput.toUpperCase();
        }
    }

    @Override
    public void displayActionSuccessful(boolean wasSuccessful, String action) {
        if (wasSuccessful) {
            con.display(action.toUpperCase() + " was successful.");
        } else {
            con.display(action.toUpperCase() + " was not successful. Please try again or contact technical support.");
        }
    }

    @Override
    public void displayProducts(List<FlooringProduct> products) {
        throw new UnsupportedOperationException("Not supported in customer mode");
    }

    @Override
    public double getUpperBound() {
        throw new UnsupportedOperationException("Not supported in customer mode");
    }

    @Override
    public List<FlooringProduct> changeStock(List<FlooringProduct> currentProducts) {
        throw new UnsupportedOperationException("Not supported in customer mode");
    }

    @Override
    public FlooringProduct addFlooringProduct(FlooringProduct flooringProduct, List<FlooringProduct> completeInventory) {
        throw new UnsupportedOperationException("Not supported in customer mode");
    }

    @Override
    public List<FlooringProduct> editProducts(List<FlooringProduct> completeInventory) {
        throw new UnsupportedOperationException("Not supported in customer mode");
    }

    @Override
    public String removeProduct(List<FlooringProduct> currentProducts) {
        throw new UnsupportedOperationException("Not supported in customer mode");
    }
}
