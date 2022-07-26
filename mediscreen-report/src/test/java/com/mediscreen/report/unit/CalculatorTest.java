package com.mediscreen.report.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mediscreen.report.model.PractitionerNote;
import com.mediscreen.report.util.Calculator;
import com.mediscreen.report.util.ICalculator;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class CalculatorTest {

    private final ICalculator calculator = new Calculator();

    @Test
    void calculateStringOccurrenceTest() throws Exception {
        List<PractitionerNote> notes = new ArrayList<>();
        PractitionerNote note1 = new PractitionerNote();
        PractitionerNote note2 = new PractitionerNote();
        note1.setNote("Hémoglobine A1C je fais un test");
        note2.setNote("je fais toujours un test Taille");
        notes.add(note1);
        notes.add(note2);
        int count = calculator.calculateStringOccurrence(notes);
        assertEquals(2, count);
    }

    @Test
    void calculateStringOccurrenceTestWithSameTriggerTerms4Times() throws Exception {
        List<PractitionerNote> notes = new ArrayList<>();
        PractitionerNote note1 = new PractitionerNote();
        PractitionerNote note2 = new PractitionerNote();
        note1.setNote("Taille taille je fais un test");
        note2.setNote("je fais taille toujours un test Taille");
        notes.add(note1);
        notes.add(note2);
        int count = calculator.calculateStringOccurrence(notes);
        assertEquals(1, count);
    }

    @Test
    void calculateStringOccurrenceTestWithNoTriggerTerms() throws Exception {
        List<PractitionerNote> notes = new ArrayList<>();
        PractitionerNote note1 = new PractitionerNote();
        PractitionerNote note2 = new PractitionerNote();
        note1.setNote("je fais un test");
        note2.setNote("je fais un test");
        notes.add(note1);
        notes.add(note2);
        int count = calculator.calculateStringOccurrence(notes);
        assertEquals(0, count);
    }

    @Test
    void calculateStringOccurrenceTestWithMixedUpperCaseLowerCaseTriggerTerms() throws Exception {
        List<PractitionerNote> notes = new ArrayList<>();
        PractitionerNote note1 = new PractitionerNote();
        PractitionerNote note2 = new PractitionerNote();
        note1.setNote("je fais un test MICROalBUMinE");
        note2.setNote("je fais un test fUMeUR");
        notes.add(note1);
        notes.add(note2);
        int count = calculator.calculateStringOccurrence(notes);
        assertEquals(2, count);
    }

    @Test
    void calculateAge() throws Exception {
        LocalDate dob = LocalDate.now().minusYears(20);
        Long age = calculator.calculateAge(dob);
        assertEquals(20, age);
    }

    @Test
    void calculateAgeWithFutureBirthDate() throws Exception {
        LocalDate dob = LocalDate.now().plusYears(20);
        assertThrows(IllegalArgumentException.class, () ->
                calculator.calculateAge(dob));
    }
}
