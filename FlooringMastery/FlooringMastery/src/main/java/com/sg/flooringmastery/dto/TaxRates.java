/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.util.Map;

/**
 *
 * @author apprentice
 */
public class TaxRates {
    private Map<String, Double> taxRates;

    public Map<String, Double> getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(Map<String, Double> taxRates) {
        this.taxRates = taxRates;
    }

}
