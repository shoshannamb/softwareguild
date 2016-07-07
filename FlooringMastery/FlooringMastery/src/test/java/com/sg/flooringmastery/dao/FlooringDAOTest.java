/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.FlooringProduct;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.TaxRates;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class FlooringDAOTest {

    public FlooringDAOTest() {
    }

    FlooringDAO dao;

    @Before
    public void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = (FlooringDAO) ctx.getBean("flooringListDAO");
    }

    /**
     * Test of addOrder method, of class FlooringDemoDAO.
     */
    @Test
    public void testAddOrderDateInFile() {
        Order o = new Order();
        o.setDate("03241986");
        o.setCustomerName("Shoshanna Barnett");
        String expected = "Shoshanna Barnett";

        int orderNum = dao.addOrder(o);
        String[] orderInfo = {"03241986", Integer.toString(orderNum)};
        Order testOrder = dao.getSingleOrder(orderInfo);
        String actual = testOrder.getCustomerName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAddOrderNewDate() {
        FlooringDemoDAO dao = new FlooringDemoDAO();
        Order o = new Order();
        o.setDate("07221999");
        o.setCustomerName("Shoshanna Barnett");
        String expected = "Shoshanna Barnett";

        int orderNum = dao.addOrder(o);
        String[] orderInfo = {"07221999", Integer.toString(orderNum)};
        Order testOrder = dao.getSingleOrder(orderInfo);
        String actual = testOrder.getCustomerName();

        Assert.assertEquals(expected, actual);
    }

    /**
     * Test of removeOrder method, of class FlooringDemoDAO.
     */
    @Test
    public void testRemoveOrder() {
        int expected = dao.getOrdersFromDay("03241986").size() - 1;
        Order o = new Order();
        o.setDate("03241986");
        o.setOrderNum(1);
        dao.removeOrder(o);
        int actual = dao.getOrdersFromDay("03241986").size();

        Assert.assertEquals(expected, actual);
    }

    /**
     * Test of getOrdersFromDay method, of class FlooringDemoDAO.
     */
    @Test
    public void testGetOrdersFromDayDoesNotExist() {
        int expected = 0;
        List<Order> orders = dao.getOrdersFromDay("MMDDYYYY");
        int actual = orders.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetOrdersFromDayInMemory() {
        int expected = 13;
        dao.getOrdersFromDay("03241986");
        int actual = dao.getOrdersFromDay("03241986").size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetOrdersFromDayInFile() {
        int expected = 13;
        int actual = dao.getOrdersFromDay("03241986").size();
        Assert.assertEquals(expected, actual);
    }

    /**
     * Test of getSingleOrder method, of class FlooringDemoDAO.
     */
    @Test
    public void testGetSingleOrderDoesNotExist() {
        int expected = 0;
        String[] orderInfo = {"DDMMYYYY", "27"};
        Order testOrder = dao.getSingleOrder(orderInfo);
        int actual = testOrder.getOrderNum();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetSingleOrderExistsInMemory() {
        String expected = "Kato R. Jefferson";
        String[] orderInfo = {"03241986", "8"};
        dao.getOrdersFromDay("03241986");
        Order testOrder = dao.getSingleOrder(orderInfo);
        String actual = testOrder.getCustomerName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetSingleOrderExistsInFile() {
        FlooringDemoDAO dao = new FlooringDemoDAO();
        String expected = "Kato R. Jefferson";
        String[] orderInfo = {"03241986", "8"};
        Order testOrder = dao.getSingleOrder(orderInfo);
        String actual = testOrder.getCustomerName();
        Assert.assertEquals(expected, actual);
    }

    /**
     * Test of editOrder method, of class FlooringDemoDAO.
     */
    @Test
    public void testEditOrder() {
        Order o = new Order();
        o.setCustomerName("Shoshanna Barnett");
        o.setDate("03241986");
        o.setOrderNum(7);
        String expected = o.getCustomerName();
        dao.getOrdersFromDay("03241986");

        dao.editOrder(o);
        String[] orderInfo = {"03241986", "7"};
        Order testOrder = dao.getSingleOrder(orderInfo);
        String actual = testOrder.getCustomerName();

        Assert.assertEquals(expected, actual);
    }

    /**
     * Test of loadInventory method, of class FlooringDemoDAO.
     */
    @Test
    public void testLoadInventory() {
        double expected = 4.15;
        FlooringDemoDAO dao = new FlooringDemoDAO();
        Map<String, FlooringProduct> inventory = dao.loadInventory();
        double actual = inventory.get("Tile").getLaborCost();
        Assert.assertEquals(expected, actual, 0.0);
    }

    /**
     * Test of loadTaxRates method, of class FlooringDemoDAO.
     */
    @Test
    public void testLoadTaxRates() {
        FlooringDemoDAO dao = new FlooringDemoDAO();
        TaxRates taxRates = new TaxRates();
        taxRates = dao.loadTaxRates(taxRates);
        double expected = 6.66;
        double actual = taxRates.getTaxRates().get("FL");
        Assert.assertEquals(expected, actual, 0.0);
    }

}
