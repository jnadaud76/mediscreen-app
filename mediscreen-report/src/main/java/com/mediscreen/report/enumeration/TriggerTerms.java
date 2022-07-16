package com.mediscreen.report.enumeration;

public enum TriggerTerms {

    TRIGGER_TERM_A("hémoglobine a1c"),
    TRIGGER_TERM_B("microalbumine"),
    TRIGGER_TERM_C("taille"),
    TRIGGER_TERM_D("poids"),
    TRIGGER_TERM_E("fumeur"),
    TRIGGER_TERM_F("anormal"),
    TRIGGER_TERM_G("cholestérol"),
    TRIGGER_TERM_H("vertige"),
    TRIGGER_TERM_I("rechute"),
    TRIGGER_TERM_J("réaction"),
    TRIGGER_TERM_K("anticorps");

    private final String triggerTerm;

    TriggerTerms(final String triggerTerm) {
        this.triggerTerm = triggerTerm;
    }

    @Override
    public String toString() {
        return triggerTerm;
    }

}
