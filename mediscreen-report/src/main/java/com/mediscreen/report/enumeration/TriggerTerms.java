package com.mediscreen.report.enumeration;

public enum TriggerTerms {

    TRIGGER_TERM_A("Hémoglobine A1C"),
    TRIGGER_TERM_B("Microalbumine"),
    TRIGGER_TERM_C("Taille"),
    TRIGGER_TERM_D("Poids"),
    TRIGGER_TERM_E("Fumeur"),
    TRIGGER_TERM_F("Anormal"),
    TRIGGER_TERM_G("Cholestérol"),
    TRIGGER_TERM_H("Vertige"),
    TRIGGER_TERM_I("Rechute"),
    TRIGGER_TERM_J("Réaction"),
    TRIGGER_TERM_K("Anticorps");

    private final String triggerTerm;

    TriggerTerms(String triggerTerm) {
        this.triggerTerm = triggerTerm;
    }

    public String getTriggerTerm() {
        return triggerTerm;
    }
}
