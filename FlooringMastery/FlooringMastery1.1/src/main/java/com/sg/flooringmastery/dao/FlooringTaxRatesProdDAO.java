/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.TaxRates;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author apprentice
 */
public class FlooringTaxRatesProdDAO implements FlooringStateTaxes {

    private boolean canSave = true;
    private List<TaxRates> taxRates;

    @Override
    public boolean loadTaxRates() {
        taxRates = new ArrayList<>();
        Scanner taxReader;

        try {
            taxReader = new Scanner(new BufferedReader(new FileReader("textFiles/taxRates.txt")));
            taxReader.nextLine();         // Skip the header
        } catch (FileNotFoundException ex) {
            return false;
        }

        while (taxReader.hasNextLine()) {
            String[] taxInfo = taxReader.nextLine().split(",");
            TaxRates state = new TaxRates();
            state.setStateAbbreviation(taxInfo[0]);
            state.setTaxRate(Double.parseDouble(taxInfo[1]));
            state.setLongStateName(taxInfo[2]);
            taxRates.add(state);
        }
        return true;
    }

    @Override
    public boolean saveTaxRates() {
        try (PrintWriter out = new PrintWriter(new FileWriter("textFiles/taxRates.txt"))) {
            out.println("State,taxRate,LongStateName");
            taxRates.stream().forEach(t -> out.println(encodeTaxRate(t)));
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    private String encodeTaxRate(TaxRates tr) {
        return tr.getStateAbbreviation() + "," + Double.toString(tr.getTaxRate()) + "," + tr.getLongStateName();
    }

    @Override
    public boolean getCanSave() {
        return this.canSave;
    }

    @Override
    public TaxRates getTaxRateForState(String stateAbbreviation) {
        return taxRates.stream().filter(t -> t.getStateAbbreviation().equals(stateAbbreviation)).collect(Collectors.toList()).get(0);
    }

    @Override
    public void setTaxRateForState(TaxRates editedTaxRate) {
        taxRates.removeIf(t -> t.getStateAbbreviation().equals(editedTaxRate.getStateAbbreviation()));
        taxRates.add(editedTaxRate);
    }

    @Override
    public Map<String, String> getStateNames() {
        Map<String, String> loadedStateNames = new HashMap<>();
        Scanner stateReader;

        try {
            stateReader = new Scanner(new BufferedReader(new FileReader("textFiles/taxRates.txt")));
            stateReader.nextLine();
        } catch (Exception ex) {
            return loadedStateNames; // return empty object
        }

        while (stateReader.hasNextLine()) {
            String[] stateInfo = stateReader.nextLine().split(",");
            loadedStateNames.put(stateInfo[2], stateInfo[0]);
        }
        return loadedStateNames;
    }

    @Override
    public Order setOrderTaxRate(Order order) {
        String state = order.getState();
        order.setTaxRate(getTaxRateForState(state).getTaxRate());
        return order;
    }
}
