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