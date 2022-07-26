package com.mediscreen.report.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mediscreen.report.model.Report;
import com.mediscreen.report.proxy.HistoryServiceProxy;
import com.mediscreen.report.proxy.PatientServiceProxy;
import com.mediscreen.report.service.IReportService;
import com.mediscreen.report.service.ReportService;
import com.mediscreen.report.util.ICalculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import feign.FeignException;
import io.swagger.v3.oas.models.security.SecurityScheme;


@SpringBootTest
class ReportServiceIT {

    @Autowired
    private HistoryServiceProxy historyServiceProxy;
    @Autowired
    private PatientServiceProxy patientServiceProxy;
    @Autowired
    private ICalculator calculator;

    @ParameterizedTest
    @CsvSource({"1,NONE", "2,BORDERLINE", "3,IN_DANGER", "4,EARLY_ONSET"})
    void generateReport(String input, String expected) throws Exception {
        IReportService reportService = new ReportService(historyServiceProxy,
                patientServiceProxy, calculator);
        Report report = reportService.generateReport(Integer.valueOf(input));
        assertEquals(expected, report.getRiskLevel());

    }

    @Test
    void generateReportWithBadPatientId() throws Exception {
        IReportService reportService = new ReportService(historyServiceProxy,
                patientServiceProxy, calculator);
        Report report = reportService.generateReport(75);
        assertNull(report);
    }

    @ParameterizedTest
    @CsvSource({"test, testnone ,NONE", "test, testborderline,BORDERLINE", "test, testindanger,IN_DANGER", "test, testearlyonset,EARLY_ONSET"})
    void generateReportByFamilyNameAndGiven(String given, String familyName, String expected) throws Exception {
        IReportService reportService = new ReportService(historyServiceProxy,
                patientServiceProxy, calculator);
        Report report = reportService.generateReportByFamilyNameAndGiven(given, familyName);
        assertEquals(expected, report.getRiskLevel());

    }

    @Test
    void generateReportByFamilyNameAndGivenWithBadPatientGivenAndFamilyName() throws Exception {
        IReportService reportService = new ReportService(historyServiceProxy,
                patientServiceProxy, calculator);
        Report report = reportService.generateReportByFamilyNameAndGiven("test","testghyyyy");
        assertNull(report);
    }

}
