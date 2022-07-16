package com.mediscreen.report.util;

import com.mediscreen.report.enumeration.TriggerTerms;
import com.mediscreen.report.model.PractitionerNote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class Calculator implements ICalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);

    /**
     * Calculate a person's age from their birthDate.
     *
     * @param birthDate the birthdate of the person.
     * @return person's age.
     */
    public long calculateAge(final LocalDate birthDate) {

        LocalDate end = LocalDate.now(ZoneId.systemDefault());

        if (birthDate != null && birthDate.isBefore(end)) {
            LOGGER.debug("Person age successfully calculated - value = {}",
                    ChronoUnit.YEARS.between(birthDate, end));
            return ChronoUnit.YEARS.between(birthDate, end);
        } else {
            LOGGER.error("Invalid start date", new IllegalArgumentException());
            throw new IllegalArgumentException();
        }

    }

    public int calculateStringOccurrence(List<PractitionerNote> notes) {
        int count = 0;
        StringBuilder notesToString = new StringBuilder();
       for (PractitionerNote n : notes) {
           notesToString.append(n.getNote());
       }
            for (TriggerTerms t : TriggerTerms.values())
                if (notesToString.toString().toLowerCase().contains(t.toString())) {
                    count++;
                }
        return count;
    }
}
