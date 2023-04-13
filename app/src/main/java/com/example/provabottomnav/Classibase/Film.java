package com.example.provabottomnav.Classibase;

import java.util.List;

public class Film {

    private String titolo;
    private String registi;//List<String> registi;
    private String attori;//List<String> attori;
    private String trama;

    public Film(String titolo, String registi, String attori, String trama) {
        this.titolo = titolo;
        this.registi = registi;
        this.attori = attori;
        this.trama = trama;
    }

    public Film(String titolo) {
        this.titolo = titolo;
    }

    public Film() {
    }

    public String getTitolo() {
        return titolo;
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

}
