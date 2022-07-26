package com.mediscreen.report.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @CsvSource({"1,NONE", "2,BORDERLINE", "3,IN_DANGER", "4,EARLY_ONSET"})
    void testGetReport(String input, String expected) throws Exception {
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is(expected)));
    }

    @Test
    void testGetReportWithPatientWithoutNotes() throws Exception {
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel", is("NONE")));;
    }

    @Test
    void testGetReportWithBadPatientId() throws Exception {
        mockMvc.perform(get("/api/report/id")
                        .queryParam("patientId", "125"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetCurlReportById() throws Exception {
        mockMvc.perform(post("/api/assess/id")
                        .queryParam("patId", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", containsString("NONE")));
    }

    @Test
    void testGetCurlReportByIdWithPatientWithoutNote() throws Exception {
        mockMvc.perform(post("/api/assess/id")
                        .queryParam("patId", "5")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", containsString("NONE")));
    }

    @Test
    void testGetCurlReportByIdWithBadId() throws Exception {
        mockMvc.perform(post("/api/assess/id")
                        .queryParam("patId", "125")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCurlReportByFamilyName() throws Exception {
        mockMvc.perform(post("/api/assess/familyName")
                        .queryParam("given", "test")
                        .queryParam("familyName", "testnone")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", containsString("NONE")));
    }

    @Test
    void testGetCurlReportByFamilyNameWithPatientWithoutNote() throws Exception {
        mockMvc.perform(post("/api/assess/familyName")
                        .queryParam("given", "test")
                        .queryParam("familyName", "testwithoutnote")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", containsString("NONE")));
    }

    @Test
    void testGetCurlReportByIdFamilyNameWithBadArguments() throws Exception {
        mockMvc.perform(post("/api/assess/familyName")
                        .queryParam("given", "toto")
                        .queryParam("familyName", "tata")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isBadRequest());
    }
}
