package com.laba.solvd.hw.Enums;

import com.laba.solvd.hw.Person.Criminal;
import com.laba.solvd.hw.Person.Officer;
import com.laba.solvd.hw.Person.Person;
import com.laba.solvd.hw.Person.Victim;

import java.util.HashSet;
import java.util.Set;

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
