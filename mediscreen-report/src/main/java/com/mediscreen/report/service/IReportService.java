package com.mediscreen.report.service;

import com.mediscreen.report.model.Report;

import org.springframework.stereotype.Service;

@Service
public interface IReportService {
    Report generateReport (Integer patientId);

    Report generateReportByFamilyNameAndGiven (String given, String familyName);

}
