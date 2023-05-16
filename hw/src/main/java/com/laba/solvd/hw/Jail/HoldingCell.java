package com.laba.solvd.hw.Jail;

import com.laba.solvd.hw.Person.Criminal;

import java.util.ArrayList;

public final class HoldingCell implements IJail {
    private ArrayList<Criminal> inmates = new ArrayList<>();
    private static final HoldingCell holdingCell;
    private HoldingCell() {
        // private constructor to prevent instantiation
    }

    static {
        holdingCell = new HoldingCell();
    }

    public static HoldingCell getInstance() {
        return holdingCell;
    }

    @Override
    public void addInmate(Criminal criminal) {
        inmates.add(criminal);
    }

    @Override
    public boolean removeInmate(Criminal criminal) {
        return inmates.remove(criminal);
    }

    @Override
    public ArrayList<Criminal> getInmates() {
        return inmates;
    }

    @Override
    public void setInmates(ArrayList<Criminal> inmates) {
        this.inmates = inmates;
    }
}
