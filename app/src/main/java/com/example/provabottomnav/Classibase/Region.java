package com.example.provabottomnav.Classibase;

import androidx.annotation.NonNull;

import java.util.Locale;

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
    VENETO("Veneto"),
    NULLREGION(null);
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

    public static String[] getRegionStringArray() {
        String[] regionString = new String[20];
        int i = 0;
        for (Region region : Region.values()) {
            regionString[i] = region.getRegionName();
            i++;
            if (i == 20) {
                return regionString;
            }
        }
        return regionString;
    }

    public static Region convertToRegion(String regionToConvert) {
        for (Region region : Region.values()) {
            if (region.getRegionName().toLowerCase().equals(regionToConvert.toLowerCase())) {
                return region;
            }
        }
        return NULLREGION;
    }
}
