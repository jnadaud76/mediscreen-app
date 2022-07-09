package com.mediscreen.patient.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PatientFromStringDto {

    private int id;

    @Length(max = 100)
    @NotEmpty(message = "Firstname is mandatory")
    private String family;

    @Length(max = 100)
    @NotEmpty(message = "Lastname is mandatory")
    private String given;

    @NotNull(message = "Date of birth is mandatory")
    private String dob;

    @NotNull(message = "Gender is mandatory")
    private String sex;

    @Length(max = 300)
    @NotEmpty(message = "Address is mandatory")
    private String address;

    @Length(max = 20)
    @NotEmpty(message = "Phone number is mandatory")
    private String phone;

    public PatientFromStringDto() {
    }

    public PatientFromStringDto(int id, String family, String given, String dob, String sex, String address, String phone) {
        this.id = id;
        this.family = family;
        this.given = given;
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
