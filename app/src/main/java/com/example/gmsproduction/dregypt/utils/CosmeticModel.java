package com.example.gmsproduction.dregypt.utils;

/**
 * Created by Hima on 5/7/2018.
 */

public class CosmeticModel {
    String idz, titlez,description, image ,address,created_at,phone_1,phone_2;
    Double rating;
    int rating_counts;

    public CosmeticModel(String idz, String titlez, String description, String image, String address, String created_at, String phone_1, String phone_2, Double rating, int rating_counts) {
        this.idz = idz;
        this.titlez = titlez;
        this.description = description;
        this.image = image;
        this.address = address;
        this.created_at = created_at;
        this.phone_1 = phone_1;
        this.phone_2 = phone_2;
        this.rating = rating;
        this.rating_counts = rating_counts;
    }

    public int getRating_counts() {
        return rating_counts;
    }

    public String getIdz() {
        return idz;
    }

    public String getTitlez() {
        return titlez;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getPhone_1() {
        return phone_1;
    }

    public String getPhone_2() {
        return phone_2;
    }

    public Double getRating() {
        return rating;
    }
}
