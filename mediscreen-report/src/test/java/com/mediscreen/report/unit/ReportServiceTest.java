package com.mediscreen.report.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.mediscreen.report.model.Patient;
import com.mediscreen.report.model.PractitionerNote;
import com.mediscreen.report.model.Report;
import com.mediscreen.report.proxy.HistoryServiceProxy;
import com.mediscreen.report.proxy.PatientServiceProxy;
import com.mediscreen.report.service.IReportService;
import com.mediscreen.report.util.ICalculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ReportServiceTest {

    @Autowired
    private IReportService reportService;
    @MockBean
    private HistoryServiceProxy historyServiceProxy;
    @MockBean
    private PatientServiceProxy patientServiceProxy;
    @MockBean
    private ICalculator calculator;

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void generateReportNoneAgeUpper30Men(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(40), 'M',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientById(1)).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(40))).thenReturn(40L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReport(1);
        assertEquals("NONE", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void generateReportNoneAgeUnder30Men(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(15), 'M',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientById(1)).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(15))).thenReturn(15L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReport(1);
        assertEquals("NONE", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void generateReportNoneAgeUnder30Women(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(15), 'F',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientById(1)).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(15))).thenReturn(15L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReport(1);
        assertEquals("NONE", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5})
    void generateReportBorderline(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(40), 'F',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientById(1)).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(40))).thenReturn(40L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReport(1);
        assertEquals("BORDERLINE", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 7})
    void generateReportInDangerAgeUpper30(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(40), 'M',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientById(1)).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(40))).thenReturn(40L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReport(1);
        assertEquals("IN_DANGER", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4})
    void generateReportInDangerAgeUnder30Men(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(15), 'M',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientById(1)).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(15))).thenReturn(15L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReport(1);
        assertEquals("IN_DANGER", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 5, 6})
    void generateReportInDangerAgeUnder30Women(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(15), 'F',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientById(1)).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(15))).thenReturn(15L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReport(1);
        assertEquals("IN_DANGER", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 9, 10, 11})
    void generateReportEarlyOnsetAgeUpper30(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(40), 'M',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientById(1)).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(40))).thenReturn(40L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReport(1);
        assertEquals("EARLY_ONSET", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9, 10, 11})
    void generateReportEarlyOnsetAgeUnder30Men(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(15), 'M',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientById(1)).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(15))).thenReturn(15L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReport(1);
        assertEquals("EARLY_ONSET", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 8, 9, 10, 11})
    void generateReportEarlyOnsetAgeUnder30Women(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(15), 'F',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientById(1)).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(15))).thenReturn(15L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReport(1);
        assertEquals("EARLY_ONSET", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void generateReportByFamilyNameAndGivenNoneAgeUpper30(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(40), 'M',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientByFirstNameAndLastName("test", "test")).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(40))).thenReturn(40L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReportByFamilyNameAndGiven("test", "test");
        assertEquals("NONE", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void generateReportByFamilyNameAndGivenNoneAgeUnder30Men(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(15), 'M',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientByFirstNameAndLastName("test", "test")).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(15))).thenReturn(15L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReportByFamilyNameAndGiven("test", "test");
        assertEquals("NONE", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void generateReportByFamilyNameAndGivenNoneAgeUnder30Women(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(15), 'F',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientByFirstNameAndLastName("test", "test")).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(15))).thenReturn(15L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReportByFamilyNameAndGiven("test", "test");
        assertEquals("NONE", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5})
    void generateReportByFamilyNameAndGivenBorderline(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(40), 'F',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientByFirstNameAndLastName("test", "test")).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(40))).thenReturn(40L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReportByFamilyNameAndGiven("test", "test");
        assertEquals("BORDERLINE", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 7})
    void generateReportByFamilyNameAndGivenInDangerAgeUpper30(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(40), 'M',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientByFirstNameAndLastName("test", "test")).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(40))).thenReturn(40L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReportByFamilyNameAndGiven("test", "test");
        assertEquals("IN_DANGER", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4})
    void generateReportByFamilyNameAndGivenInDangerAgeUnder30Men(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(15), 'M',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientByFirstNameAndLastName("test", "test")).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(15))).thenReturn(15L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReportByFamilyNameAndGiven("test", "test");
        assertEquals("IN_DANGER", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 5, 6})
    void generateReportByFamilyNameAndGivenInDangerAgeUnder30Women(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(15), 'F',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientByFirstNameAndLastName("test", "test")).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(15))).thenReturn(15L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReportByFamilyNameAndGiven("test", "test");
        assertEquals("IN_DANGER", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 9, 10, 11})
    void generateReportByFamilyNameAndGivenEarlyOnsetAgeUpper30(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(40), 'M',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientByFirstNameAndLastName("test", "test")).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(40))).thenReturn(40L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReportByFamilyNameAndGiven("test", "test");
        assertEquals("EARLY_ONSET", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9, 10, 11})
    void generateReportByFamilyNameAndGivenEarlyOnsetAgeUnder30Men(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(15), 'M',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientByFirstNameAndLastName("test", "test")).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(15))).thenReturn(15L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReportByFamilyNameAndGiven("test", "test");
        assertEquals("EARLY_ONSET", report.getRiskLevel());
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 8, 9, 10, 11})
    void generateReportByFamilyNameAndGivenEarlyOnsetAgeUnder30Women(int ints) throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDate.now().minusYears(15), 'F',
                "12 rue du test", "555-555-555");
        PractitionerNote note = new PractitionerNote();
        List<PractitionerNote> notes = new ArrayList<>();
        notes.add(note);
        when(patientServiceProxy.getPatientByFirstNameAndLastName("test", "test")).thenReturn(patient);
        when(historyServiceProxy.getAllPractitionerNoteByPatientId(1)).thenReturn(notes);
        when(calculator.calculateAge(LocalDate.now().minusYears(15))).thenReturn(15L);
        when(calculator.calculateStringOccurrence(notes)).thenReturn(ints);
        Report report = reportService.generateReportByFamilyNameAndGiven("test", "test");
        assertEquals("EARLY_ONSET", report.getRiskLevel());
    }

}
