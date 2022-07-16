package com.mediscreen.report.util;

import com.mediscreen.report.model.PractitionerNote;

import java.time.LocalDate;
import java.util.List;

public interface ICalculator {

    long calculateAge(final LocalDate birthDate);

    int calculateStringOccurrence(List<PractitionerNote> notes);
}
