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

I added 3 lambda functions from utils to PoliceStation.java. There are two "forEach" terminal stream operations in the code. Additionally, there are collection streamings under getPersonByName and printReport with terminal and non-terminal operations.

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

I also added 2 lambda functions from utils to Jail.java as well as a collection streaming under removeInmate:

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
        boolean foundInThisJail = inmates.remove(criminal);
        if (foundInThisJail) {
            return true;
        }

        printError.accept("The specified inmate was not found in this jail.");
        System.out.print("Do you want to remove that inmate from all jails, including the holding cell? (yes/no): ");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toLowerCase();
        scanner.close();

        if (input.equals("yes")) {
            boolean removedFromAllJails = PoliceStation.jails.stream()
                    .filter(jail -> jail.getInmates().contains(criminal))
                    .peek(jail -> jail.getInmates().remove(criminal))
                    .count() > 0;
            boolean removedFromHoldingCell = holdingCell.removeInmate(criminal);
            if (removedFromAllJails || removedFromHoldingCell) {
                throw new InmateNotFoundException("The inmate has been removed from all jails, including the holding cell.");
            }
        } else if (input.equals("no")) {
            return false;
        } else {
            printError.accept("Invalid input. Please enter either 'yes' or 'no'.");
            return removeInmate(criminal);
        }
        return false;
    }
}
```

I also refactored the getSeverity method Case.java to use a collection streaming.
```
public int getSeverity() {
        return suspect.getCrimes().stream()
                .mapToInt(crime -> crime.getSeverity().ordinal())
                .sum();
    }
```

# What I still need to do:

1. Using reflection extract information(modifiers, return types, parameters, etc) about fields, constructors, methods. Create object and call method using the only reflection.
2. Add 3 more collection streamings in the hierarchy with terminal and non-terminal operations.
3. Add 1 custom lambda function with generics.
4. Add 1 complex Enums(with fields, methods, blocks)

# Part 2

I added an Rank complex enum to Officer.java:
```
package com.laba.solvd.hw.Person;

public class Officer extends Person {
    private int badgeNumber;
    private Rank rank;

    private Officer(String name, String DOB, String address, int badgeNumber, Rank rank) {
        super(name, DOB, address);
        this.badgeNumber = badgeNumber;
        this.rank = rank;
    }

    public int getBadgeNumber() {
        return badgeNumber;
    }

    public void setBadgeNumber(int badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    @Override
    public String getProfile() {
        return "Officer " + getName() + " (" + getRank() + "), Badge #" + getBadgeNumber();
    }

    @Override
    public String toString() {
        return getProfile();
    }

    @Override
    public int hashCode() {
        return getProfile().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Officer) {
            Officer otherOfficer = (Officer) obj;
            return getProfile().equals(otherOfficer.getProfile());
        }
        return false;
    }

    public enum Rank {
        PATROL("Patrol", 0),
        CONSTABLE("Constable", 1),
        SERGEANT("Sergeant", 2),
        LIEUTENANT("Lieutenant", 3),
        CAPTAIN("Captain", 4);

        private final String label;
        private final int level;

        Rank(String label, int level) {
            this.label = label;
            this.level = level;
        }

        public String getLabel() {
            return label;
        }

        public int getLevel() {
            return level;
        }

        public boolean isHigherThan(Rank otherRank) {
            return this.level > otherRank.level;
        }
    }
}
```

I also extracted info in the Main class about the Officer class and created an object and call method using the only reflection. In doing so I also added 3 more collection streamings!

```
package com.laba.solvd;
import com.laba.solvd.hw.*;
import com.laba.solvd.hw.Beast.PoliceDog;
import com.laba.solvd.hw.Case.*;
import com.laba.solvd.hw.Exception.LogReaderException;
import com.laba.solvd.hw.Jail.Jail;
import com.laba.solvd.hw.Person.*;
import org.apache.log4j.PropertyConfigurator;
import java.io.*;
import org.apache.log4j.Logger;
import java.util.*;
import java.lang.reflect.*;
import java.util.stream.Collectors;

