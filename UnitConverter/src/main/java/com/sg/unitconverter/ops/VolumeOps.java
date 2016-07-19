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
public class VolumeOps implements ConverterOps {

    // supports "Milliliters", "Liters", "Teaspoons", "Tablespoons", "Cups", "Quarts", "Pints", "Gallons"
    @Override
    public Conversion calculate(Conversion toConvert) {
        BigDecimal valueInmL = convertTomL(toConvert.getStartingUnits().toLowerCase(), new BigDecimal(toConvert.getStartingValue()));
        
        BigDecimal convertedValue = toConvert.getEndingUnits().equalsIgnoreCase("milliliters") ? valueInmL
                : toConvert.getEndingUnits().equalsIgnoreCase("liters") ? convertmLToLiters(valueInmL)
                : toConvert.getEndingUnits().equalsIgnoreCase("teaspoons") ? convertmLToTsp(valueInmL)
                : toConvert.getEndingUnits().equalsIgnoreCase("tablespoons") ? convertmLToTbsp(valueInmL)
                : toConvert.getEndingUnits().equalsIgnoreCase("cups") ? convertmLToCups(valueInmL)
                : toConvert.getEndingUnits().equalsIgnoreCase("quarts") ? convertmLToQuarts(valueInmL)
                : toConvert.getEndingUnits().equalsIgnoreCase("pints") ? convertmLToPints(valueInmL)
                : toConvert.getEndingUnits().equalsIgnoreCase("gallons") ? convertmLToGallons(valueInmL)
                : new BigDecimal(""); // empty BigDecimal as default

        toConvert.setEndingValue(convertedValue.toString());
        return toConvert;
    }

    BigDecimal convertTomL(String startingUnits, BigDecimal startingValue) {
        switch (startingUnits) {
            case "milliliters":
                return startingValue;
            case "liters":
                return startingValue.multiply(new BigDecimal("1000"));
            case "teaspoons":
                return startingValue.multiply(new BigDecimal("4.92892"));
            case "tablespooons":
                return startingValue.multiply(new BigDecimal("14.7868"));
            case "cups":
                return startingValue.multiply(new BigDecimal("236.588"));
            case "quarts":
                return startingValue.multiply(new BigDecimal("946.353"));
            case "pints":
                return startingValue.multiply(new BigDecimal("473.176"));
            case "gallons":
                return startingValue.multiply(new BigDecimal("3785.41"));
            default:
                return new BigDecimal("");
        }
    }

    private BigDecimal convertmLToLiters(BigDecimal valueInmL) {
        return valueInmL.divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP);
    }

    private BigDecimal convertmLToTsp(BigDecimal valueInmL) {
        return valueInmL.divide(new BigDecimal("4.92892"), 10, RoundingMode.HALF_UP);
    }

    private BigDecimal convertmLToTbsp(BigDecimal valueInmL) {
        return valueInmL.divide(new BigDecimal("14.7868"), 10, RoundingMode.HALF_UP);
    }

    private BigDecimal convertmLToCups(BigDecimal valueInmL) {
        return valueInmL.divide(new BigDecimal("236.588"), 10, RoundingMode.HALF_UP);
    }

    private BigDecimal convertmLToQuarts(BigDecimal valueInmL) {
        return valueInmL.divide(new BigDecimal("946.353"), 10, RoundingMode.HALF_UP);
    }

    private BigDecimal convertmLToPints(BigDecimal valueInmL) {
        return valueInmL.divide(new BigDecimal("473.176"), 10, RoundingMode.HALF_UP);
    }

    private BigDecimal convertmLToGallons(BigDecimal valueInmL) {
        return valueInmL.divide(new BigDecimal("3785.41"), 10, RoundingMode.HALF_UP);
    }

}
