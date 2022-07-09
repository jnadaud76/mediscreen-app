package com.mediscreen.patient.util;

import com.mediscreen.patient.dto.PatientFromStringDto;
import com.mediscreen.patient.model.Patient;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Conversion implements IConversion {

    public Patient patientFromStringDtoToPatient (PatientFromStringDto patientFromStringDto) {
        Patient patient = new Patient();
        patient.setFirstName(patientFromStringDto.getGiven());
        patient.setLastName(patientFromStringDto.getFamily());
        patient.setDateOfBirth(LocalDate.parse(patientFromStringDto.getDob()));
        patient.setGender(patientFromStringDto.getSex().charAt(0));
        patient.setPhoneNumber(patientFromStringDto.getPhone());
        patient.setAddress(patientFromStringDto.getAddress());
        return patient;
    }

}
