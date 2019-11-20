package com.fpoly.suppermannh.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Manage implements Parcelable {
    public int id;
    public String date;
    public  int idname;
    public  String image;
    public  String name;
    public int price;
    public  String status;
    public  String time;
    public  int discounts;
    public  int tables;
    public  String soluong;
    public  int idnhahang;
    public String songuoi;
    public int iduser;


    public Manage(int id, String date, int idname, String image, String name, int price, String status, String time, int discounts, int tables, String soluong, int idnhahang, String songuoi, int iduser) {
        this.id = id;
        this.date = date;
        this.idname = idname;
        this.image = image;
        this.name = name;
        this.price = price;
        this.status = status;
        this.time = time;
        this.discounts = discounts;
        this.tables = tables;
        this.soluong = soluong;
        this.idnhahang = idnhahang;
        this.songuoi = songuoi;
        this.iduser = iduser;
    }

    protected Manage(Parcel in) {
        id = in.readInt();
        date = in.readString();
        idname = in.readInt();
        image = in.readString();
        name = in.readString();
        price = in.readInt();
        status = in.readString();
        time = in.readString();
        discounts = in.readInt();
        tables = in.readInt();
        soluong = in.readString();
        idnhahang = in.readInt();
        songuoi = in.readString();
        iduser = in.readInt();
    }

    public static final Creator<Manage> CREATOR = new Creator<Manage>() {
        @Override
        public Manage createFromParcel(Parcel in) {
            return new Manage(in);
        }

        @Override
        public Manage[] newArray(int size) {
            return new Manage[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdname() {
        return idname;
    }

    public void setIdname(int idname) {
        this.idname = idname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDiscounts() {
        return discounts;
    }

    public void setDiscounts(int discounts) {
        this.discounts = discounts;
    }

    public int getTables() {
        return tables;
    }

    public void setTables(int tables) {
        this.tables = tables;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public int getIdnhahang() {
        return idnhahang;
    }

    public void setIdnhahang(int idnhahang) {
        this.idnhahang = idnhahang;
    }

    public String getSonguoi() {
        return songuoi;
    }

    public void setSonguoi(String songuoi) {
        this.songuoi = songuoi;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(date);
        parcel.writeInt(idname);
        parcel.writeString(image);
        parcel.writeString(name);
        parcel.writeInt(price);
        parcel.writeString(status);
        parcel.writeString(time);
        parcel.writeInt(discounts);
        parcel.writeInt(tables);
        parcel.writeString(soluong);
        parcel.writeInt(idnhahang);
        parcel.writeString(songuoi);
        parcel.writeInt(iduser);
    }
}
