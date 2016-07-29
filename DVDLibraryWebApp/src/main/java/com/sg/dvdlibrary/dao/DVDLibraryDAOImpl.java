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
    public boolean saveProgress(String fileName) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(fileName));
            for (DVD dvd : dvdLibrary.values()) {
                String coded = dvd.getID() + "::" + dvd.getTitle() + "::" + dvd.getReleaseYear() + "::" + dvd.getMpaaRating() + "::" + dvd.getDirector() + "::" + dvd.getStudio() + "::" + dvd.getNote();
                // handle null strings for note
                if (dvd.getNote().equals("")) {
                    coded += "null";
                }
                out.println(coded);
            }
            out.flush();
            out.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    // decode file
    @Override
    public boolean loadLibrary(String fileName) {
        try {
            Scanner fRead = new Scanner(new BufferedReader(new FileReader(fileName)));
            while (fRead.hasNextLine()) {
                String[] fields = fRead.nextLine().split("::");
                // handle null strings
                if (fields[6].equals("null")) {
                    fields[6] = "";
                }
                DVD d = new DVD(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3], fields[4], fields[5], fields[6]);
                dvdLibrary.put(d.getID(), d);
                dvdCounter = d.getID() + 1;
            }
            fRead.close();
            return true;
        } catch (FileNotFoundException ex) {
            return false;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}
