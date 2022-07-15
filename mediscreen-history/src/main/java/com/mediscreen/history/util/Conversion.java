package com.mediscreen.history.util;

import com.mediscreen.history.dto.PractitionerNoteFromStringDto;
import com.mediscreen.history.model.PractitionerNote;


import org.springframework.stereotype.Component;

@Component
public class Conversion implements IConversion {

    public PractitionerNote practitionerNoteFromStringDtoToPractitionerNote (PractitionerNoteFromStringDto practitionerNoteFromStringDto) {
        PractitionerNote note = new PractitionerNote();
        note.setPatientId(Integer.parseInt(practitionerNoteFromStringDto.getPatId()));
        note.setNote(practitionerNoteFromStringDto.getE());
        return note;
    }

}
