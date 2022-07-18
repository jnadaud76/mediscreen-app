package com.mediscreen.report.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mediscreen.report.controller.ReportController;
import com.mediscreen.report.model.Report;
import com.mediscreen.report.service.IReportService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IReportService reportService;

    @Test
    void TestGetReportNone() throws Exception {
        Report report = new Report();
        report.setRiskLevel("NONE");
        when(reportService.generateReport(1)).thenReturn(report);
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is ("NONE")));
    }

    @Test
    void TestGetReportBorderline() throws Exception {
        Report report = new Report();
        report.setRiskLevel("BORDERLINE");
        when(reportService.generateReport(2)).thenReturn(report);
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is ("BORDERLINE")));
    }

    @Test
    void TestGetReportInDanger() throws Exception {
        Report report = new Report();
        report.setRiskLevel("IN_DANGER");
        when(reportService.generateReport(3)).thenReturn(report);
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is ("IN_DANGER")));
    }

    @Test
    void TestGetReportEarlyOnset() throws Exception {
        Report report = new Report();
        report.setRiskLevel("EARLY_ONSET");
        when(reportService.generateReport(4)).thenReturn(report);
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is ("EARLY_ONSET")));
    }

    @Test
    void TestGetReportWithBadPatientId() throws Exception {
        when(reportService.generateReport(4)).thenReturn(null);
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "125"))
                .andExpect(status().isNotFound());
    }
}
