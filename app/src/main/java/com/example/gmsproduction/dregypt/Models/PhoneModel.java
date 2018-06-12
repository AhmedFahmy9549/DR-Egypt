package com.example.gmsproduction.dregypt.Models;

/**
 * Created by Hima on 6/6/2018.
 */

public class PhoneModel {
    String id01, num01, id02, num02;

    public PhoneModel(String id01, String num01, String id02, String num02) {
        this.id01 = id01;
        this.num01 = num01;
        this.id02 = id02;
        this.num02 = num02;
    }

    public String getId01() {
        return id01;
    }

    public String getNum01() {
        return num01;
    }

    public String getId02() {
        return id02;
    }

    public String getNum02() {
        return num02;
    }
}
