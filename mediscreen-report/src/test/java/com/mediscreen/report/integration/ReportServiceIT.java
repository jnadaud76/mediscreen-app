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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import feign.FeignException;


@SpringBootTest
class ReportServiceIT {

    @Autowired
    private HistoryServiceProxy historyServiceProxy;
    @Autowired
    private PatientServiceProxy patientServiceProxy;
    @Autowired
    private ICalculator calculator;

    @Test
    void generateReportNone() throws Exception {
        IReportService reportService = new ReportService(historyServiceProxy,
                patientServiceProxy, calculator);
        Report report = reportService.generateReport(1);
        assertEquals("NONE", report.getRiskLevel());
    }

    @Test
    void generateReportBorderline() throws Exception {
        IReportService reportService = new ReportService(historyServiceProxy,
                patientServiceProxy, calculator);
        Report report = reportService.generateReport(2);
        assertEquals("BORDERLINE", report.getRiskLevel());
    }

    @Test
    void generateReportInDanger() throws Exception {
        IReportService reportService = new ReportService(historyServiceProxy,
                patientServiceProxy, calculator);
        Report report = reportService.generateReport(3);
        assertEquals("IN_DANGER", report.getRiskLevel());
    }

    @Test
    void generateReportEarlyOnset() throws Exception {
        IReportService reportService = new ReportService(historyServiceProxy,
                patientServiceProxy, calculator);
        Report report = reportService.generateReport(4);
        assertEquals("EARLY_ONSET", report.getRiskLevel());
    }

    @Test
    void generateReportWithBadPatientId() throws Exception {
        IReportService reportService = new ReportService(historyServiceProxy,
                patientServiceProxy, calculator);
        Report report = reportService.generateReport(75);
        assertNull(report);
    }

}
