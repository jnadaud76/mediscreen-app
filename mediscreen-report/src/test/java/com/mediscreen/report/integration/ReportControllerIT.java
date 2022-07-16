package com.mediscreen.report.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.report.service.IReportService;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IReportService reportService;

    @Test
    void TestGetReportNone() throws Exception {
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is ("NONE")));
    }

    @Test
    void TestGetReportBorderline() throws Exception {
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is ("BORDERLINE")));
    }

    @Test
    void TestGetReportInDanger() throws Exception {
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is ("IN_DANGER")));
    }

    @Test
    void TestGetReportEarlyOnset() throws Exception {
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is ("EARLY_ONSET")));
    }

    @Test
    void TestGetReportWithBadPatientId() throws Exception {
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "125"))
                .andExpect(status().isNotFound());
    }
}
