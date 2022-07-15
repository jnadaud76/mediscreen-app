package com.mediscreen.report.proxy;

import com.mediscreen.report.model.PractitionerNote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "microservice-history", url = "${MICROSERVICE_HISTORY}")
public interface HistoryServiceProxy {

    @GetMapping("/patHistory/id")
    List<PractitionerNote> getAllPractitionerNoteByPatientId(@RequestParam Integer patientId);
}
