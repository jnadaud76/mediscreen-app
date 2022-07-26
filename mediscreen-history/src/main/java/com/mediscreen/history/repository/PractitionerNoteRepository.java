package com.mediscreen.history.repository;

import com.mediscreen.history.model.PractitionerNote;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PractitionerNoteRepository extends MongoRepository<PractitionerNote, String> {

    List<PractitionerNote> findPractitionerNotesByPatientIdOrderByCreationDateDesc(Integer patientId);
}
