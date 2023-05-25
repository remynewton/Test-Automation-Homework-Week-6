package com.laba.solvd.hw.Enums;

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
