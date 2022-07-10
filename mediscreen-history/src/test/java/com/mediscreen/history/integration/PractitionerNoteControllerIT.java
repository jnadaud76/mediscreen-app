package com.mediscreen.history.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mediscreen.history.model.PractitionerNote;
import com.mediscreen.history.repository.PractitionerNoteRepository;
import com.mediscreen.history.service.IPractitionerNoteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class PractitionerNoteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPractitionerNoteService practitionerNoteService;

    @Autowired
    private PractitionerNoteRepository practitionerNoteRepository;

    @BeforeEach
    void setUp() {
        PractitionerNote note1 = new PractitionerNote();
        note1.setPatientId(1);
        note1.setNote("test1");
        note1.setCreationDate(LocalDateTime.now().minusHours(1));
        PractitionerNote note2 = new PractitionerNote();
        note2.setPatientId(2);
        note2.setNote("test2");
        note2.setCreationDate(LocalDateTime.now().minusHours(1));
        PractitionerNote note3 = new PractitionerNote();
        note3.setPatientId(2);
        note3.setNote("test2");
        note3.setCreationDate(LocalDateTime.now().minusHours(2));
        practitionerNoteRepository.save(note1);
        practitionerNoteRepository.save(note2);
        practitionerNoteRepository.save(note3);
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
}
