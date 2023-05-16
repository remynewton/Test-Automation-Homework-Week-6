package com.laba.solvd.hw;

import com.laba.solvd.hw.Beast.PoliceDog;
import com.laba.solvd.hw.Case.*;
import com.laba.solvd.hw.Jail.Jail;
import com.laba.solvd.hw.Person.*;
import com.laba.solvd.hw.Exception.PersonTypeException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.function.*;

public class PoliceStation {

    private final Set<Case> solvedCases = new HashSet<>();
    private static final Set<Person> persons = new HashSet<>();
    public static final List<PoliceDog> dogs = new ArrayList<>();
    public static List<Jail> jails = new ArrayList<>();

    private static final Function<Person, PersonType> getTypeOfPerson = p -> {
        switch (p.getClass().getSimpleName()) {
            case "Officer":
                return PersonType.OFFICER;
            case "Criminal":
                return PersonType.CRIMINAL;
            case "Victim":
                return PersonType.VICTIM;
            default:
                try {
                    throw new PersonTypeException("Invalid person type.");
                } catch (PersonTypeException e) {
                    throw new RuntimeException(e);
                }
        }
    };

    private static PersonType getTypeOfPerson(Person p) throws PersonTypeException {
        return getTypeOfPerson.apply(p);
    }

    public static Consumer<Person> addPerson = (Person p) -> {
        try {
            PersonType type = getTypeOfPerson(p);
            type.addPerson(p);
            persons.add(p);
        } catch (PersonTypeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    };

    public static void addPerson(Person p) {
        addPerson.accept(p);
    }

    public void removePerson(Person p) {
        persons.removeIf(person -> person.getName().equals(p.getName()));
    }

    public enum PersonType {
        OFFICER("Officer", new HashSet<Officer>()),
        CRIMINAL("Criminal", new HashSet<Criminal>()),
        VICTIM("Victim", new HashSet<Victim>());

        private final String type;
        private final Set<Person> personSet;

        private PersonType(String type, Set<? extends Person> personSet) {
            this.type = type;
            this.personSet = (Set<Person>) personSet;
        }

        public String getType() {
            return type;
        }

        public Set<Person> getPersonSet() {
            return personSet;
        }

        public void addPerson(Person person) {
            personSet.add(person);
        }

        public void removePerson(Person person) {
            personSet.remove(person);
        }

        public boolean containsPerson(Person person) {
            return personSet.contains(person);
        }

        public <T extends Person> T getPersonByName(String name) {
            return (T) personSet.stream()
                    .filter(p -> p.getName().equals(name))
                    .findFirst().orElse(null);
        }
    }

    public void printAllPersons() {
        persons.forEach((Consumer<Person>) person -> System.out.println(person));
    }

    public void addCase(Case c) {
        solvedCases.add(c);
    }

    public void printReport() {
        List<Case> solvedCases = this.solvedCases.stream()
                .filter(c -> c.checkSolved())
                .collect(Collectors.toList());
        solvedCases.forEach(System.out::println);
    }
}