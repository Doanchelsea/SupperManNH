package com.fpoly.suppermannh.model;

public class Pay {
    int id;
    String namemonan;
    String images;
    int price;
    int banan;
    int discounts;
    int soluong;
    int iduer;

    public Pay(int id, String namemonan, String images, int price, int banan, int discounts, int soluong, int iduer) {
        this.id = id;
        this.namemonan = namemonan;
        this.images = images;
        this.price = price;
        this.banan = banan;
        this.discounts = discounts;
        this.soluong = soluong;
        this.iduer = iduer;
    }

    public int getIduer() {
        return iduer;
    }

    public void setIduer(int iduer) {
        this.iduer = iduer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamemonan() {
        return namemonan;
    }

    public void setNamemonan(String namemonan) {
        this.namemonan = namemonan;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBanan() {
        return banan;
    }

    public void setBanan(int banan) {
        this.banan = banan;
    }

    public int getDiscounts() {
        return discounts;
    }

    public void setDiscounts(int discounts) {
        this.discounts = discounts;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
