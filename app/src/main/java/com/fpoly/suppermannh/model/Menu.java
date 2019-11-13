package com.fpoly.suppermannh.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Menu implements Parcelable {
     int id;
     int idmonan;
     String dates;
     String descriptions;
     String images;
     String names;
     int prices;
     int idnhahang;
     String namenh;

    public Menu(int id, int idmonan, String dates, String descriptions, String images, String names, int prices, int idnhahang, String namenh) {
        this.id = id;
        this.idmonan = idmonan;
        this.dates = dates;
        this.descriptions = descriptions;
        this.images = images;
        this.names = names;
        this.prices = prices;
        this.idnhahang = idnhahang;
        this.namenh = namenh;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdmonan() {
        return idmonan;
    }

    public void setIdmonan(int idmonan) {
        this.idmonan = idmonan;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public int getPrices() {
        return prices;
    }

    public void setPrices(int prices) {
        this.prices = prices;
    }

    public int getIdnhahang() {
        return idnhahang;
    }

    public void setIdnhahang(int idnhahang) {
        this.idnhahang = idnhahang;
    }

    public String getNamenh() {
        return namenh;
    }

    public void setNamenh(String namenh) {
        this.namenh = namenh;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(idmonan);
        parcel.writeString(dates);
        parcel.writeString(descriptions);
        parcel.writeString(images);
        parcel.writeString(names);
        parcel.writeInt(prices);
        parcel.writeInt(idnhahang);
        parcel.writeString(namenh);
    }

    protected Menu(Parcel in) {
        id = in.readInt();
        idmonan = in.readInt();
        dates = in.readString();
        descriptions = in.readString();
        images = in.readString();
        names = in.readString();
        prices = in.readInt();
        idnhahang = in.readInt();
        namenh = in.readString();
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };
}
