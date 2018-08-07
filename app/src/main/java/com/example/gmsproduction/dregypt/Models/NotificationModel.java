package com.example.gmsproduction.dregypt.Models;

/**
 * Created by Hima on 8/7/2018.
 */

public class NotificationModel {
    String title,body;

    public NotificationModel(String title, String body) {
        this.title = title;
        this.body = body;

    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
