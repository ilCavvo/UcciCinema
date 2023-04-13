package com.example.provabottomnav;

import androidx.annotation.NonNull;

public enum Region {
    ABRUZZO("Abruzzo"),
    BASILICATA("Basilicata"),
    CALABRIA("Calabria"),
    CAMPANIA("Campania"),
    EMILIA_ROMAGNA("Emilia Romagna"),
    FRIULI_VENEZIA_GIULIA("Friuli Venezia Giulia"),
    LAZIO("Lazio"),
    LIGURIA("Liguria"),
    LOMBARDIA("Lombardia"),
    MARCHE("Marche"),
    MOLISE("Molise"),
    PIEMONTE("Piemonte"),
    PUGLIA("Puglia"),
    SARDEGNA("Sardegna"),
    SICILIA("Sicilia"),
    TOSCANA("Toscana"),
    TRENTINO_ALTO_ADIGE("Trentino Adige"),
    UMBRIA("Umbria"),
    VAL_D_AOSTA("Val d'Aosta"),
    VENETO("Veneto");
    private String regionName;

    private Region(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionName() {
        return regionName;
    }

    @NonNull
    @Override
    public String toString() {
        return regionName;
    }
    public static String[] getRegionStringArray(){
        String[] regionString = new String[20];
        int i=0;
        for (Region region: Region.values()) {
            regionString[i] = region.getRegionName();
            i++;
        }
        return regionString;
    }
}
