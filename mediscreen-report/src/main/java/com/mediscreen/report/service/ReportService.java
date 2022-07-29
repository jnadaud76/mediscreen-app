package com.mediscreen.report.service;

import com.mediscreen.report.enumeration.RiskLevel;
import com.mediscreen.report.model.Patient;
import com.mediscreen.report.model.PractitionerNote;
import com.mediscreen.report.model.Report;
import com.mediscreen.report.proxy.HistoryServiceProxy;
import com.mediscreen.report.proxy.PatientServiceProxy;
import com.mediscreen.report.util.ICalculator;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ReportService implements IReportService {

    private final HistoryServiceProxy historyServiceProxy;
    private final PatientServiceProxy patientServiceProxy;
    private final ICalculator calculator;

    public ReportService(HistoryServiceProxy historyServiceProxy, PatientServiceProxy patientServiceProxy, ICalculator calculator) {
        this.historyServiceProxy = historyServiceProxy;
        this.patientServiceProxy = patientServiceProxy;
        this.calculator = calculator;
    }

    /**
     * Generates a report based on the patient's age, sex and the detection of
     * certain keywords present in the practitioner's notes associated with
     * this patient.
     *
     * @param patientId an unique patient id.
     * @return a report containing the patient's information, age
     * and level of risk of developing diabetes.
     */
    public Report generateReport(Integer patientId) {
        Report report = new Report();
        String riskLevel = null;
        int triggersTermsOccurrences;
        List<PractitionerNote> notes;
        Patient patient;
        //If patient don't exist, return null.
        try {
            patient = patientServiceProxy.getPatientById(patientId);
        } catch (Exception e) {
            return null;
        }
        int age = Math.toIntExact(calculator.calculateAge(patient.getDateOfBirth()));
        Character gender = patient.getGender();
        report.setPatient(patient);
        report.setAge(age);

        //If patient don't have any note, return an empty ArrayList.
        try {
            notes = historyServiceProxy
                    .getAllPractitionerNoteByPatientId(patientId);

        } catch (Exception e) {
            notes = new ArrayList<>();
        }
        triggersTermsOccurrences = calculator.calculateStringOccurrence(notes);

        if ((triggersTermsOccurrences >= 0 && triggersTermsOccurrences <= 1 && age > 30)
                || (gender == 'M' && triggersTermsOccurrences >= 0 && triggersTermsOccurrences <= 2 && age < 30)
                || (gender == 'F' && triggersTermsOccurrences >= 0 && triggersTermsOccurrences <= 3 && age < 30)
                || notes.isEmpty()) {
            riskLevel = RiskLevel.NONE.toString();

        } else if (triggersTermsOccurrences >= 2 && triggersTermsOccurrences < 6 && age > 30) {
            riskLevel = RiskLevel.BORDERLINE.toString();

        } else if ((age < 30 && gender == 'M' && triggersTermsOccurrences >= 3 && triggersTermsOccurrences < 5)
                || (age < 30 && gender == 'F' && triggersTermsOccurrences >= 4 && triggersTermsOccurrences < 7)
                || (age > 30 && triggersTermsOccurrences >= 6 && triggersTermsOccurrences < 8)) {
            riskLevel = RiskLevel.IN_DANGER.toString();

        } else if ((age < 30 && gender == 'M' && triggersTermsOccurrences >= 5)
                || (age < 30 && gender == 'F' && triggersTermsOccurrences >= 7)
                || (age > 30 && triggersTermsOccurrences >= 8)) {
            riskLevel = RiskLevel.EARLY_ONSET.toString();
        }

        report.setRiskLevel(riskLevel);
        return report;
    }

    /**
     * Generates a report based on the patient's age, sex and the detection of
     * certain keywords present in the practitioner's notes associated with
     * this patient.
     *
     * @param given      patient first name.
     * @param familyName patient last name.
     * @return a report containing the patient's first name and last name, age
     * and level of risk of developing diabetes.
     */
    public Report generateReportByFamilyNameAndGiven(String given, String familyName) {
        Report report = new Report();
        String riskLevel = null;
        int triggersTermsOccurrences;
        List<PractitionerNote> notes;
        Patient patient;
        //If patient don't exist, return null.
        try {
            patient = patientServiceProxy.getPatientByFirstNameAndLastName(given, familyName);
        } catch (Exception e) {
            return null;
        }
        int age = Math.toIntExact(calculator.calculateAge(patient.getDateOfBirth()));
        Character gender = patient.getGender();
        report.setPatient(patient);
        report.setAge(age);

        //If patient don't have any note, return an empty ArrayList.
        try {
            notes = historyServiceProxy
                    .getAllPractitionerNoteByPatientId(patient.getId());

        } catch (Exception e) {
            notes = new ArrayList<>();
        }

        triggersTermsOccurrences = calculator.calculateStringOccurrence(notes);

        if ((triggersTermsOccurrences >= 0 && triggersTermsOccurrences <= 1 && age > 30)
                || (gender == 'M' && triggersTermsOccurrences >= 0 && triggersTermsOccurrences <= 2 && age < 30)
                || (gender == 'F' && triggersTermsOccurrences >= 0 && triggersTermsOccurrences <= 3 && age < 30)
                || notes.isEmpty()) {
            riskLevel = RiskLevel.NONE.toString();

        } else if (triggersTermsOccurrences >= 2 && triggersTermsOccurrences < 6 && age > 30) {
            riskLevel = RiskLevel.BORDERLINE.toString();

        } else if ((age < 30 && gender == 'M' && triggersTermsOccurrences >= 3 && triggersTermsOccurrences < 5)
                || (age < 30 && gender == 'F' && triggersTermsOccurrences >= 4 && triggersTermsOccurrences < 7)
                || (age > 30 && triggersTermsOccurrences >= 6 && triggersTermsOccurrences < 8)) {
            riskLevel = RiskLevel.IN_DANGER.toString();

        } else if ((age < 30 && gender == 'M' && triggersTermsOccurrences >= 5)
                || (age < 30 && gender == 'F' && triggersTermsOccurrences >= 7)
                || (age > 30 && triggersTermsOccurrences >= 8)) {
            riskLevel = RiskLevel.EARLY_ONSET.toString();
        }

        report.setRiskLevel(riskLevel);
        return report;

    }
}
