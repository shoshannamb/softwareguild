/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.FlooringProduct;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.TaxRates;
import com.sg.flooringmastery.ui.InputFormatter;
import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */
public interface FlooringDAO {

    // will assign an orderNumber and add it to the order container
    public int addOrder(Order newOrder);

    // date is in format MMDDYYYY
    public void removeOrder(Order toRemove);

    public List<Order> getOrdersFromDay(String date);

    public Order getSingleOrder(String[] dateAndOrderNumber);

    public void editOrder(Order editedOrder);

    public boolean saveOrderChanges();

    public boolean getCanSave();

    public Map<String, FlooringProduct> loadInventory();

    public TaxRates loadTaxRates(TaxRates emptyTRObject);

    public Map<String, String> loadStateAbbreviations();
}
