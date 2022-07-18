package com.mediscreen.report.service;

import com.mediscreen.report.enumeration.RiskLevel;
import com.mediscreen.report.model.Patient;
import com.mediscreen.report.model.PractitionerNote;
import com.mediscreen.report.model.Report;
import com.mediscreen.report.proxy.HistoryServiceProxy;
import com.mediscreen.report.proxy.PatientServiceProxy;
import com.mediscreen.report.util.ICalculator;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReportService implements IReportService{

    private final HistoryServiceProxy historyServiceProxy;

    private final PatientServiceProxy patientServiceProxy;

    private final ICalculator calculator;


    public ReportService(HistoryServiceProxy historyServiceProxy, PatientServiceProxy patientServiceProxy, ICalculator calculator) {
        this.historyServiceProxy = historyServiceProxy;
        this.patientServiceProxy = patientServiceProxy;
        this.calculator = calculator;
    }

    public Report generateReport (Integer patientId) {
        try {
            Patient patient = patientServiceProxy.getPatientById(patientId);

            List<PractitionerNote> notes = historyServiceProxy
                    .getAllPractitionerNoteByPatientId(patientId);


            int age = Math.toIntExact(calculator.calculateAge(patient.getDateOfBirth()));
            Character gender = patient.getGender();
            int triggersTermsOccurrences = calculator.calculateStringOccurrence(notes);
            String riskLevel = null;

            Report report = new Report();
            report.setPatient(patient);
            report.setAge(age);

            if ((triggersTermsOccurrences >= 0 && triggersTermsOccurrences <= 1 && age > 30)
            || (gender=='M' && triggersTermsOccurrences >= 0 && triggersTermsOccurrences <= 2 && age < 30)
            || (gender=='F' && triggersTermsOccurrences >= 0 && triggersTermsOccurrences <= 3 && age < 30)) {
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
        } catch (Exception e) {
            return null;
        }
    }
}
