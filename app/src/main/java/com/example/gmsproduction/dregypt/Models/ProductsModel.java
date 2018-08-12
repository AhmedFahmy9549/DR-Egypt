package com.example.gmsproduction.dregypt.Models;

import java.io.Serializable;

/**
 * Created by Hima on 4/30/2018.
 */

public class ProductsModel implements Serializable{

    String idz, titlez,category, description,price, status, image ,address,created_at,created_by,phone_1,phone_2;

    public ProductsModel(String idz, String titlez, String category, String description, String price, String status, String image, String address, String created_at, String created_by, String phone_1, String phone_2) {
        this.idz = idz;
        this.titlez = titlez;
        this.category = category;
        this.description = description;
        this.price = price;
        this.status = status;
        this.image = image;
        this.address = address;
        this.created_at = created_at;
        this.created_by = created_by;
        this.phone_1 = phone_1;
        this.phone_2 = phone_2;
    }

    public String getIdz() {
        return idz;
    }

    public String getTitlez() {
        return titlez;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
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

    public String getCreated_by() {
        return created_by;
    }

    public String getPhone_1() {
        return phone_1;
    }

    public String getPhone_2() {
        return phone_2;
    }
}
