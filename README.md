# Test-Automation-Homework-Week-6

My mentor gave me the following assessment of how much my code has completed all of the homeworks prior and current:

hw 2 (hierarchy) - done

hw 3 (abstract) - add logic for working with 5 protected fields

hw 4 (interface) -Create final class, method, variable.
Create a static block, method, variable.

hw 5 (exceprions)
Use try-catch with resources.

hw 6 (collections) - done

hw 7 (maven) - done

hw 8 (utils) - done

hw 9 (lambda, enums)  - enums
Use at least 5 lambda functions from the java.util.function package.
Create 3 custom Lambda functions with generics.
Create 5 complex Enums(with fields, methods, blocks).

hw 10 (streaming collections)
Add 7 collection streaming in the hierarchy with terminal and non-terminal operations.
Using reflection extract information(modifiers, return types, parameters, etc) about fields, constructors, methods. Create object and call method using the only reflection.

# For Homework 3 & 4

HoldingCell is a final class and has a static object variable and a static method. There are two final variables in Crime.java called description and severity. In terms of protected variables, I have 1 in Jail called totalJails and 1 in Beast called furry.

I decided to change the name and DOB variables in the Beast class to protected as well as the age variable in the Person class.

I implemented a static block in the HoldingCell class, so that the holdingCell object would be instantiated at the moment of class creation. I read about it here: https://www.java2novice.com/java-fundamentals/static/singleton/. I actuallly think this implementation is better than my prior one.

I also changed some of the getters and setters in the abstract class Person to final, because I do not want polymorphism.

# For Homework 5

I changed the LogReader class so that it implemented a try-catch with resources.

```
package com.laba.solvd.hw;

import com.laba.solvd.hw.Exception.LogReaderException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LogReader {
    public void countUniqueWords(String logFilePath) throws LogReaderException {
        File file = new File(logFilePath);
        InputStream inputStream = null;
        try {
            inputStream = FileUtils.openInputStream(file);
            String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            String[] words = StringUtils.split(text);
            Set<String> uniqueWords = new HashSet<>();
            Collections.addAll(uniqueWords, words);
            int numUniqueWords = uniqueWords.size();
            FileUtils.writeStringToFile(file, "Number of unique words: " + numUniqueWords, StandardCharsets.UTF_8, true);
            System.out.println("Result written to file.");
        } catch (IOException e) {
            throw new LogReaderException("Failed to read or write to file.", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
```

# For Homeowork 9 & 10

I’d like to note that I made 4 complex enums already and 2 custom lambda functions with generics. I don’t recall how many lambda functions from utils I used, so I’ll make some.

I added 3 lambda functions from utils to PoliceStation.java and there are already 2 streams:

```
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
```

I also added 2 lambda functions from utils to Jail.java:

```
package com.laba.solvd.hw.Jail;

import com.laba.solvd.hw.Person.Criminal;
import com.laba.solvd.hw.Exception.InmateNotFoundException;
import com.laba.solvd.hw.Exception.JailFullException;
import com.laba.solvd.hw.PoliceStation;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class Jail implements IJail {
    private ArrayList<Criminal> inmates = new ArrayList<>();
    private int capacity;
    protected static int totalJails;
    HoldingCell holdingCell = HoldingCell.getInstance();

    private Consumer<String> printError = (msg) -> System.out.println(msg);
    private Function<Criminal, Void> moveToHoldingCell = (criminal) -> {
        holdingCell.addInmate(criminal);
        return null;
    };

    public Jail(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be greater than zero!");
        }
        this.capacity = capacity;
        PoliceStation.jails.add(this);
        totalJails = PoliceStation.jails.size();
    }

    public static int getTotalJails() {
        return totalJails;
    }

    @Override
    public ArrayList<Criminal> getInmates() {
        return inmates;
    }

    @Override
    public void setInmates(ArrayList<Criminal> inmates) {
        this.inmates = inmates;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void addInmate(Criminal criminal) {
        try {
            if (inmates.size() < capacity) {
                inmates.add(criminal);
            } else {
                throw new JailFullException();
            }
        } catch (JailFullException e) {
            printError.accept(e.getMessage());
            moveToHoldingCell.apply(criminal);
        }
    }    

    @Override
    public boolean removeInmate(Criminal criminal) throws InmateNotFoundException {
        if (inmates.remove(criminal)) {
            return true;
        }
    
        printError.accept("The specified inmate was not found in this jail.");
        System.out.print("Do you want to remove that inmate from all jails, including the holding cell? (yes/no): ");
    
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toLowerCase();
        scanner.close();
    
        if (input.equals("yes")) {
            for (Jail jail : PoliceStation.jails) {
                if (jail.getInmates().contains(criminal)) {
                    jail.inmates.remove(criminal);
                }
            }
            holdingCell.removeInmate(criminal);
            throw new InmateNotFoundException("The inmate has been removed from all jails, including the holding cell.");
        } else if (input.equals("no")) {
            return false;
        } else {
            printError.accept("Invalid input. Please enter either 'yes' or 'no'.");
            return removeInmate(criminal);
        }
    }
}
```
