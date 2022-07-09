package com.mediscreen.patient.unit;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediscreen.patient.controller.PatientController;
import com.mediscreen.patient.dto.PatientFromStringDto;
import com.mediscreen.patient.dto.PatientFullDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.service.IPatientService;
import com.mediscreen.patient.util.IConversion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = PatientController.class)
class PatientControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPatientService patientService;

    @MockBean
    private IConversion conversion;

    @Test
    void TestGetAllPatient() throws Exception {
        List<Patient> patients = new ArrayList<>();
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(20), 'M',
                "12 rue du test", "555-555-555");
        patients.add(patient);
        when(patientService.getAllPatient()).thenReturn(patients);
        mockMvc.perform(get("/api/patient/patients"))
                .andExpect(status().isOk());
    }

    @Test
    void TestGetPatientById() throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(20), 'M',
              "12 rue du test", "555-555-555");
       when(patientService.getPatientById(1)).thenReturn(Optional.of(patient));
        mockMvc.perform(get("/api/patient/patient/id").queryParam("patientId", "1"))
                .andExpect(status().isOk());

    }

    @Test
    void TestGetPatientByIdWithBadId() throws Exception {
        when(patientService.getPatientById(1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/patient/patient/id").queryParam("patientId", "1"))
                .andExpect(status().isNotFound());

    }

    @Test
    void TestGetPatientByFirstNameAndLastName() throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(20), 'M',
                "12 rue du test", "555-555-555");
        when(patientService.getPatientByFirstNameAndLastName("test","test")).thenReturn(Optional.of(patient));
        mockMvc.perform(get("/api/patient/patient").queryParam("firstName", "test").queryParam("lastName", "test"))
                .andExpect(status().isOk());

    }

    @Test
    void TestGetPatientByBadFirstNameAndBadLastName() throws Exception {
        when(patientService.getPatientByFirstNameAndLastName("test1","test1")).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/patient/patient").queryParam("firstName", "test1").queryParam("lastName", "test1"))
                .andExpect(status().isNotFound());

    }

  /*@Test
    void TestUpdatePatient() throws Exception {
       PatientFullDto patientUpdateDto = new PatientFullDto(28, "test2", "test2", LocalDate.now().minusYears(20), 'M',
               "13 rue du test", "666-666-666");
      Patient patientUpdated = new Patient(28, "test2", "test2", LocalDate.now().minusYears(20), 'M',
              "13 rue du test", "666-666-666");
       Patient patientToUpdate = OBJECT_MAPPER.convertValue(patientUpdateDto, Patient.class);
       String patientAsString = OBJECT_MAPPER.writeValueAsString(patientUpdateDto);
       when(patientService.updatePatient(patientToUpdate)).thenReturn(patientUpdated);
       mockMvc.perform(put("/api/patient/patient/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientAsString))
                .andExpect(status().isOk());
    }*/

    @Test
    void TestUpdatePatientWithBadId() throws Exception {
        Patient patientUpdate = new Patient(28, "test2", "test2", LocalDate.now().minusYears(20), 'M',
                "13 rue du test", "666-666-666");
        PatientFullDto patientUpdateDto = new PatientFullDto(28, "test2", "test2", LocalDate.now().minusYears(20), 'M',
                "13 rue du test", "666-666-666");
        String patientAsString = OBJECT_MAPPER.writeValueAsString(patientUpdateDto);
        when(patientService.updatePatient(patientUpdate)).thenReturn(null);
        mockMvc.perform(put("/api/patient/patient/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientAsString))
                .andExpect(status().isBadRequest());


    }

    @Test
    void TestCreatePatientFromJson() throws Exception {
        PatientFullDto patientCreateDto = new PatientFullDto();
        patientCreateDto.setFirstName("test2");
        patientCreateDto.setLastName("test2");
        patientCreateDto.setDateOfBirth(LocalDate.now().minusYears(20));
        patientCreateDto.setGender('M');
        patientCreateDto.setAddress("13 rue du test");
        patientCreateDto.setPhoneNumber("666-666-666");
        Patient patient = new Patient(28, "test2", "test2", LocalDate.now().minusYears(20), 'M',
                "13 rue du test", "666-666-666");
        String patientAsString = OBJECT_MAPPER.writeValueAsString(patientCreateDto);
        when(patientService.savePatient(patient)).thenReturn(patient);
        mockMvc.perform(post("/api/patient/patient/add/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientAsString))
                .andExpect(status().isCreated());
    }

    @Test
    void TestCreatePatientFromJsonWhichAlreadyExist() throws Exception {
        PatientFullDto patientCreateDto = new PatientFullDto();
        patientCreateDto.setFirstName("test2");
        patientCreateDto.setLastName("test2");
        patientCreateDto.setDateOfBirth(LocalDate.now().minusYears(20));
        patientCreateDto.setGender('M');
        patientCreateDto.setAddress("13 rue du test");
        patientCreateDto.setPhoneNumber("666-666-666");
        Patient patient = new  Patient (28, "test2", "test2", LocalDate.now().minusYears(20), 'M',
                "13 rue du test", "666-666-666");
        String patientAsString = OBJECT_MAPPER.writeValueAsString(patientCreateDto);
        when(patientService.getPatientByFirstNameAndLastName("test2","test2")).thenReturn(Optional.of(patient));
        mockMvc.perform(post("/api/patient/patient/add/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientAsString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void TestCreatePatient() throws Exception {
        Patient patient = new Patient(28, "test2", "test2", LocalDate.parse("1945-03-01"), 'M',
                "13 rue du test", "666-666-666");
        when(patientService.savePatient(patient)).thenReturn(patient);
        mockMvc.perform(post("/api/patient/patient/add")
                        .queryParam("family", "test2")
                        .queryParam("given", "test2")
                        .queryParam("dob", "1945-03-01")
                        .queryParam("sex", "M")
                        .queryParam("address", "13 rue du test")
                        .queryParam("phone", "666-666-666")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                        .andExpect(status().isCreated());
    }

    @Test
    void TestCreatePatientWhichAlreadyExist() throws Exception {
        Patient patient = new Patient(28, "test2", "test2", LocalDate.parse("1945-03-01"), 'M',
                "13 rue du test", "666-666-666");
        when(patientService.getPatientByFirstNameAndLastName("test2","test2")).thenReturn(Optional.of(patient));
        mockMvc.perform(post("/api/patient/patient/add")
                        .queryParam("family", "test2")
                        .queryParam("given", "test2")
                        .queryParam("dob", "1945-03-01")
                        .queryParam("sex", "M")
                        .queryParam("address", "13 rue du test")
                        .queryParam("phone", "666-666-666")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isBadRequest());
    }

}
