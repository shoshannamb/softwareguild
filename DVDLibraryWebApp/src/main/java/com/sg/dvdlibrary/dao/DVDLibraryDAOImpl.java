/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.model.DVD;
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

/**
 *
 * @author apprentice
 */
public class DVDLibraryDAOImpl implements DVDLibraryDAO {

    private Map<Integer, DVD> dvdLibrary;
    private int dvdCounter;

    public DVDLibraryDAOImpl() {
        dvdLibrary = new HashMap<>();
        dvdCounter = 0;
    }

    @Override
    public void addDVD(DVD dvd) {
        dvd.setID(dvdCounter);
        dvdLibrary.put(dvdCounter, dvd);
        dvdCounter++;
    }

    @Override
    public DVD getById(int dvdID) {
        return dvdLibrary.get(dvdID);
    }

    // Remove the dvd object
    @Override
    public void deleteDVD(int dvdID) {
        dvdLibrary.remove(dvdID);
    }

    // getter for library ArrayList
    @Override
    public List<DVD> getDVDLibrary() {
        return new ArrayList(dvdLibrary.values());
    }

    // match the DVD object
    // update fields from String[]
    @Override
    public void edit(DVD toEdit) {
        dvdLibrary.put(toEdit.getID(), toEdit);
    }

    // encode file
    @Override
    public boolean saveProgress() {
        return false; // this is a demo and progress cannot be saved.
    }

    // decode file
    @Override
    public boolean loadLibrary() {
        // dummy data
        dvdLibrary = new HashMap<>();
        DVD d1 = new DVD(0,"The Shawshank Redemption","1994","R","Frank Darabont","Castle Rock Entertainment","Fear can hold you prisoner. Hope can set you free.");
        DVD d2 = new DVD(1,"The Lion King","1995","G","Roger Allers, Rob Minkoff","Disney","Hakuna matata!");
        DVD d3 = new DVD(2,"Buffy the Vampire Slayer","1992","PG-13","Fran Rubel Kuzui","Fox","Does the word \"duh\" mean anything to you?");
        DVD d4 = new DVD(3,"FernGully: The Last Rainforest","1992","G","Bill Kroyer","Fai Films","Do you believe in humans?");
        DVD d5 = new DVD(4,"Ghostbusters","1984","PG","Ivan Reitman","Black Rhino Productions","Who ya gonna call?");
        DVD d6 = new DVD(5,"Princess Mononoke","1997","PG-13","Hayao Miyazaki","DENTSU Music And Entertainment","See with eyes unclouded by hate");
        
        addDVD(d1);
        addDVD(d2);
        addDVD(d3);
        addDVD(d4);
        addDVD(d5);
        addDVD(d6);
        
        return true;
    }

}
