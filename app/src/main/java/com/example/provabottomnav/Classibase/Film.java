package com.example.provabottomnav.Classibase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Film implements Parcelable {
    private int idfilm;
    private String immagine;
    private String anno;
    private String durata;
    private String genere;
    private String paese;
    private String titolo;
    private String registi;//List<String> registi;
    private String attori;//List<String> attori;
    private String trama;
    private String trailer;

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    public Film(int idfilm, String immagine, String anno, String durata, String genere, String paese, String titolo, String registi, String attori, String trama, String trailer) {
        this.idfilm = idfilm;
        this.immagine = immagine;
        this.anno = anno;
        this.durata = durata;
        this.genere = genere;
        this.paese = paese;
        this.titolo = titolo;
        this.registi = registi;
        this.attori = attori;
        this.trama = trama;
        this.trailer = trailer;
    }
    public Film(Parcel in) {
        anno = in.readString();
        attori = in.readString();
        durata=in.readString();
        genere=in.readString();
        idfilm = in.readInt();
        immagine = in.readString();
        paese=in.readString();
        registi=in.readString();
        titolo = in.readString();
        trama = in.readString();
        trailer=in.readString();

    }
    public static final Parcelable.Creator<Film> CREATOR=new Parcelable.Creator<Film>()
    {
        public Film createFromParcel(Parcel in)
        {
            return new Film(in);
        }

        public Film[] newArray(int size)
        {
            return new Film[size];
        }
    };
    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeString(anno);
        out.writeString(attori);
        out.writeString(durata);
        out.writeString(genere);
        out.writeInt(idfilm);
        out.writeString(immagine);
        out.writeString(paese);
        out.writeString(registi);
        out.writeString(titolo);
        out.writeString(trama);
        out.writeString(trailer);

    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public int getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(int idfilm) {
        this.idfilm = idfilm;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }
    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getRegisti() {
        return registi;
    }

    public void setRegisti(String registi) {
        this.registi = registi;
    }

    public String getAttori() {
        return attori;
    }

    public void setAttori(String attori) {
        this.attori = attori;
    }



}
