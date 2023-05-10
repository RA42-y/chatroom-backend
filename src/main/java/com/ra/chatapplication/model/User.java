package com.ra.chatapplication.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstname")
    @Size(min = 2)
    @NotEmpty(message = "firstname obligatoire")
    private String firstName;

    @Column(name = "lastname")
    @Size(min = 2)
    @NotEmpty(message = "firstname obligatoire")
    private String lastName;

    @Column
    @Email
    private String email;

    @Column
    private String password;

    @Column
    private Boolean admin = false;

    @Column
    private Boolean active = true;

    public User(){}

    public User(long id) {
        this.id = id;
    }

    public User(String email) {
        this.email = email;
    }

    public User(String firstName, String lastName, String email, String password, Boolean admin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}

