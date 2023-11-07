package com.demo.loginSignup.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "userName"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @NotBlank(message = "Name cannot be blank or null")
    @Size(min=2, max=100, message = "Length of name should be between 2 and 100")
    private String name;
    @NotBlank(message = "UserName cannot be blank or null")
    @Size(max=20, message = "Length of username should be between 1 and 20")
    private String userName;

    @NotBlank(message = "Email cannot be blank or null")
    @Size(max=50, message = "Length of email should not exceed 50")
    @Email(message = "Please provide a valid email address")
    @Pattern(regexp=".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password cannot be blank or null")
    @Size(max=120, message = "Length of email should not exceed 120")
    private String password;

    public User(){

    }
    public User(String name, String userName, String email, String password) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
