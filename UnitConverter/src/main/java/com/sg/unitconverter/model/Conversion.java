/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.unitconverter.model;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;


/**
 *
 * @author apprentice
 */
public class Conversion {
    private String startingUnits;
    private String endingUnits;
    
    @NotEmpty
    @Digits(integer=20, fraction=20)
    private String startingValue;
    private String endingValue;
    
    public Conversion() {
    }

    public Conversion(String startingUnits, String endingUnits, String startingValue) {
        this.startingUnits = startingUnits;
        this.endingUnits = endingUnits;
        this.startingValue = startingValue;
    }

    public String getStartingUnits() {
        return startingUnits;
    }

    public String getEndingUnits() {
        return endingUnits;
    }

    public String getStartingValue() {
        return startingValue;
    }

    public String getEndingValue() {
        return endingValue;
    }

    public void setEndingValue(String endingValue) {
        this.endingValue = endingValue;
    }

    public void setStartingUnits(String startingUnits) {
        this.startingUnits = startingUnits;
    }

    public void setEndingUnits(String endingUnits) {
        this.endingUnits = endingUnits;
    }

    public void setStartingValue(String startingValue) {
        this.startingValue = startingValue;
    }
}
