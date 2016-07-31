/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.model.DVD;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface DVDLibraryDAO {
    public void addDVD(DVD newDVD);
    public DVD getById(int dvdID);
    public void deleteDVD(int dvdID);
    public List<DVD> getDVDLibrary();
    public void edit(DVD toEdit);
    public boolean saveProgress();
    public boolean loadLibrary();
}
