package com.mediscreen.history.util;

import com.mediscreen.history.dto.PractitionerNoteFromStringDto;
import com.mediscreen.history.model.PractitionerNote;

public interface IConversion {

    PractitionerNote practitionerNoteFromStringDtoToPractitionerNote(PractitionerNoteFromStringDto practitionerNoteFromStringDto);

}
