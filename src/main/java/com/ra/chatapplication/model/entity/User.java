package com.ra.chatapplication.model.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User entity
 */
@Entity
@Data
@ToString
@Table(name = "user")
public class User implements Serializable, UserDetails {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    /**
     * First name
     */
    @Column(name = "first_name")
    @Size(min = 2)
    @NotEmpty(message = "firstname required")
    private String firstName;

    /**
     * Last name
     */
    @Column(name = "last_name")
    @Size(min = 2)
    @NotEmpty(message = "lastname required")
    private String lastName;

    /**
     * Email
     */
    @Column
    @Email
    private String email;

    /**
     * Password
     */
    @Column
    @Size(min = 8)
    private String password;

    /**
     * Role
     * admin - true, user - false
     */
    @Column
    private Boolean admin = false;

    /**
     * Account status
     * activated - true, deactivated - false
     */
    @Column
    private Boolean active = true;

    /**
     * Login status
     * first time to log in - true, not the first time (user has reset the password) - false
     */
    @Column
    private Boolean firstLogin = true;

    /**
     * The number of failed login attempts
     */
    @Column
    private Integer failureTimes = 0;

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

    public String getFullName(){
        return firstName + " " + lastName;
    }

//    public User(String email, String password) {
//        this.email = email;
//        this.password = password;
//    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (this.getAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return active;
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

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public Integer getFailureTimes() {
        return failureTimes;
    }

    public void setFailureTimes(Integer failureTimes) {
        this.failureTimes = failureTimes;
    }

    public boolean isActive() {
        return active;
    }
}

