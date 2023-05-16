package com.laba.solvd.hw.Beast;

import java.util.List;
import java.util.function.Predicate;

public class PoliceDog extends Beast implements SearchAndRescue, Detection, Patrol, Cadaver {
    private Breed breed;
    private List<String> trainings;

    public PoliceDog(String name, String DOB, boolean furry, Breed breed, List<String> trainings) {
        super(name, DOB, furry);
        this.breed = breed;
        this.trainings = trainings;
    }

    public List<String> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<String> trainings) {
        this.trainings = trainings;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public static PoliceDog findElement(List<PoliceDog> list, Predicate<PoliceDog> predicate) {
        for (PoliceDog element : list) {
            if (predicate.test(element)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public void searchAndRescue() {
        if (trainings.contains("Search and Rescue")) {
            System.out.println("Performing search and rescue operation...");
        } else {
            System.out.println("This dog is not trained for search and rescue.");
        }
    }

    @Override
    public void detect() {
        if (trainings.contains("Detection")) {
            System.out.println("Detecting for drugs and explosives...");
        } else {
            System.out.println("This dog is not trained for detection.");
        }
    }

    @Override
    public String patrol() {
        if (trainings.contains("Patrol")) {
            return "Patrolling the area...";
        } else {
            return "This dog is not trained for patrol.";
        }
    }

    @Override
    public void findCadaver() {
        if (trainings.contains("Cadaver")) {
            System.out.println("Searching for cadaver...");
        } else {
            System.out.println("This dog is not trained for cadaver detection.");
        }
    }

    public enum Breed {
        BELGIAN_MALINOIS,
        BLOODHOUND,
        BORDER_COLLIE,
        BOXER,
        DOBERMAN_PINSCHER,
        ENGLISH_SPRINGER_SPANIEL,
        GERMAN_SHEPHERD,
        LABRADOR_RETRIEVER,
        OTHER,
        ROTWEILLER
    }
}