/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.util.Map;

/**
 *
 * @author apprentice
 */
public class InputFormatter {
    
    // all String are in upper case
    private Map<String, String> stateAbbreviations;
    
    public boolean stateExists(String s) {
        if(stateAbbreviations.containsKey(s.toUpperCase()) || stateAbbreviations.containsValue(s.toUpperCase()))
            return true;
        return false;
    }
    
    public String convertToStateAbbreviation(String longStateName) {
        return stateAbbreviations.get(longStateName.toUpperCase());
    }

    public Map<String, String> getStateAbbreviations() {
        return stateAbbreviations;
    }

    public void setStateAbbreviations(Map<String, String> stateAbbreviations) {
        this.stateAbbreviations = stateAbbreviations;
    }
}
