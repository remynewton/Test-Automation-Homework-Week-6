package com.laba.solvd.hw.Enums;

public enum PersonType {
    OFFICER("Officer"),
    CRIMINAL("Criminal"),
    VICTIM("Victim");

    private final String type;

    PersonType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}