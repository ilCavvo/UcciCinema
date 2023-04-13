package com.example.provabottomnav;

public class Cinema {

    private String name;
    private int cinemaRoomsNumber;

    private Region region;
    private String indirizzo;


    public Cinema(String name, int cinemaRoomsNumber) {
        this.name = name;
        this.cinemaRoomsNumber = cinemaRoomsNumber;
    }

    public Cinema(){

    }

    public String getName() {
        return name;
    }

    public int getCinemaRoomsNumber() {
        return cinemaRoomsNumber;
    }

    public Region getRegion() {
        return region;
    }

    public String getIndirizzo() {
        return indirizzo;
    }
}