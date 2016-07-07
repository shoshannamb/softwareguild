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
    private String stateAbbreviation;
    private String longStateName;
    private double taxRate;

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public String getLongStateName() {
        return longStateName;
    }

    public void setLongStateName(String longStateName) {
        this.longStateName = longStateName;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

}
