package com.laba.solvd;
import com.laba.solvd.hw.*;
import com.laba.solvd.hw.Beast.PoliceDog;
import com.laba.solvd.hw.Case.*;
import com.laba.solvd.hw.Enums.*;
import com.laba.solvd.hw.Enums.Type;
import com.laba.solvd.hw.Exception.LogReaderException;
import com.laba.solvd.hw.Jail.Jail;
import com.laba.solvd.hw.Person.*;
import org.apache.log4j.PropertyConfigurator;
import java.io.*;
import org.apache.log4j.Logger;
import java.util.*;
import java.lang.reflect.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.laba.solvd.hw.Beast.Beast.beastCount;
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
        Constructor<?> officerConstructor = officerClass.getDeclaredConstructor(String.class, String.class, String.class, int.class, Rank.class);
        officerConstructor.setAccessible(true);
        Officer officer1 = (Officer) officerConstructor.newInstance("John Doe", "06/12/1981", "123 Main St", 12345, Rank.PATROL);
        Method getProfileMethod = officerClass.getDeclaredMethod("getProfile");
        String profile = (String) getProfileMethod.invoke(officer1);
        logger.info("Officer profile: " + profile);
        PoliceDog patroldog1 = new PoliceDog("Fido", "05/17/2019", true, Breed.GERMAN_SHEPHERD, List.of("Patrol"));
        dogs.add(patroldog1);
        logger.info("Beast Count: " + beastCount);
        PoliceDog dog1 = PoliceDog.findElement(dogs, dog -> dog.getBreed() == Breed.GERMAN_SHEPHERD);
        if (dog1 != null) {
            logger.info(String.format("Found a German Shepherd: " + dog1.getName()));
        } else {
            logger.info(String.format("Could not find a German Shepherd."));
        }
        ArrayList<ICrime> crimes1 = new ArrayList<>();
        crimes1.add(new Crime(Type.JAVA_INSTR));
        Criminal criminal1 = new Criminal("Andrei Trukhanovich", "07/17/1991", "456 Elm St", crimes1);
        boolean foundCrime = criminal1.findCrime(crime -> crime.getType().equals(Type.JAVA_INSTR));
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
        // Threads for police chase
        Thread thread1 = new Thread(criminal1);
        Thread thread2 = new Thread(officer1);
        thread1.start();
        thread2.start();

        CompletableFuture<Void> criminalFuture = CompletableFuture.runAsync(() -> {
            Stream.generate(criminal1::getPosition)
                    .peek(position -> System.out.println("The criminal " + criminal1.getName() + " is running..."))
                    .filter(position -> position == officer1.getPosition())
                    .findFirst()
                    .ifPresent(position -> {
                        criminal1.stopRunning();
                        officer1.stopRunning();
                    });
        });

        CompletableFuture<Void> officerFuture = CompletableFuture.runAsync(() -> {
            Stream.generate(officer1::getPosition)
                    .peek(position -> System.out.println("Officer " + officer1.getName() + " is on the chase..."))
                    .filter(position -> position == criminal1.getPosition())
                    .findFirst()
                    .ifPresent(position -> {
                        criminal1.stopRunning();
                        officer1.stopRunning();
                    });
        });

        CompletableFuture.allOf(criminalFuture, officerFuture).join();

        thread1.join();
        thread2.join();
        // More logging
        logger.info(String.format("The officer has apprehended the criminal. %s", criminal1.getProfile()));
        jail1.addInmate(criminal1);
        logger.info(String.format("%s gets a treat.", patroldog1.getName()));
        String verb = jail1.getInmates().size() > 1 ? "are" : "is";
        logger.info(String.format("There %s now %d inmate(s) in the jail. Number of jails: %d.", verb, jail1.getInmates().size(), Jail.getTotalJails()));
        LogReader logReader = new LogReader();
        logReader.countUniqueWords("./target/police_station.log");
    }
}