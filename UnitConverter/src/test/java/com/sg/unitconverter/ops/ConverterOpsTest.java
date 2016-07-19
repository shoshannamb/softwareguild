/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.unitconverter.ops;

import com.sg.unitconverter.model.Conversion;
import java.math.BigDecimal;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author apprentice
 */
public class ConverterOpsTest {
    private ConverterOps ops;
    
    
    public ConverterOpsTest() {
    }

    /**
     * Test of calculate method, of class ConverterOps.
     */
    @Test
    public void testCalculateMass() {
        ops = new MassOps();
        Conversion c = new Conversion("ounces", "kilograms", "27");
        
        c = ops.calculate(c);
        String actual = c.getEndingValue();
        
        Assert.assertEquals(0.765, Double.parseDouble(actual), 0.01);
    }
    
    @Test
    public void testCalculateTime() {
        ops = new TimeOps();
        Conversion c = new Conversion("weeks","hours","3");
        c = ops.calculate(c);
        String actual = c.getEndingValue();
        
        Assert.assertEquals(504.00, Double.parseDouble(actual), 0.01);
    }
    
    @Test
    public void testCalculateVolume() {
        ops = new VolumeOps();
        Conversion c = new Conversion("pints","milliliters", "34");
        c = ops.calculate(c);
        String actual = c.getEndingValue();
        
        Assert.assertEquals(16088.0, Double.parseDouble(actual), 0.1);
    }
    
}
