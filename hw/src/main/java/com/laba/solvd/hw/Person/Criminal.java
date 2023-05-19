package com.laba.solvd.hw.Person;
import com.laba.solvd.hw.Case.Crime;
import com.laba.solvd.hw.Case.ICrime;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class Criminal extends Person implements Runnable {
    private ArrayList<ICrime> crimes;
    private int crimeCount;
    public byte position;
    private volatile boolean isRunning = true;


    public Criminal(String name, String DOB, String address, ArrayList<ICrime> crimes) {
        super(name, DOB, address);
        this.crimes = crimes;
        this.crimeCount = crimes.size();
    }

    public ArrayList<ICrime> getCrimes() {
        return crimes;
    }

    public void addCrime(ICrime crime) {
        crimes.add(crime);
        crimeCount++;
    }

    public int getCrimeCount() {
        return crimeCount;
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
        if (obj instanceof Criminal) {
            Criminal otherCriminal = (Criminal) obj;
            return getProfile().equals(otherCriminal.getProfile());
        }
        return false;
    }
    
    @Override
    public String getProfile() {
        StringJoiner joiner = new StringJoiner(", ");
        for (ICrime crime : crimes) {
            joiner.add(crime.getDescription() + " (Severity: " + crime.getSeverity() + ")");
        }
        return "The criminal " + getName() + " has committed " + crimeCount + " crime(s), including: " + joiner.toString() + ".";
    }

    public <T extends Crime> boolean findCrime(Predicate<T> predicate) {
        for (ICrime crime : crimes) {
            if (predicate.test((T) crime)) {
                return true;
            }
        }
        return false;
    }

    public void run() {
        this.position = 12;
        while (isRunning) {
            System.out.println("The criminal " + getName() + " is running...");
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
}