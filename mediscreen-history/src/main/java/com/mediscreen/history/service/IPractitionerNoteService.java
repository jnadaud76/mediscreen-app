package com.mediscreen.history.service;

import com.mediscreen.history.model.PractitionerNote;

import java.util.List;

public interface IPractitionerNoteService {

    List<PractitionerNote> getAllPractitionerNote();

    List<PractitionerNote> getAllPractitionerNoteByPatientId(Integer patientId);

    PractitionerNote updatePractitionerNote(PractitionerNote noteUpdate);

    PractitionerNote getPractitionerNoteById (String noteId);

    PractitionerNote savePractitionerNote(PractitionerNote note);

}