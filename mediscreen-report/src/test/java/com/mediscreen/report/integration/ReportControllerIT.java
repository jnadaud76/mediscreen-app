package com.mediscreen.report.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerIT {

    @Autowired
    private MockMvc mockMvc;

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

    @Test
    void TestGetCurlReportById() throws Exception {
        mockMvc.perform(post("/api/assess/id")
                        .queryParam("patId", "1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", containsString ("NONE")));
    }

    @Test
    void TestGetCurlReportByIdWithBadId() throws Exception {
        mockMvc.perform(post("/api/assess/id")
                        .queryParam("patId", "125")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void TestGetCurlReportByFamilyName() throws Exception {
        mockMvc.perform(post("/api/assess/familyName")
                        .queryParam("given", "test")
                        .queryParam("familyName", "testnone")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", containsString ("NONE")));
    }

    @Test
    void TestGetCurlReportByIdFamilyNameWithBadArguments() throws Exception {
        mockMvc.perform(post("/api/assess/familyName")
                        .queryParam("given", "toto")
                        .queryParam("familyName", "tata")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isBadRequest());
    }
}
