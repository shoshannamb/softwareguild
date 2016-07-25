package com.sg.dvdlibrary;

import com.sg.dvdlibrary.dao.DVDLibraryDAO;
import com.sg.dvdlibrary.model.DVD;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class DVDLibraryController {

    private DVDLibraryDAO dao;

    @Inject
    public DVDLibraryController(DVDLibraryDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String displayHomePage() {
        return "home";
    }
    
    // get a DVD
    // RM: /dvd/{dvd-id} GET
    @RequestMapping(value="/dvd/{dvdID}", method=RequestMethod.GET)
    @ResponseBody
    public DVD getByID(@PathVariable("dvdID") int dvdID) {
        return dao.getById(dvdID);
    }
    
    // add a DVD
    //RM: /dvd POST
    @RequestMapping(value="/dvd", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public DVD addDVD(@Valid @RequestBody DVD dvd) {
        dao.addDVD(dvd);
        return dvd;
    }
    
    // get all DVDs
    // RM: /all-dvds GET
    @RequestMapping(value="/all-dvds", method=RequestMethod.GET)
    @ResponseBody
    public List<DVD> getAllDVDs() {
        return dao.getDVDLibrary();
    }
    
    // remove a DVD
    // RM: /dvd/{dvd-id} DELETE
    @RequestMapping(value="dvd/{dvdID}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDVD(@PathVariable("dvdID") Integer dvdID) {
        dao.deleteDVD(dvdID);
    }
    
    // edit a DVD
    // RM: /dvd/{dvd-id} PUT
    @RequestMapping(value="dvd/{dvdId}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editDVD(@PathVariable("dvdId") int dvdID, @Valid @RequestBody DVD dvd) {
        dvd.setID(dvdID);
        dao.edit(dvd);
    }

}
