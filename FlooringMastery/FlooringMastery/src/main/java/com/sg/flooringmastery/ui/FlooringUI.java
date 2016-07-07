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
public class FlooringUI {

    ConsoleIO con = new ConsoleIO();
    InputFormatter formatter = new InputFormatter();

    public void setSupportedStates(Map<String, String> abbreviations) {
        formatter.setStateAbbreviations(abbreviations);
    }

    public int displayMenu() {
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        con.display("                          MAIN MENU                          ");
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        con.display("1. Place an order");
        con.display("2. View all orders from a date");
        con.display("3. View a single order");
        con.display("4. Edit an order");
        con.display("5. Remove an order");
        con.display("6. Save your progress");
        con.display("7. Save and quit");
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        int selection = con.promptIntInRange("> ", 1, 7);
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        return selection;
    }

    public Order getNewOrder(Order blankOrder, Map<String, FlooringProduct> inventory) {
        String material = getProductSelection(inventory);
        // !+! is part of encoding and not allowed.
        String name = con.promptStringWithoutRegex("Enter your name: ", "!+!");
        double area = con.promptDoubleInRange("Enter the flooring area in Sq. Ft.: ", 0, inventory.get(material).getAmountInStock());
        String state = validateState("What state are you in? ");
        blankOrder.setCustomerName(name);
        blankOrder.setArea(area);
        blankOrder.setProductType(material);
        blankOrder.setState(state);
        return blankOrder; // no longer blank!
    }

    public String getProductSelection(Map<String, FlooringProduct> inventory) {
        con.display("                     Available Products                     ");
        con.display("Material / Material Cost Per SqFt / Labor Cost Per SqFt / Amount in stock (SqFt)");
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        List<FlooringProduct> inventoryList = inventory.values().stream().collect(Collectors.toList());
        for (int i = 0; i < inventoryList.size(); i++) {
            con.display(i + 1 + ". " + inventoryList.get(i).getMaterialName()
                    + " / $" + inventoryList.get(i).getMaterialCost()
                    + " / $" + inventoryList.get(i).getLaborCost()
                    + " / " + inventoryList.get(i).getAmountInStock());
        }
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        int materialSelection = con.promptIntInRange("Which material would you like? ", 1, inventory.size());
        return inventoryList.get(materialSelection - 1).getMaterialName();
    }

    public Order editOrder(Order toEdit, Map<String, FlooringProduct> inventory) {
        con.display("Current Name: " + toEdit.getCustomerName());
        boolean editField = con.promptYesOrNo("Edit name (Y/N)? ");
        if (editField) {
            toEdit.setCustomerName(con.promptStringWithoutRegex("New Name: ", "!+!"));
        }
        con.display("Current Flooring Material: " + toEdit.getProductType());
        editField = con.promptYesOrNo("Edit Flooring Material (Y/N)? ");
        if (editField) {
            toEdit.setProductType(getProductSelection(inventory));
        }
        con.display("Current Flooring Area: " + toEdit.getArea());
        editField = con.promptYesOrNo("Edit Area (Y/N)? ");
        if (editField) {
            toEdit.setArea(con.promptDoubleInRange("New Area (Sq. Ft.): ", 0, inventory.get(toEdit.getProductType()).getAmountInStock()));
        }
        con.display("Current State: " + toEdit.getState());
        editField = con.promptYesOrNo("Edit State (Y/N)? ");
        if (editField) {
            toEdit.setState(validateState("New State: "));
        }
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        return toEdit;
    }

    public String[] getOrderAccessInfo() {
        String date = getDate();
        String orderNum = Integer.toString(con.promptInt("What is the order number? "));
        con.nl();
        String[] orderInfo = {date, orderNum};
        return orderInfo;
    }

    public void displayOrders(List<Order> orderCollection) {
        orderCollection.stream().forEach(o -> con.display("Order Number: " + o.getOrderNum() + "\n" + o.toString() + "\n"));
    }

    public void displaySingleOrder(Order toShow) {
        con.display(toShow);
        con.nl();
    }

    public void displayError(String errorMessage) {
        con.display(errorMessage);
    }

    public boolean verifyAction(String prompt, Order toVerify) {
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        displaySingleOrder(toVerify);
        return con.promptYesOrNo(prompt);
    }

    public String getDate() {
        LocalDate date = con.promptDate("What date was your order placed? ");
        String dateString = date.toString(); //YYYY-MM-DD
        return dateString.substring(5, 7) + dateString.substring(8) + dateString.substring(0, 4);
    }

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

    public void displayActionSuccessful(String message) {
        con.display(message);
    }

}
