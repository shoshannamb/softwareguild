/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author apprentice
 */
public class InputFormatterTest {

    public InputFormatterTest() {
    }
    
    InputFormatter formatter = new InputFormatter();
    
    @Before
    public void setUp() {
        Map<String,String> testStates = new HashMap<>();
        testStates.put("ALABAMA", "AL");
        testStates.put("ALASKA","AK");
        testStates.put("ARIZONA", "AZ");
        formatter.setStateAbbreviations(testStates);
    }

    /**
     * Test of stateExists method, of class InputFormatter.
     */
    @Test
    public void testStateExistsLongTrue() {
        boolean expected = true;
        boolean actual = formatter.stateExists("alaska");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testStateExistsShortTrue() {
        boolean expected = true;
        boolean actual = formatter.stateExists("aZ");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testStateExistsFale() {
        boolean expected = false;
        boolean actual = formatter.stateExists("aksdjfaks");
        Assert.assertEquals(expected, actual);
    }

    /**
     * Test of convertToStateAbbreviation method, of class InputFormatter.
     */
    @Test
    public void testConvertToStateAbbreviation() {
        String expected = "AK";
        String actual = formatter.convertToStateAbbreviation("aLasKa");
        Assert.assertEquals(expected, actual);
    }

    /**
     * Test of getStateAbbreviations method, of class InputFormatter.
     */
    @Test
    public void testGetStateAbbreviations() {
    }

    /**
     * Test of setStateAbbreviations method, of class InputFormatter.
     */
    @Test
    public void testSetStateAbbreviations() {
    }

}
