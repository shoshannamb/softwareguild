/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.unitconverter.ops;

import com.sg.unitconverter.model.Conversion;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author apprentice
 */
public class MassOps implements ConverterOps {

    // supports "Ounces", "Pounds", "Tons", "Grams", "Kilograms"
    @Override
    public Conversion calculate(Conversion toConvert) {
        BigDecimal valueInGrams = convertToGrams(toConvert.getStartingUnits().toLowerCase(), new BigDecimal(toConvert.getStartingValue()));
        
        BigDecimal convertedValue = toConvert.getEndingUnits().equalsIgnoreCase("grams") ? valueInGrams
                : toConvert.getEndingUnits().equalsIgnoreCase("ounces") ? convertGramsToOunces(valueInGrams)
                : toConvert.getEndingUnits().equalsIgnoreCase("pounds") ? convertGramsToPounds(valueInGrams)
                : toConvert.getEndingUnits().equalsIgnoreCase("tons") ? convertGramsToTons(valueInGrams)
                : toConvert.getEndingUnits().equalsIgnoreCase("kilograms") ? convertGramsToKilograms(valueInGrams)
                : new BigDecimal(""); // empty BigDecimal as default

        toConvert.setEndingValue(convertedValue.toString());
        return toConvert;
    }

    BigDecimal convertToGrams(String startingUnits, BigDecimal startingValue) {
        switch (startingUnits) {
            case "grams":
                return startingValue;
            case "ounces":
                return startingValue.multiply(new BigDecimal("28.3495"));
            case "pounds":
                return startingValue.multiply(new BigDecimal("453.592"));
            case "tons":
                return startingValue.multiply(new BigDecimal("907185"));
            case "kilograms":
                return startingValue.multiply(new BigDecimal("1000"));
            default:
                return new BigDecimal("");
        }
    }

    private BigDecimal convertGramsToOunces(BigDecimal valueInGrams) {
        return valueInGrams.divide(new BigDecimal("28.3495"), 10, RoundingMode.HALF_UP);
    }

    private BigDecimal convertGramsToPounds(BigDecimal valueInGrams) {
        return valueInGrams.divide(new BigDecimal("453.592"), 10, RoundingMode.HALF_UP);
    }

    private BigDecimal convertGramsToTons(BigDecimal valueInGrams) {
        return valueInGrams.divide(new BigDecimal("907185"), 10, RoundingMode.HALF_UP);
    }

    private BigDecimal convertGramsToKilograms(BigDecimal valueInGrams) {
        return valueInGrams.divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP);
    }

}
