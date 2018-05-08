package com.example.gmsproduction.dregypt.Models;

/**
 * Created by Ahmed Fahmy on 5/8/2018.
 */

public class HospitalModel {
    private String name, address, note, website, email, img, phone1, phone2, createdAt;
    private int id, count, rating,favorites;

    public HospitalModel( int id,String name, String address, String note, String website, String email, String img, String phone1, String phone2, int count, int rating, int favorites, String createdAt) {
        this.name = name;
        this.address = address;
        this.note = note;
        this.website = website;
        this.email = email;
        this.img = img;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.createdAt = createdAt;
        this.id = id;
        this.count = count;
        this.rating = rating;
        this.favorites = favorites;

    }



    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getNote() {
        return note;
    }

    public String getWebsite() {
        return website;
    }

    public String getEmail() {
        return email;
    }

    public String getImg() {
        return img;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public int getFavorites() {
        return favorites;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public int getRating() {
        return rating;
    }


}