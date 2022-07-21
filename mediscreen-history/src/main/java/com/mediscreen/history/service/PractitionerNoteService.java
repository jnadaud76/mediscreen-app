package com.mediscreen.history.service;

import com.mediscreen.history.model.PractitionerNote;
import com.mediscreen.history.repository.PractitionerNoteRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PractitionerNoteService implements IPractitionerNoteService {

   private final PractitionerNoteRepository practitionerNoteRepository;

    public PractitionerNoteService(PractitionerNoteRepository practitionerNoteRepository) {
        this.practitionerNoteRepository = practitionerNoteRepository;
    }

    public List<PractitionerNote> getAllPractitionerNote() {
        return practitionerNoteRepository.findAll();
    }

    public List<PractitionerNote> getAllPractitionerNoteByPatientId(Integer patientId) {
        return practitionerNoteRepository.findPractitionerNotesByPatientIdOrderByCreationDateDesc(patientId);
    }

    public PractitionerNote updatePractitionerNote(PractitionerNote noteUpdate) {
        if (practitionerNoteRepository.existsById(noteUpdate.getId())) {
            PractitionerNote note = practitionerNoteRepository.findById(noteUpdate.getId()).get();
            note.setNote(noteUpdate.getNote());
            practitionerNoteRepository.save(note);
            return note;
        } else {
            return null;
        }
    }

     public PractitionerNote savePractitionerNote(PractitionerNote note)  {
            note.setCreationDate(LocalDateTime.now());
            return practitionerNoteRepository.save(note);
        }
    }

