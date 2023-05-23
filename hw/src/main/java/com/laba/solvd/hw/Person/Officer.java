package com.laba.solvd.hw.Person;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import com.laba.solvd.hw.Enums.*;

public class Officer extends Person implements Runnable {
    private int badgeNumber;
    private Rank rank;
    public byte position;
    private volatile boolean isRunning = true;

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

    public void run() {
        Stream.generate(() -> {
            System.out.println("Officer " + getName() + " is on the chase...");
            position = (byte) ThreadLocalRandom.current().nextInt(0, 3);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return position;
        }).anyMatch(pos -> !isRunning);
    }

    public void stopRunning() {
        isRunning = false;
    }

    public byte getPosition() {
        return position;
    }
}