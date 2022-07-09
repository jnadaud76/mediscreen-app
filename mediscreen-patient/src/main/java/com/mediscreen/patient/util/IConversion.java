package com.mediscreen.patient.util;

import com.mediscreen.patient.dto.PatientFromStringDto;
import com.mediscreen.patient.model.Patient;

public interface IConversion {

    Patient patientFromStringDtoToPatient (PatientFromStringDto patientFromStringDto);

}
