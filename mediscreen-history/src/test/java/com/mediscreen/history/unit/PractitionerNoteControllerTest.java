package com.mediscreen.history.unit;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediscreen.history.controller.PractitionerNoteController;
import com.mediscreen.history.dto.PractitionerNoteFullDto;
import com.mediscreen.history.model.PractitionerNote;
import com.mediscreen.history.service.IPractitionerNoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@WebMvcTest(controllers = PractitionerNoteController.class)
class PractitionerNoteControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

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

    /*@Test
    void TestUpdatePractitionerNote() throws Exception {
        PractitionerNoteFullDto noteFullDto = new PractitionerNoteFullDto();
        noteFullDto.setId("111111111111111");
        noteFullDto.setPatientId(2);
        noteFullDto.setNote("test2");
        noteFullDto.setCreationDate(LocalDateTime.now().minusHours(1));
        PractitionerNote note = OBJECT_MAPPER.convertValue(noteFullDto, PractitionerNote.class);
        //notes.add(note);
        String noteFullDtoAsString = OBJECT_MAPPER.writeValueAsString(noteFullDto);
        when(practitionerNoteService.updatePractitionerNote(note))
                .thenReturn(note);
        mockMvc.perform(put("/api/history/patHistory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteFullDtoAsString))
                .andExpect(status().isOk());
    }*/

    @Test
    void TestUpdatePractitionerNoteWhichDontExist() throws Exception {
        PractitionerNoteFullDto noteFullDto = new PractitionerNoteFullDto();
        noteFullDto.setId("111111111111111");
        noteFullDto.setPatientId(2);
        noteFullDto.setNote("test2");
        noteFullDto.setCreationDate(LocalDateTime.now().minusHours(1));
        PractitionerNote note = OBJECT_MAPPER.convertValue(noteFullDto, PractitionerNote.class);
        String noteFullDtoAsString = OBJECT_MAPPER.writeValueAsString(noteFullDto);
        when(practitionerNoteService.updatePractitionerNote(note))
                .thenReturn(null);
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
        PractitionerNoteFullDto noteFullDtoReturn = new PractitionerNoteFullDto();
        noteFullDtoReturn.setId("62cd787aba941b281705bb9c");
        noteFullDtoReturn.setPatientId(2);
        noteFullDtoReturn.setNote("test2");
        noteFullDtoReturn.setCreationDate(LocalDateTime.now());
        PractitionerNote note = OBJECT_MAPPER.convertValue(noteFullDto, PractitionerNote.class);
        PractitionerNote noteReturn =  OBJECT_MAPPER.convertValue(noteFullDtoReturn, PractitionerNote.class);
        String noteFullDtoAsString = OBJECT_MAPPER.writeValueAsString(noteFullDto);
        when(practitionerNoteService.savePractitionerNote(note))
                .thenReturn(noteReturn);
        mockMvc.perform(post("/api/history/patHistory/add/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteFullDtoAsString))
                .andExpect(status().isCreated());
    }

}
