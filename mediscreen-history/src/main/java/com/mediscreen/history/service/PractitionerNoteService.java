package com.mediscreen.history.service;

import com.mediscreen.history.model.PractitionerNote;
import com.mediscreen.history.repository.PractitionerNoteRepository;

import org.springframework.stereotype.Service;

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
}
