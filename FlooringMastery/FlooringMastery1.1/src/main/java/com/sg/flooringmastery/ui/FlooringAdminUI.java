/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.FlooringProduct;
import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author apprentice
 */
public class FlooringAdminUI implements FlooringUI {

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
        con.display("Order options:");
        con.display("1. Place an order");
        con.display("2. Remove an order");
        con.display("3. View an order");
        con.display("4. Edit an order");
        con.display("5. Save your progress");
        con.display("6. Show all orders for a date");
        con.nl();
        con.display("Product options:");
        con.display("7. See out of stock products");
        con.display("8. See low stock products");
        con.display("9. Add or remove stock for a product");
        con.display("10. Add a new product");
        con.display("11. Edit a product");
        con.display("12. Discontinue a product");
        con.nl();
        con.display("Press 0 to save and quit");
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        int selection = con.promptIntInRange("> ", 0, 12);
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        return selection;
    }

    @Override
    public Order getNewOrder(Order blankOrder, List<FlooringProduct> inventory) {
        int productIndex = getProductSelection(inventory);
        // !+! is part of encoding and not allowed.
        double area = con.promptDoubleInRange("Enter the flooring area in Sq. Ft.: ", 0, inventory.get(productIndex).getAmountInStock());
        String name = con.promptStringWithoutRegex("Enter customer name: ", "!+!");
        String state = validateState("Enter customer state: ");
        blankOrder.setCustomerName(name);
        blankOrder.setArea(area);
        blankOrder.setProductType(inventory.get(productIndex).getMaterialName());
        blankOrder.setMaterialCostPerSquareFoot(inventory.get(productIndex).getMaterialCost());
        blankOrder.setLaborCostPerSqFt(inventory.get(productIndex).getLaborCost());
        blankOrder.setState(state);
        
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        return blankOrder; // no longer blank!
    }

    @Override
    public int getProductSelection(List<FlooringProduct> inventory) {
        con.display("                     Available Products                     ");
        displayProducts(inventory);
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        return con.promptIntInRange("Enter the order material: ", 1, inventory.size()) - 1; // return index of selection
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
        LocalDate date = con.promptDate("What is the order date? ");
        String dateString = date.toString(); //YYYY-MM-DD
        return dateString.substring(5, 7) + dateString.substring(8) + dateString.substring(0, 4);
    }

    @Override
    public void displayOrderNumber(int orderNumber) {
        String dateString = LocalDate.now().toString();
        String dateFormatted = dateString.substring(5, 7) + "-" + dateString.substring(8) + "-" + dateString.substring(0, 4);
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        con.display("The order number is " + orderNumber + " and today's date is " + dateFormatted);
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

    public void displayProducts(List<FlooringProduct> products) {
        if (products.isEmpty()) {
            con.display("No products found.");
        } else {
            con.display("Material / Material Cost Per SqFt / Labor Cost Per SqFt / Amount in stock (SqFt)");
            con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            for (int i = 0; i < products.size(); i++) {
                String materialString = i + 1 + ". " + products.get(i).getMaterialName()
                        + " / $" + products.get(i).getMaterialCost()
                        + " / $" + products.get(i).getLaborCost()
                        + " / " + products.get(i).getAmountInStock();
                if (products.get(i).isDiscontinued()) {
                    materialString += " (Discontinued)";
                }
                con.display(materialString);
            }
        }
    }

    @Override
    public double getUpperBound() {
        return con.promptDouble("Find products with stock less than what (SqFt)? ");
    }

    @Override
    public List<FlooringProduct> changeStock(List<FlooringProduct> currentProducts) {
        boolean stillAddingStock = true;
        while (stillAddingStock) {
            con.display("Current stock amounts:");
            for (int i = 0; i < currentProducts.size(); i++) {
                con.display(i + 1 + ". " + currentProducts.get(i).getMaterialName() + ": " + currentProducts.get(i).getAmountInStock() + " SqFt");
            }
            con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            con.display("Which would you like to change?");
            int index = con.promptIntInRange("Or press 0 to stop changing the inventory. ", 0, currentProducts.size()) - 1;
            if (index != -1) {
                currentProducts.get(index).setAmountInStock(con.promptDouble("New amount: "));
                con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            } else {
                stillAddingStock = false;
            }
        }
        return currentProducts;
    }

    @Override
    public FlooringProduct addFlooringProduct(FlooringProduct newProduct, List<FlooringProduct> inventory) {
        boolean repeatMaterialName;
        String newMaterialName = "";

        do {
            String name = con.promptString("New product name: ");
            repeatMaterialName = inventory.stream().anyMatch(p -> p.getMaterialName().equals(name));
            if (repeatMaterialName) {
                con.display("That product is already in the inventory. Please pick a different product name.");
            } else {
                newMaterialName = name;
            }
        } while (repeatMaterialName);
        double materialCost = con.promptPositiveDouble("Material cost per SqFt: ");
        double laborCost = con.promptPositiveDouble("Labor cost per SqFt: ");
        double amount = con.promptPositiveDouble("Amount in stock (SqFt): ");

        newProduct.setMaterialName(newMaterialName);
        newProduct.setMaterialCost(materialCost);
        newProduct.setLaborCost(laborCost);
        newProduct.setAmountInStock(amount);
        return newProduct;
    }

    @Override
    public List<FlooringProduct> editProducts(List<FlooringProduct> completeInventory) {

        boolean stillEditing = true;
        while (stillEditing) {
            con.display("Current stock amounts:");
            displayProducts(completeInventory);
            con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            con.display("Which would you like to edit?");
            int index = con.promptIntInRange("Or press 0 to stop changing the inventory. ", 0, completeInventory.size()) - 1;
            if (index != -1) {
                FlooringProduct edited = editProduct(completeInventory.get(index));
                completeInventory.remove(index);
                completeInventory.add(edited);
                con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            } else {
                stillEditing = false;
            }
        }
        return completeInventory;
    }

    private FlooringProduct editProduct(FlooringProduct toEdit) {
        con.display("Current Name: " + toEdit.getMaterialName());
        if (con.promptYesOrNo("Edit name? ")) {
            toEdit.setMaterialName(con.promptString("New material name: ")); // add in check!
        }
        con.display("Current Material Cost: " + toEdit.getMaterialCost());
        if (con.promptYesOrNo("Edit material cost? ")) {
            toEdit.setMaterialCost(con.promptPositiveDouble("New material cost: "));
        }
        con.display("Current Labor Cost: " + toEdit.getLaborCost());
        if (con.promptYesOrNo("Edit labor cost? ")) {
            toEdit.setLaborCost(con.promptPositiveDouble("New labor cost: "));
        }
        con.display("Current amount in stock: " + toEdit.getAmountInStock());
        if (con.promptYesOrNo("Edit amount in stock? ")) {
            toEdit.setAmountInStock(con.promptPositiveDouble("New amount in stock: "));
        }
        if (toEdit.isDiscontinued()) {
            toEdit.setDiscontinued(!con.promptYesOrNo(toEdit.getMaterialName() + " is discontinued. Would you like to make it available? "));
        }
        return toEdit;
    }

    @Override
    public String removeProduct(List<FlooringProduct> currentProducts) {
        con.display("Products Currently Available");
        displayProducts(currentProducts);
        con.display("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        con.display("Which would you like to discontinue?");
        int index = con.promptIntInRange("Or press 0 to go back. ", 0, currentProducts.size()) - 1;
        if (index < 0) {
            return ""; // don't do anything.
        } else {
            return currentProducts.get(index).getMaterialName();
        }
    }
}
