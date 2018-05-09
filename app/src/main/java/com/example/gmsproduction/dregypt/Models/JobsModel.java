package com.example.gmsproduction.dregypt.Models;

/**
 * Created by Hima on 5/9/2018.
 */

public class JobsModel {
    String id,userId, title, description,salary, image, status, address, created_at, phone_1, phone_2,category,experience,education_level,employment_type;

    public JobsModel(String id, String userId, String title, String description, String salary, String image, String status, String address, String created_at, String phone_1, String phone_2, String category, String experience, String education_level, String employment_type) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.image = image;
        this.status = status;
        this.address = address;
        this.created_at = created_at;
        this.phone_1 = phone_1;
        this.phone_2 = phone_2;
        this.category = category;
        this.experience = experience;
        this.education_level = education_level;
        this.employment_type = employment_type;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSalary() {
        return salary;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
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

    public String getCategory() {
        return category;
    }

    public String getExperience() {
        return experience;
    }

    public String getEducation_level() {
        return education_level;
    }

    public String getEmployment_type() {
        return employment_type;
    }
}
