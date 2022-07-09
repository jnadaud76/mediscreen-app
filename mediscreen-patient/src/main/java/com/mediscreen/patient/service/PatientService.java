package com.mediscreen.patient.service;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Transactional
@Service
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Integer patientId) {
        return patientRepository.findById(patientId);
    }

    public Optional<Patient> getPatientByFirstNameAndLastName(String firstName, String lastName) {
        return patientRepository.findPatientByFirstNameAndLastName(firstName, lastName);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient (Patient patientUpdate) {
        if(patientRepository.existsById(patientUpdate.getId())) {
            Patient patient = getPatientById(patientUpdate.getId()).get();
            patient.setFirstName(patientUpdate.getFirstName());
            patient.setLastName(patientUpdate.getLastName());
            patient.setDateOfBirth(patientUpdate.getDateOfBirth());
            patient.setGender(patientUpdate.getGender());
            patient.setAddress(patientUpdate.getAddress());
            patient.setPhoneNumber(patientUpdate.getPhoneNumber());
            patientRepository.save(patient);
            return patient;
        } else {
            return null;
        }
    }


}
