package com.mediscreen.report.unit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mediscreen.report.controller.ReportController;
import com.mediscreen.report.model.Patient;
import com.mediscreen.report.model.Report;
import com.mediscreen.report.service.IReportService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@WebMvcTest(controllers = ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IReportService reportService;

    @Test
    void testGetReportNone() throws Exception {
        Report report = new Report();
        report.setRiskLevel("NONE");
        when(reportService.generateReport(1)).thenReturn(report);
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is ("NONE")));
    }

    @Test
    void testGetReportBorderline() throws Exception {
        Report report = new Report();
        report.setRiskLevel("BORDERLINE");
        when(reportService.generateReport(2)).thenReturn(report);
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is ("BORDERLINE")));
    }

    @Test
    void testGetReportInDanger() throws Exception {
        Report report = new Report();
        report.setRiskLevel("IN_DANGER");
        when(reportService.generateReport(3)).thenReturn(report);
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is ("IN_DANGER")));
    }

    @Test
    void testGetReportEarlyOnset() throws Exception {
        Report report = new Report();
        report.setRiskLevel("EARLY_ONSET");
        when(reportService.generateReport(4)).thenReturn(report);
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is ("EARLY_ONSET")));
    }

    @Test
    void testGetReportWithBadPatientId() throws Exception {
        when(reportService.generateReport(4)).thenReturn(null);
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "125"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetCurlReportById() throws Exception {
        Report report = new Report();
        Patient patient = new Patient(1, "test28", "test28", LocalDate.now().minusYears(40), 'M',
                "12 rue du test", "555-555-555");
        report.setPatient(patient);
        report.setAge(40);
        report.setRiskLevel("NONE");
        when(reportService.generateReport(1)).thenReturn(report);
        mockMvc.perform(post("/api/assess/id")
                        .queryParam("patId", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", containsString ("NONE")));
    }

    @Test
    void testGetCurlReportByIdWithBadId() throws Exception {
        when(reportService.generateReport(125)).thenReturn(null);
        mockMvc.perform(post("/api/assess/id")
                        .queryParam("patId", "125")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCurlReportByFamilyName() throws Exception {
        Report report = new Report();
        Patient patient = new Patient(1, "test", "testnone", LocalDate.now().minusYears(40), 'M',
                "12 rue du test", "555-555-555");
        report.setPatient(patient);
        report.setAge(40);
        report.setRiskLevel("NONE");
        when(reportService.generateReportByFamilyNameAndGiven("test", "testnone")).thenReturn(report);
        mockMvc.perform(post("/api/assess/familyName")
                        .queryParam("given", "test")
                        .queryParam("familyName", "testnone")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", containsString ("NONE")));
    }

    @Test
    void testGetCurlReportByIdFamilyNameWithBadArguments() throws Exception {
        when(reportService.generateReportByFamilyNameAndGiven("toto", "tata")).thenReturn(null);
        mockMvc.perform(post("/api/assess/familyName")
                        .queryParam("given", "toto")
                        .queryParam("familyName", "tata")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isBadRequest());
    }
}
