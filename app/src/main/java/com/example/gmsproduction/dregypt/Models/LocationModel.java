package com.example.gmsproduction.dregypt.Models;

/**
 * Created by Ahmed Fahmy on 5/6/2018.
 */

public class LocationModel {
    String locName;
    int locId;

    public LocationModel(String locName, int locId) {
        this.locName = locName;
        this.locId = locId;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }
}
