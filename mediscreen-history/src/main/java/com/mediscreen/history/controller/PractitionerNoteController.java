package com.mediscreen.history.controller;

import com.mediscreen.history.model.PractitionerNote;
import com.mediscreen.history.service.IPractitionerNoteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("API for practitioner's notes CRUD operations.")
@CrossOrigin("*")
@RequestMapping(value = "api/history")
@RestController
public class PractitionerNoteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PractitionerNoteController.class);

    private final IPractitionerNoteService practitionerNoteService;

    public PractitionerNoteController(IPractitionerNoteService practitionerNoteService) {
        this.practitionerNoteService = practitionerNoteService;
    }

    @ApiOperation(value = "Retrieve all practitioner's notes for all patient.")
    @GetMapping("/patHistory")
    public ResponseEntity<List<PractitionerNote>> getAllPractitionerNotes(){
        List<PractitionerNote> notes = practitionerNoteService.getAllPractitionerNote();
        if (!notes.isEmpty()) {
            LOGGER.info("Practitioner's notes successfully found - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(notes);
        } else {
            LOGGER.error("Practitioner's notes not found - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "Retrieve all practitioner's notes for one patient by id.")
    @GetMapping("/patHistory/id")
    public ResponseEntity<List<PractitionerNote>> getAllPractitionerNoteByPatientId(@RequestParam Integer patientId){
        List<PractitionerNote> notes = practitionerNoteService.getAllPractitionerNoteByPatientId(patientId);
        if (!notes.isEmpty()) {
            LOGGER.info("Practitioner's notes successfully found - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(notes);
        } else {
            LOGGER.error("Practitioner's notes not found - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
