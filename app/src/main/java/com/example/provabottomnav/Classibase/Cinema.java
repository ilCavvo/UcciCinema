package com.example.provabottomnav.Classibase;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class Cinema implements Parcelable{

    private String name;
    private int cinemaRoomsNumber;
    private String telefono;
    public String region;
    private String indirizzo;

    @Override
    public String toString() {
        return "Cinema{" +
                "name='" + name + '\'' +
                ", cinemaRoomsNumber=" + cinemaRoomsNumber +
                ", telefono='" + telefono + '\'' +
                ", region=" + region +
                ", indirizzo='" + indirizzo + '\'' +
                '}';
    }

    public Cinema(String name, int cinemaRoomsNumber, String telefono, String indirizzo, String region) {
        this.name = name;
        this.cinemaRoomsNumber = cinemaRoomsNumber;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.region = region;
    }

    public Cinema(Parcel in) {
        name = in.readString();
        cinemaRoomsNumber = in.readInt();
        indirizzo=in.readString();
        telefono=in.readString();
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

    public String getTelefono() {
        return telefono;
    }

    public int getCinemaRoomsNumber() {
        return cinemaRoomsNumber;
    }

    public String getRegion() {


        return region;

    }

    public String getIndirizzo() {
        return indirizzo;
    }
    public static final Parcelable.Creator<Cinema> CREATOR=new Parcelable.Creator<Cinema>()
    {
        public Cinema createFromParcel(Parcel in)
        {
            return new Cinema(in);
        }

        public Cinema[] newArray(int size)
        {
            return new Cinema[size];
        }
    };
    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeString(name);
        out.writeInt(cinemaRoomsNumber);
        out.writeString(telefono);
        out.writeString(indirizzo);
    }
    @Override
    public int describeContents() {
        return 0;
    }

}
