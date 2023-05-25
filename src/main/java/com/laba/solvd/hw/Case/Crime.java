package com.laba.solvd.hw.Case;
import com.laba.solvd.hw.Enums.*;

public class Crime implements ICrime {
    private String description;
    private Severity severity;
    private Type type;

    public Crime(Type type) {
        setType(type);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        this.description = type.getDescription();
        this.severity = type.getSeverity();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    @Override
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
}