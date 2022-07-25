package com.mediscreen.patient.integration;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediscreen.patient.dto.PatientFullDto;
import com.mediscreen.patient.service.IPatientService;
import com.mediscreen.patient.util.IConversion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@ActiveProfiles("test")
//@Sql(scripts = {"classpath:sql/schema-h2.sql", "classpath:sql/data-h2.sql"})
@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerIT {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IConversion conversion;

    @Test
    void TestGetAllPatient() throws Exception {
       mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void testGetPatientById(int ints) throws Exception {
          mockMvc.perform(get("/api/patient/id").queryParam("patientId", String.valueOf(ints)))
                .andExpect(status().isOk());

    }

    @Test
    void testGetPatientByIdWithBadId() throws Exception {
         mockMvc.perform(get("/api/patient/id").queryParam("patientId", "150"))
                .andExpect(status().isNotFound());

    }

    @Test
    void testGetPatientByFirstNameAndLastName() throws Exception {
       mockMvc.perform(get("/api/patient").queryParam("firstName", "Test").queryParam("lastName", "TestNone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("@.firstName", is("Test")))
               .andExpect(jsonPath("@.lastName", is("TestNone")));

    }

    @Test
    void testGetPatientByBadFirstNameAndBadLastName() throws Exception {
        mockMvc.perform(get("/api/patient").queryParam("firstName", "test").queryParam("lastName", "test"))
                .andExpect(status().isNotFound());

    }

    @Test
    void testUpdatePatient() throws Exception {
        PatientFullDto patientUpdateDto = new PatientFullDto(2, "test2", "test2", LocalDate.now().minusYears(30), 'F',
                "77 rue du test", "550-550-550");
        String patientAsString = OBJECT_MAPPER.writeValueAsString(patientUpdateDto);
        mockMvc.perform(put("/api/patient/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientAsString))
                .andExpect(status().isOk());


    }

    @Test
    void testUpdatePatientWithBadId() throws Exception {
        PatientFullDto patientUpdateDto = new PatientFullDto(28, "test2", "test2", LocalDate.now().minusYears(30), 'F',
                "77 rue du test", "550-550-550");
        String patientAsString = OBJECT_MAPPER.writeValueAsString(patientUpdateDto);
        mockMvc.perform(put("/api/patient/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientAsString))
                .andExpect(status().isBadRequest());


    }

    @Test
    void testCreatePatientFromJson() throws Exception {
        PatientFullDto patientCreateDto = new PatientFullDto();
        patientCreateDto.setFirstName("test22");
        patientCreateDto.setLastName("test22");
        patientCreateDto.setDateOfBirth(LocalDate.now().minusYears(20));
        patientCreateDto.setGender('M');
        patientCreateDto.setAddress("13 rue du test");
        patientCreateDto.setPhoneNumber("666-666-666");
        String patientAsString = OBJECT_MAPPER.writeValueAsString(patientCreateDto);
        mockMvc.perform(post("/api/patient/add/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientAsString))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreatePatientFromJsonWhichAlreadyExist() throws Exception {
        PatientFullDto patientCreateDto = new PatientFullDto();
        patientCreateDto.setFirstName("Test");
        patientCreateDto.setLastName("TestNone");
        patientCreateDto.setDateOfBirth(LocalDate.now().minusYears(20));
        patientCreateDto.setGender('M');
        patientCreateDto.setAddress("13 rue du test");
        patientCreateDto.setPhoneNumber("666-666-666");
        String patientAsString = OBJECT_MAPPER.writeValueAsString(patientCreateDto);
        mockMvc.perform(post("/api/patient/add/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientAsString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreatePatient() throws Exception {
        mockMvc.perform(post("/api/patient/add")
                        .queryParam("family", "test12")
                        .queryParam("given", "test12")
                        .queryParam("dob", "1945-03-01")
                        .queryParam("sex", "M")
                        .queryParam("address", "13 rue du test")
                        .queryParam("phone", "666-666-666")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreatePatientWhichAlreadyExist() throws Exception {
        mockMvc.perform(post("/api/patient/add")
                        .queryParam("family", "TestNone")
                        .queryParam("given", "Test")
                        .queryParam("dob", "1945-03-01")
                        .queryParam("sex", "M")
                        .queryParam("address", "13 rue du test")
                        .queryParam("phone", "666-666-666")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isBadRequest());
    }
}
