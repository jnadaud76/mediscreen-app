package com.mediscreen.patient.dto;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

public class PatientFullDto {

    private int id;

    @Length(max = 100)
    @NotEmpty(message = "Firstname is mandatory")
    private String firstName;

    @Length(max = 100)
    @NotEmpty(message = "Lastname is mandatory")
    private String lastName;

    @Past
    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is mandatory")
    private Character gender;

    @Length(max = 300)
    @NotEmpty(message = "Address is mandatory")
    private String address;

    @Length(max = 20)
    @NotEmpty(message = "Phone number is mandatory")
    private String phoneNumber;

    public PatientFullDto() {
    }

    public PatientFullDto(int id, String firstName, String lastName, LocalDate dateOfBirth, Character gender, String address, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
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

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
