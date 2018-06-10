package com.example.gmsproduction.dregypt.Models;

/**
 * Created by Hima on 6/6/2018.
 */

public class PhoneModel {
    String id,num;

    public PhoneModel(String id, String num) {
        this.id = id;
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public String getNum() {
        return num;
    }
}
