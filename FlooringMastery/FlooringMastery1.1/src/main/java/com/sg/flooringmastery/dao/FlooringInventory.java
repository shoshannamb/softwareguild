/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.FlooringProduct;
import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface FlooringInventory {

    public boolean loadInventory();

    public List<FlooringProduct> getCurrentProducts();

    public List<FlooringProduct> getInStockProducts();

    public List<FlooringProduct> getCompleteInventory();

    public List<FlooringProduct> getLowSupplyProducts(double maxSqFt);

    public List<FlooringProduct> getOutOfStockProducts();

    public void returnMaterialsToInventory(Order o);

    public void removeMaterialsFromInventory(Order o);

    public void addNewProduct(FlooringProduct newProduct);
    
    public void editProducts(List<FlooringProduct> toEdit);

    public void removeProduct(String productToRemove);

    public boolean isAbleToSave();

    public boolean save();
}
