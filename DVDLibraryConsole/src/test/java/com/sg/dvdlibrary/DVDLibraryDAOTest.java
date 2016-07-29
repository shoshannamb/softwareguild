/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary;

import com.sg.dvdlibrary.dao.DVDLibraryDAO;
import com.sg.dvdlibrary.dao.DVDLibraryDAOImpl;
import com.sg.dvdlibrary.model.DVD;
import java.util.ArrayList;
import javafx.application.Application;
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
public class DVDLibraryDAOTest {
    
    private DVD dvd1;
    private DVD dvd2;
    private DVDLibraryDAO lib;
    
    public DVDLibraryDAOTest() {
    }
    
    @Before
    public void setUp() {
        dvd1 = new DVD();
        dvd1.setTitle("Ghostbusters");
        dvd1.setReleaseDate("1984");
        dvd1.setMpaaRating("PG-13");
        dvd1.setNote("AWESOME!!!");
        
        dvd2 = new DVD();
        dvd2.setTitle("The Lion King");
        dvd2.setReleaseDate("1994");
        dvd2.setMpaaRating("G");
        dvd2.setStudio("Disney");
        dvd2.setNote("Ari's favorite");
        
        ApplicationContext ctx = 
        new ClassPathXmlApplicationContext("test-applicationContext.xml");
        lib = ctx.getBean("dvdLibraryDAO", DVDLibraryDAO.class);
    } 

    /**
     * Test of addDVD method, of class DVDLibraryDAO.
     */
    @Test
    public void testAddGetDVD() {
        lib.addDVD(dvd1);
        lib.addDVD(dvd2);
        
        Assert.assertEquals(1, dvd2.getID());
        
        
        Assert.assertEquals("The Lion King", lib.getById(1).getTitle());
    }

    /**
     * Test of delete method, of class DVDLibraryDAO.
     */
    @Test
    public void testDeleteGetAll() {
        lib.addDVD(dvd1);
        lib.addDVD(dvd2);
        
        lib.deleteDVD(0);
        
        Assert.assertEquals(1, lib.getDVDLibrary().size());
    }

    /**
     * Test of edit method, of class DVDLibraryDAO.
     */
    @Test
    public void testEdit() {
        lib.addDVD(dvd1);
        lib.addDVD(dvd2);
        
        dvd1.setTitle("Ghostbusters II");
        lib.edit(dvd1);
        
        Assert.assertEquals("Ghostbusters II", lib.getById(0).getTitle());
    }

}
