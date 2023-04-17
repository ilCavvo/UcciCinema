package com.example.provabottomnav.Classibase;

public class Cinema {

    private String name;
    private int cinemaRoomsNumber;
    private String telefono;
    private Region region;
    private String indirizzo;

    public Cinema(String name, int cinemaRoomsNumber, String telefono, String indirizzo) {
        this.name = name;
        this.cinemaRoomsNumber = cinemaRoomsNumber;
        this.telefono = telefono;
        this.region = region;
        this.indirizzo = indirizzo;
    }

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
