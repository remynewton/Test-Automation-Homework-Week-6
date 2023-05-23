package com.laba.solvd.hw.Enums;

public enum Type {
    THEFT("Taking someone's property without their consent.", Severity.LOW),
    ASSAULT("Physically attacking someone, causing them harm.", Severity.LOW),
    BURGLARY("Entering a building illegally to commit a crime.", Severity.MEDIUM),
    FRAUD("Deceiving someone to obtain money or property.", Severity.MEDIUM),
    DRUG_POSSESSION("Illegally possessing controlled substances.", Severity.MEDIUM),
    MURDER("Intentionally killing another person.", Severity.HIGH),
    JAVA_INSTR("The vile act of teaching the Java programming language", Severity.MEDIUM);

    private final String description;
    private final Severity severity;

    Type(String description, Severity severity) {
        this.description = description;
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public Severity getSeverity() {
        return severity;
    }
}
