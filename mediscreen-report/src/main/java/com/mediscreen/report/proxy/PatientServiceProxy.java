package com.mediscreen.report.proxy;

import com.mediscreen.report.model.Patient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "microservice-patient", url = "${MICROSERVICE_PATIENT}")
public interface PatientServiceProxy {

    @GetMapping("/patient/id")
    Patient getPatientById(@RequestParam Integer patientId);
}
