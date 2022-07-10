package com.mediscreen.history.unit;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mediscreen.history.controller.PractitionerNoteController;
import com.mediscreen.history.model.PractitionerNote;
import com.mediscreen.history.service.IPractitionerNoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@WebMvcTest(controllers = PractitionerNoteController.class)
class PractitionerNoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPractitionerNoteService practitionerNoteService;

    @Test
    void TestGetAllNotes() throws Exception {
        List<PractitionerNote> notes = new ArrayList<>();
        PractitionerNote note = new PractitionerNote();
        note.setPatientId(1);
        note.setNote("test1");
        note.setCreationDate(LocalDateTime.now().minusHours(1));
        notes.add(note);
        when(practitionerNoteService.getAllPractitionerNote()).thenReturn(notes);
        mockMvc.perform(get("/api/history/patHistory"))
                .andExpect(status().isOk());
    }

    @Test
    void TestGetAllNotesWithoutNotes() throws Exception {
        List<PractitionerNote> notes = new ArrayList<>();
        when(practitionerNoteService.getAllPractitionerNote()).thenReturn(notes);
        mockMvc.perform(get("/api/history/patHistory"))
                .andExpect(status().isNotFound());
    }

    @Test
    void TestGetAllPractitionerNoteByPatientId() throws Exception {
        List<PractitionerNote> notes = new ArrayList<>();
        PractitionerNote note = new PractitionerNote();
        note.setPatientId(1);
        note.setNote("test1");
        note.setCreationDate(LocalDateTime.now().minusHours(1));
        notes.add(note);
        when(practitionerNoteService.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        mockMvc.perform(get("/api/history/patHistory/id").queryParam("patientId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void TestGetAllPractitionerNoteByPatientIdWithBadId() throws Exception {
        List<PractitionerNote> notes = new ArrayList<>();
        when(practitionerNoteService.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        mockMvc.perform(get("/api/history/patHistory/id").queryParam("patientId", "1"))
                .andExpect(status().isNotFound());
    }
}
