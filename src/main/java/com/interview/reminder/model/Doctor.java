package com.interview.reminder.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Doctor extends Account {

    /**
     *
     */
    private static final long serialVersionUID = -5565672523649886938L;
    private String name;
    private String email;
    private String phone;
    private String title;

    public Doctor() {
    }

    public Doctor(String username, String password, String name) {
        super(username, password);
        this.setName(name);
        this.setType(Type.DOCTOR);
    }

    public void setProfile(Profile profile) {
        this.email = profile.getEmail();
        this.name = profile.getName();
        this.title = profile.getTitle();
        this.phone = profile.getPhone();
    }

    public Doctor(String name, String email, String phone, String title) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.title = title;
    }

}
