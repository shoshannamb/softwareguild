/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.FlooringProduct;
import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author apprentice
 */
public class FlooringInventoryProdDAO implements FlooringInventory {

    private Map<String, FlooringProduct> inventory = new HashMap<>();
    private boolean ableToSave = true;

    @Override
    public boolean loadInventory() {
        Scanner productReader;

        try {
            productReader = new Scanner(new BufferedReader(new FileReader("textFiles/productInventory.txt")));
            productReader.nextLine();
        } catch (FileNotFoundException ex) {
            return false;
        }

        while (productReader.hasNextLine()) {
            String[] productInfo = productReader.nextLine().split(",");
            FlooringProduct product = new FlooringProduct();
            if (productInfo[0].charAt(0) == '*') {
                product.setDiscontinued(true); // * is flag for a discontinued product
                productInfo[0] = productInfo[0].substring(1); // digest first character
            }
            product.setMaterialName(productInfo[0]);
            product.setMaterialCost(Double.parseDouble(productInfo[1]));
            product.setLaborCost(Double.parseDouble(productInfo[2]));
            product.setAmountInStock(Double.parseDouble(productInfo[3]));
            inventory.put(product.getMaterialName(), product);
        }
        return true;
    }

    @Override
    public List<FlooringProduct> getCompleteInventory() {
        return inventory.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<FlooringProduct> getLowSupplyProducts(double maxSqFt) {
        return getCurrentProducts().stream().filter(p -> p.getAmountInStock() < maxSqFt).collect(Collectors.toList());
    }

    @Override
    public List<FlooringProduct> getOutOfStockProducts() {
        return getCurrentProducts().stream().filter(p -> p.getAmountInStock() <= 0).collect(Collectors.toList());
    }

    @Override
    public void addNewProduct(FlooringProduct newProduct) {
        inventory.put(newProduct.getMaterialName(), newProduct);
    }

    @Override
    public void editProducts(List<FlooringProduct> products) {
        products.forEach(p -> inventory.put(p.getMaterialName(), p));
    }

    @Override
    public void removeProduct(String productToRemove) {
        if (inventory.containsKey(productToRemove)) {
            inventory.get(productToRemove).setDiscontinued(true);
        }
    }

    @Override
    public boolean isAbleToSave() {
        return this.ableToSave;
    }

    @Override
    public boolean save() {
        try (PrintWriter out = new PrintWriter(new FileWriter("textFiles/productInventory.txt"))) {
            out.println("ProductType,CostPerSquareFoot,LaborCostPerSquareFoot,SqFeetInStock");
            inventory.values().stream().forEach(p -> out.println(encodeFlooringProduct(p)));
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    private String encodeFlooringProduct(FlooringProduct toEncode) {
        String encodedProduct = "";
        if (toEncode.isDiscontinued()) {
            encodedProduct += "*";
        }
        encodedProduct += toEncode.getMaterialName() + "," + toEncode.getMaterialCost() + "," + toEncode.getLaborCost() + "," + toEncode.getAmountInStock();
        return encodedProduct;
    }

    @Override
    public void returnMaterialsToInventory(Order o) {
        double currentAmount = inventory.get(o.getProductType()).getAmountInStock();
        inventory.get(o.getProductType()).setAmountInStock(currentAmount + o.getArea());
    }

    @Override
    public void removeMaterialsFromInventory(Order o) {
        double currentAmount = inventory.get(o.getProductType()).getAmountInStock();
        inventory.get(o.getProductType()).setAmountInStock(currentAmount - o.getArea());
    }

    @Override
    public List<FlooringProduct> getInStockProducts() {
        return getCurrentProducts().stream().filter(p -> p.getAmountInStock() > 0).collect(Collectors.toList());
    }

    @Override
    public List<FlooringProduct> getCurrentProducts() {
        return inventory.values().stream().filter(p -> !p.isDiscontinued()).collect(Collectors.toList());
    }
}
