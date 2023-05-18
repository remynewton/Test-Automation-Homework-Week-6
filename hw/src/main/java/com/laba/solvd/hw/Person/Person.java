package com.laba.solvd.hw.Person;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Person {
    private String name;
    protected LocalDate DOB;
    private String address;
    static protected Period age;

    public Person (String name, String DOB, String address) {
        this.name = name;
        setDOB(DOB);
        this.address = address;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final int getAge() {
        return age.getYears();
    }

    public final LocalDate getDOB() {
        return DOB;
    }

    public void setDOB(String inputDOB) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        this.DOB = LocalDate.parse(inputDOB, formatter);
        LocalDate currentDate = LocalDate.now();
        age = Period.between(DOB, currentDate);
    }      

    public final String getAddress() {
        return address;
    }

    public final void setAddress(String address) {
        this.address = address;
    }

    public abstract String getProfile();
}