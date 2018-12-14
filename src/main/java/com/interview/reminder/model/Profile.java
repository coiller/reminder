package com.interview.reminder.model;

import lombok.Data;

import java.util.Date;

@Data
public class Profile {
    private String address;
    private Date dob;
    private String email;
    private String name;
    private String phone;
    private String title;

    public Profile() {
    }
}
