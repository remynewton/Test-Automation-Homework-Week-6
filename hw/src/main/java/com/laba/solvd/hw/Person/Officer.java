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