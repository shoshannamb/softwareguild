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
public class TimeOps implements ConverterOps {

    // supports seconds, minutes, hours, days, weeks, years
    @Override
    public Conversion calculate(Conversion toConvert) {
        BigDecimal valueInSeconds = convertToSeconds(toConvert.getStartingUnits().toLowerCase(), new BigDecimal(toConvert.getStartingValue()));
        BigDecimal convertedValue = toConvert.getEndingUnits().equalsIgnoreCase("seconds") ? valueInSeconds
                : toConvert.getEndingUnits().equalsIgnoreCase("minutes") ? convertSecondsToMinutes(valueInSeconds)
                : toConvert.getEndingUnits().equalsIgnoreCase("hours") ? convertSecondsToHours(valueInSeconds)
                : toConvert.getEndingUnits().equalsIgnoreCase("days") ? convertSecondsToDays(valueInSeconds)
                : toConvert.getEndingUnits().equalsIgnoreCase("weeks") ? convertSecondsToWeeks(valueInSeconds)
                : toConvert.getEndingUnits().equalsIgnoreCase("years") ? convertSecondsToYears(valueInSeconds)
                : new BigDecimal(""); // empty BigDecimal as default

        toConvert.setEndingValue(convertedValue.toString());
        return toConvert;
    }

    private BigDecimal convertToSeconds(String startingUnits, BigDecimal startingValue) {
        switch (startingUnits) {
            case "seconds":
                return startingValue;
            case "minutes":
                return startingValue.multiply(new BigDecimal("60"));
            case "hours":
                return startingValue.multiply(new BigDecimal("3600"));
            case "days":
                return startingValue.multiply(new BigDecimal("86400"));
            case "weeks":
                return startingValue.multiply(new BigDecimal("604800"));
            case "years":
                return startingValue.multiply(new BigDecimal("3.154e7"));
            default:
                return new BigDecimal("");
        }
    }

    private BigDecimal convertSecondsToMinutes(BigDecimal valueInSeconds) {
        return valueInSeconds.divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP);
    }

    private BigDecimal convertSecondsToHours(BigDecimal valueInSeconds) {
        return valueInSeconds.divide(new BigDecimal("3600"), 10, RoundingMode.HALF_UP);

    }

    private BigDecimal convertSecondsToDays(BigDecimal valueInSeconds) {
        return valueInSeconds.divide(new BigDecimal("86400"), 10, RoundingMode.HALF_UP);
    }

    private BigDecimal convertSecondsToWeeks(BigDecimal valueInSeconds) {
        return valueInSeconds.divide(new BigDecimal("604800"), 10, RoundingMode.HALF_UP);
    }

    private BigDecimal convertSecondsToYears(BigDecimal valueInSeconds) {
        return valueInSeconds.divide(new BigDecimal("3.154e7"), 10, RoundingMode.HALF_UP);
    }

}
