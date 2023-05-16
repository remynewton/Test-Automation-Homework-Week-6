package com.laba.solvd.hw.Person;
import com.laba.solvd.hw.Person.Person;

import java.util.StringJoiner;

public class Victim extends Person {
    private final String incidentReportNumber;

    public Victim(String name, String DOB, String address, String incidentReportNumber) {
        super(name, DOB, address);
        this.incidentReportNumber = incidentReportNumber;
    }

    public String getIncidentReportNumber() {
        return incidentReportNumber;
    }

    public String getProfile() {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add(getName());
        joiner.add("Age: " + getAge());
        joiner.add("current address: " + getAddress());
        joiner.add("Incident report number: " + getIncidentReportNumber());
        return joiner.toString();
    }
}