import static com.laba.solvd.hw.Beast.Beast.beastCount;
import static com.laba.solvd.hw.Beast.PoliceDog.Breed.GERMAN_SHEPHERD;
import static com.laba.solvd.hw.PoliceStation.dogs;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) throws InterruptedException, IOException, LogReaderException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        Class<?> officerClass = Officer.class;
        // Extract information about fields
        Field[] fields = officerClass.getDeclaredFields();
        Arrays.stream(fields)
                .map(field -> Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() + " " + field.getName())
                .forEach(logger::info);
        // Extract information about constructors
        Constructor<?>[] constructors = officerClass.getDeclaredConstructors();
        Arrays.stream(constructors)
                .map(constructor -> {
                    String parameterTypes = Arrays.stream(constructor.getParameters())
                            .map(parameter -> parameter.getType().getSimpleName())
                            .collect(Collectors.joining(", "));
                    return Modifier.toString(constructor.getModifiers()) + " " + constructor.getName() + "(" + parameterTypes + ")";
                })
                .forEach(logger::info);
        // Extract information about methods
        Method[] methods = officerClass.getDeclaredMethods();
        Arrays.stream(methods)
                .map(method -> {
                    String parameterTypes = Arrays.stream(method.getParameters())
                            .map(parameter -> parameter.getType().getSimpleName())
                            .collect(Collectors.joining(", "));
                    return Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getSimpleName() + " " + method.getName() + "(" + parameterTypes + ")";
                })
                .forEach(logger::info);
        Constructor<?> officerConstructor = officerClass.getDeclaredConstructor(String.class, String.class, String.class, int.class, Officer.Rank.class);
        officerConstructor.setAccessible(true);
        Officer officer1 = (Officer) officerConstructor.newInstance("John Doe", "06/12/1981", "123 Main St", 12345, Officer.Rank.PATROL);
        Method getProfileMethod = officerClass.getDeclaredMethod("getProfile");
        String profile = (String) getProfileMethod.invoke(officer1);
        logger.info("Officer profile: " + profile);
        PoliceDog patroldog1 = new PoliceDog("Fido", "05/17/2019", true, GERMAN_SHEPHERD, List.of("Patrol"));
        dogs.add(patroldog1);
        logger.info("Beast Count: " + beastCount);
        PoliceDog dog1 = PoliceDog.findElement(dogs, dog -> dog.getBreed() == GERMAN_SHEPHERD);
        if (dog1 != null) {
            logger.info(String.format("Found a German Shepherd: " + dog1.getName()));
        } else {
            logger.info(String.format("Could not find a German Shepherd."));
        }
        ArrayList<ICrime> crimes1 = new ArrayList<>();
        crimes1.add(new Crime(Crime.Type.JAVA_INSTR));
        Criminal criminal1 = new Criminal("Andrei Trukhanovich", "07/17/1991", "456 Elm St", crimes1);
        boolean foundCrime = criminal1.findCrime(crime -> crime.getType().equals(Crime.Type.JAVA_INSTR));
        logger.info(String.format("It's %s that the crime Java Instruction was committed by %s.", foundCrime, criminal1.getName()));
        Victim victim1 = new Victim("Remy Newton", "05/22/1997", "789 Oak Ave", "9876");
        Case case1 = new Case("repeated Java instruction", officer1, criminal1, victim1, false);
        PoliceStation station = new PoliceStation();
        UnsolvedCases<Case> unsolvedCases = new UnsolvedCases<>();
        unsolvedCases.add(case1);
        Jail jail1 = new Jail(50);
        PoliceStation.addPerson(officer1);
        PoliceStation.addPerson(criminal1);
        PoliceStation.addPerson(victim1);

        logger.info(String.format("Officer %s from %s department is investigating a case of %s. That's %s.", officer1.getName(), officer1.getRank(), case1.getDescription(), officer1.getProfile()));
        logger.info(String.format("The victim of the crime is %s.", victim1.getProfile()));
        logger.info(String.format("Officer %s uses his trusty police dog %s to patrol the area for the criminal.", officer1.getName(), patroldog1.getName()));
        logger.info(patroldog1.patrol());
        logger.info(String.format("The officer has apprehended the criminal. %s", criminal1.getProfile()));
        jail1.addInmate(criminal1);
        logger.info(String.format("%s gets a treat.", patroldog1.getName()));
        String verb = jail1.getInmates().size() > 1 ? "are" : "is";
        logger.info(String.format("There %s now %d inmate(s) in the jail. Number of jails: %d.", verb, jail1.getInmates().size(), Jail.getTotalJails()));
        LogReader logReader = new LogReader();
        logReader.countUniqueWords("./target/police_station.log");
    }
}
```

I also added a custom lambda function with generics to the Criminal class:
```
public <T extends Crime> boolean findCrime(Predicate<T> predicate) {
        for (ICrime crime : crimes) {
            if (predicate.test((T) crime)) {
                return true;
            }
        }
        return false;
    }
```
I edited LogReader.java to use an actual try catch with resources instead of just a finally block. It seems I was confused before.
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

        try (InputStream inputStream = FileUtils.openInputStream(file)) {
            String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            String[] words = StringUtils.split(text);
            Set<String> uniqueWords = new HashSet<>();
            Collections.addAll(uniqueWords, words);
            int numUniqueWords = uniqueWords.size();
            FileUtils.writeStringToFile(file, "Number of unique words: " + numUniqueWords, StandardCharsets.UTF_8, true);
            System.out.println("Result written to file.");
        } catch (IOException e) {
            throw new LogReaderException("Failed to read or write to file.", e);
        }
    }
}
```

I moved the getters and setters for the name variable in the Beast class to the PoliceDog class, so that I could justify having it be protected. I also implemented a new static variable called beastCount, as suggested by my mentor.

The new homework prompt is as follows:

Practical tasks:

Create 2 Threads using Runnable and Thread.

Create Connection Pool. Use collection from java.util.concurrent package. Connection class may be mocked. The pool should be threadsafe and lazy initialized.

Initialize Connection Pool object of size 5. Load Connection Pool using single threads and Java Thread Pool (7 threads in total). 5 threads should be able to get the connection. 2 Threads should wait for the next available connection. The program should wait as well.

Implement previous point but with interfaces Future and CompletableStage.

I made Criminal and Officer implement Runnable with a little program where the Officer chases the Criminal.

Example code:
```
public byte position;
private volatile boolean isRunning = true;

public void run() {
        while (isRunning) {
            System.out.println("Officer " + getName() + " is on the chase...");
            this.position = (byte) ThreadLocalRandom.current().nextInt(0, 21);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stopRunning() {
        isRunning = false;
    }
```

Then I implemented it in my Main method.
```
        Thread thread1 = new Thread(criminal1);
        Thread thread2 = new Thread(officer1);
        thread1.start();
        thread2.start();
        while (true) {
            if (officer1.position == criminal1.position) {
                criminal1.stopRunning();
                officer1.stopRunning();
                break;
            }
        }
        thread1.join();
        thread2.join();
```
