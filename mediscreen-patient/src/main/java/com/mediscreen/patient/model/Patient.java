package com.mediscreen.patient.model;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Length(max = 100)
    @NotBlank(message = "Firstname is mandatory")
    @Column(name = "FIRSTNAME", nullable = false, length = 100)
    private String firstName;

    @Length(max = 100)
    @NotBlank(message = "Lastname is mandatory")
    @Column(name = "LASTNAME", nullable = false, length = 100)
    private String lastName;

    @Past
    @NotNull(message = "Date of birth is mandatory")
    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is mandatory")
    @Column(name = "GENDER", length = 1)
    private Character gender;

    @NotBlank(message = "Address is mandatory")
    @Column(name = "ADDRESS", nullable = false, length = 300)
    private String address;

    @NotBlank(message = "Phone number is mandatory")
    @Column(name = "PHONE_NUMBER", nullable = false, length = 20)
    private String phoneNumber;

    public Patient() {
    }

    public Patient(int id, String firstName, String lastName, LocalDate date, Character gender, String address, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = date;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate date) {
        this.dateOfBirth = date;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
