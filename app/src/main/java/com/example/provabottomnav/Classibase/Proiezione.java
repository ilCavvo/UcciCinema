package com.example.provabottomnav.Classibase;

import com.example.provabottomnav.Classibase.Cinema;
import com.example.provabottomnav.Classibase.Film;

import java.time.LocalDateTime;

public class Proiezione {
    private Cinema nome;
    private Film titolo;
    private LocalDateTime oraData;

    public Proiezione(Cinema nome, Film titolo, LocalDateTime oraData) {
        this.nome = nome;
        this.titolo = titolo;
        this.oraData = oraData;
    }

    public Proiezione(Cinema nome, Film titolo) {
        this.nome = nome;
        this.titolo = titolo;
    }

    public Proiezione() {
    }

    public Cinema getNome() {
        return nome;
    }

    public Film getTitolo() {
        return titolo;
    }

    public LocalDateTime getOraData() {
        return oraData;
    }

    public void setOraData(LocalDateTime oraData) {
        this.oraData = oraData;
    }
}
