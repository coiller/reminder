package com.interview.reminder.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author mike
 * @Date 12/11/18
 * @Last Modified by: mike
 * @Last Modified time:12/11/18 10:54 PM
 */
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
