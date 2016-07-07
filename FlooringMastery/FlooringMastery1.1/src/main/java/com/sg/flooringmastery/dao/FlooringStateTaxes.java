/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.TaxRates;
import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */
public interface FlooringStateTaxes {
    public boolean loadTaxRates();
    public boolean saveTaxRates();
    public boolean getCanSave();
    public TaxRates getTaxRateForState(String stateAbbreviation);
    public void setTaxRateForState(TaxRates editedTR);
    public Map<String,String> getStateNames();
    public Order setOrderTaxRate(Order newOrder);
}
