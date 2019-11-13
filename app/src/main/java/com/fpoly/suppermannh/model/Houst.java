package com.fpoly.suppermannh.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Houst implements Parcelable {
    int id;
    String phones;
    String names;
    double lat;
    double lng;
    String images;
    String address;

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdnhahang() {
        return idnhahang;
    }

    public void setIdnhahang(int idnhahang) {
        this.idnhahang = idnhahang;
    }

    int iduser;
    int idnhahang;

    public Houst(int id, String phones, String names, double lat, double lng, String images, String address) {
        this.id = id;
        this.phones = phones;
        this.names = names;
        this.lat = lat;
        this.lng = lng;
        this.images = images;
        this.address = address;
    }
    public Houst(int iduser, int idnhahang, String names, double lat, double lng, String images, String address) {
        this.iduser = iduser;
        this.idnhahang = idnhahang;
        this.names = names;
        this.lat = lat;
        this.lng = lng;
        this.images = images;
        this.address = address;
    }


    protected Houst(Parcel in) {
        id = in.readInt();
        phones = in.readString();
        names = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        images = in.readString();
        address = in.readString();
        iduser = in.readInt();
        idnhahang = in.readInt();
    }

    public static final Creator<Houst> CREATOR = new Creator<Houst>() {
        @Override
        public Houst createFromParcel(Parcel in) {
            return new Houst(in);
        }

        @Override
        public Houst[] newArray(int size) {
            return new Houst[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(phones);
        parcel.writeString(names);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeString(images);
        parcel.writeString(address);
        parcel.writeInt(iduser);
        parcel.writeInt(idnhahang);
    }
}
