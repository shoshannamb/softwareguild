/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author apprentice
 */
public class DVD {

    private int id;

    @NotEmpty(message = "DVDs must have a title")
    @Length(max = 49, message = "'Title' must be less than 50 characters")
    private String title;

    @NotEmpty(message = "DVDs must have a release year")
    @Range(min = 1880, max = 2030)
    private String releaseYear;

    @Pattern(regexp = "G|PG|PG-13|R|NC-17|Not Rated", message = "Please select a valid MPAA rating")
    private String mpaaRating;

    @Length(max = 49, message = "'Director' must be less than 50 characters")
    private String director;

    @Length(max = 49, message = "'Director' must be less than 50 characters")
    private String studio;
    
    @Length(max = 249, message= "Notes must be less than 250 characters")
    private String note;

    public DVD(int id, String title, String releaseYear, String mpaaRating, String director, String studio, String note) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.mpaaRating = mpaaRating;
        this.director = director;
        this.studio = studio;
        this.note = note;
    }

    public DVD() {
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseDate(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
