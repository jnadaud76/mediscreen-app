package com.mediscreen.report.controller;

import com.mediscreen.report.model.Report;
import com.mediscreen.report.service.IReportService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("API for report.")
@CrossOrigin("*")
@RequestMapping(value = "api")
@RestController
public class ReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);
    private final IReportService reportService;

    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }


    @ApiOperation(value = "Retrieve one report by patient id.")
    @GetMapping("/report/id")
    public ResponseEntity<Report> getReportByPatientId(@RequestParam Integer patientId) {
        Report report = reportService.generateReport(patientId);
        if (report != null) {
            LOGGER.info("Report successfully found - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(report);
        } else {
            LOGGER.error("Report not found, Patient don't exist - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "Send one report by patient id from URLENCODED_VALUE.")
    @PostMapping(value = "/assess/id", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCurlReportById(Integer patId) {
        Report report = reportService.generateReport(patId);
        if (report != null) {
            String reportAsString = "Patient : " + report.getPatient().getFirstName() + " " + report.getPatient().getLastName() +
                    " (age " + report.getAge() + ") diabetes assessment is: " +
                    report.getRiskLevel();
            LOGGER.info("Report successfully send - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.CREATED).body(reportAsString);
        } else {
            LOGGER.error("Report can't be send, Patient don't exist - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @ApiOperation(value = "Send one report by patient's given and familyName from URLENCODED_VALUE.")
    @PostMapping(value = "/assess/familyName", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCurlReportByFamilyName(String given, String familyName) {
        Report report = reportService.generateReportByFamilyNameAndGiven(given, familyName);
        if (report != null) {
            String reportAsString = "Patient : " + report.getPatient().getFirstName() + " " + report.getPatient().getLastName() +
                    " (age " + report.getAge() + ") diabetes assessment is: " +
                    report.getRiskLevel();
            LOGGER.info("Report successfully send - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.CREATED).body(reportAsString);
        } else {
            LOGGER.error("Report can't be send, Patient don't exist - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
