package com.laba.solvd.hw.Beast;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Beast {
    protected String name;
    protected LocalDate DOB;
    private Period age;
    protected boolean furry;
    public static int beastCount;

    public Beast(String name, String DOB, boolean furry) {
        this.name = name;
        setDOB(DOB);
        this.furry = furry;
        beastCount++;
    }

    public int getAge() {
        return age.getYears();
    }

    public void setDOB(String inputDOB) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(inputDOB, formatter);
        this.DOB = date;
        LocalDate currentDate = LocalDate.now();
        age = Period.between(DOB, currentDate);
    }
}