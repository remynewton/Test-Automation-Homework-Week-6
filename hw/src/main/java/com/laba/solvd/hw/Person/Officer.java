package com.laba.solvd.hw.Person;

import java.util.concurrent.ThreadLocalRandom;

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
}