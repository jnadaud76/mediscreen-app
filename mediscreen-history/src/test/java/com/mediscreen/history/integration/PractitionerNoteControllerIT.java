package com.mediscreen.history.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediscreen.history.dto.PractitionerNoteFullDto;
import com.mediscreen.history.model.PractitionerNote;
import com.mediscreen.history.repository.PractitionerNoteRepository;
import com.mediscreen.history.service.IPractitionerNoteService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class PractitionerNoteControllerIT {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPractitionerNoteService practitionerNoteService;

    @Autowired
    private PractitionerNoteRepository practitionerNoteRepository;

    @BeforeEach
    void setUp() {
        PractitionerNote note1 = new PractitionerNote();
        note1.setId("1111");
        note1.setPatientId(1);
        note1.setNote("test1");
        note1.setCreationDate(LocalDateTime.parse("2022-07-04T08:20:00"));
        PractitionerNote note2 = new PractitionerNote();
        note2.setId("2222");
        note2.setPatientId(2);
        note2.setNote("test2");
        note2.setCreationDate(LocalDateTime.parse("2022-07-04T08:20:00"));
        PractitionerNote note3 = new PractitionerNote();
        note1.setId("3333");
        note3.setPatientId(2);
        note3.setNote("test2");
        note3.setCreationDate(LocalDateTime.parse("2022-07-05T10:00:00"));
        practitionerNoteRepository.save(note1);
        practitionerNoteRepository.save(note2);
        practitionerNoteRepository.save(note3);
    }

    @AfterEach
    void clear() {
        practitionerNoteRepository.deleteAll();
    }

    @Test
    void TestGetAllNotes() throws Exception {
        mockMvc.perform(get("/api/history/patHistory"))
                .andExpect(status().isOk());
    }

    @Test
    void TestGetAllNotesWithoutNotes() throws Exception {
        practitionerNoteRepository.deleteAll();
        mockMvc.perform(get("/api/history/patHistory"))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void TestGetAllPractitionerNoteByPatientId(int ints) throws Exception {
        mockMvc.perform(get("/api/history/patHistory/id").queryParam("patientId", String.valueOf(ints)))
                .andExpect(status().isOk());
    }

    @Test
    void TestGetAllPractitionerNoteByPatientIdWithBadId() throws Exception {
        mockMvc.perform(get("/api/history/patHistory/id").queryParam("patientId", "3"))
                .andExpect(status().isNotFound());
    }

        @Test
        void TestUpdatePractitionerNote() throws Exception {
        PractitionerNoteFullDto noteFullDto = new PractitionerNoteFullDto();
        noteFullDto.setId("2222");
        noteFullDto.setPatientId(1);
        noteFullDto.setNote("test10");
        noteFullDto.setCreationDate(LocalDateTime.parse("2022-07-04T08:20:00"));
        String noteFullDtoAsString = OBJECT_MAPPER.writeValueAsString(noteFullDto);
        mockMvc.perform(put("/api/history/patHistory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteFullDtoAsString))
                        .andExpect(status().isOk());
    }

    @Test
    void TestUpdatePractitionerNoteWhichDontExist() throws Exception {
        PractitionerNoteFullDto noteFullDto = new PractitionerNoteFullDto();
        noteFullDto.setId("8888");
        noteFullDto.setPatientId(125);
        noteFullDto.setNote("test1");
        noteFullDto.setCreationDate(LocalDateTime.parse("2022-07-04T08:20:00"));
        String noteFullDtoAsString = OBJECT_MAPPER.writeValueAsString(noteFullDto);
        mockMvc.perform(put("/api/history/patHistory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteFullDtoAsString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void TestCreatePractitionerNote() throws Exception {
        PractitionerNoteFullDto noteFullDto = new PractitionerNoteFullDto();
        noteFullDto.setPatientId(2);
        noteFullDto.setNote("test2");
        String noteFullDtoAsString = OBJECT_MAPPER.writeValueAsString(noteFullDto);
        mockMvc.perform(post("/api/history/patHistory/add/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteFullDtoAsString))
                .andExpect(status().isCreated());
    }
}
