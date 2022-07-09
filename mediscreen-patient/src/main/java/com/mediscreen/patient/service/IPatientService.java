package com.mediscreen.patient.service;

import com.mediscreen.patient.model.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientService {

    List<Patient> getAllPatient();

    Optional<Patient> getPatientById(Integer patientId);

    Optional<Patient> getPatientByFirstNameAndLastName(String firstName, String lastName);

    Patient savePatient(Patient patient);

    Patient updatePatient (Patient patientUpdate);
}
