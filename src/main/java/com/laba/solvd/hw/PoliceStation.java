package com.laba.solvd.hw;

import com.laba.solvd.hw.Beast.*;
import com.laba.solvd.hw.Case.*;
import com.laba.solvd.hw.Exception.PersonTypeException;
import com.laba.solvd.hw.Jail.Jail;
import com.laba.solvd.hw.Person.*;
import com.laba.solvd.hw.Enums.PersonType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PoliceStation {
    private final Set<Case> solvedCases = new HashSet<>();
    private static final Set<Person> persons = new HashSet<>();
    public static final List<PoliceDog> dogs = new ArrayList<>();
    public static List<Jail> jails = new ArrayList<>();

    public static PersonType getTypeOfPerson(Person person) throws PersonTypeException {
        if (person instanceof Officer) {
            return PersonType.OFFICER;
        } else if (person instanceof Criminal) {
            return PersonType.CRIMINAL;
        } else if (person instanceof Victim) {
            return PersonType.VICTIM;
        }
        throw new RuntimeException("Invalid person type.");
    }

    public static void addPerson(Person person) {
        try {
            PersonType type = getTypeOfPerson(person);
            persons.add(person);
        } catch (RuntimeException | PersonTypeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void removePerson(Person person) {
        persons.removeIf(p -> p.getName().equals(person.getName()));
    }

    public void printAllPersons() {
        persons.forEach(System.out::println);
    }

    public void addCase(Case c) {
        solvedCases.add(c);
    }

    public void printReport() {
        List<Case> solvedCases = this.solvedCases.stream()
                .filter(Case::checkSolved)
                .collect(Collectors.toList());
        solvedCases.forEach(System.out::println);
    }
}