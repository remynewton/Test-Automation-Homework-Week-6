package com.laba.solvd.hw.Case;
import com.laba.solvd.hw.Person.*;

public class Case {
    private String description;
    private Officer assignedOfficer;
    private Criminal suspect;
    private Victim victim;
    private boolean solved;

    public Case(String description, Officer assignedOfficer, Criminal suspect, Victim victim, boolean solved) {
        this.description = description;
        this.assignedOfficer = assignedOfficer;
        this.suspect = suspect;
        this.victim = victim;
        this.solved = solved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Officer getAssignedOfficer() {
        return assignedOfficer;
    }

    public void setAssignedOfficer(Officer assignedOfficer) {
        this.assignedOfficer = assignedOfficer;
    }

    public Criminal getSuspect() {
        return suspect;
    }

    public void setSuspect(Criminal suspect) {
        this.suspect = suspect;
    }

    public Victim getVictim() {
        return victim;
    }

    public void setVictim(Victim victim) {
        this.victim = victim;
    }

    public boolean checkSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public int getSeverity() {
        return suspect.getCrimes().stream()
                .mapToInt(crime -> crime.getSeverity().ordinal())
                .sum();
    }

    @Override
    public String toString() {
        return "Case{" +
                "description='" + description + '\'' +
                ", assignedOfficer=" + assignedOfficer.getName() +
                ", suspect=" + suspect.getName() +
                ", victim=" + victim.getName() +
                ", solved=" + solved +
                ", severity=" + getSeverity() +
                '}';
    }
}