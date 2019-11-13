package com.fpoly.suppermannh.model;

import android.os.Parcel;
import android.os.Parcelable;

public class History implements Parcelable {
    int id;
    int iduser;
    int idnhahang;
    String price;
    String date;
    String namenh;
    String images;
    int tables;
    String idtrangthai;

    public History(int id, int iduser, int idnhahang, String price, String date, String namenh, String images, int tables, String idtrangthai) {
        this.id = id;
        this.iduser = iduser;
        this.idnhahang = idnhahang;
        this.price = price;
        this.date = date;
        this.namenh = namenh;
        this.images = images;
        this.tables = tables;
        this.idtrangthai = idtrangthai;
    }

    protected History(Parcel in) {
        id = in.readInt();
        iduser = in.readInt();
        idnhahang = in.readInt();
        price = in.readString();
        date = in.readString();
        namenh = in.readString();
        images = in.readString();
        tables = in.readInt();
        idtrangthai = in.readString();
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    public String getIdtrangthai() {
        return idtrangthai;
    }

    public void setIdtrangthai(String idtrangthai) {
        this.idtrangthai = idtrangthai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNamenh() {
        return namenh;
    }

    public void setNamenh(String namenh) {
        this.namenh = namenh;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getTables() {
        return tables;
    }

    public void setTables(int tables) {
        this.tables = tables;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(iduser);
        parcel.writeInt(idnhahang);
        parcel.writeString(price);
        parcel.writeString(date);
        parcel.writeString(namenh);
        parcel.writeString(images);
        parcel.writeInt(tables);
        parcel.writeString(idtrangthai);
    }
}
