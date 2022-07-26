package com.mediscreen.history.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

public class PractitionerNoteFromStringDto {

    private String id;
    private String patId;
    @NotBlank(message = "note is mandatory")
    private String e;
    private LocalDateTime creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
