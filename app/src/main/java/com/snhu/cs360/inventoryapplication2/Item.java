package com.snhu.cs360.inventoryapplication2;

import java.util.Objects;

public class Item {
    private long id;
    private String name;
    private int quantity;
    private String units;
    private int value;
    private String location;

    //Getters. No setters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnits() {
        return units;
    }

    public int getValue() {
        return value;
    }

    public String getLocation() {
        return location;
    }

    public Item(long iId, String iName, int iQuantity, String iUnits, int iValue, String iLocation){
        id = iId;
        name = iName;
        quantity = iQuantity;
        units = iUnits;
        value = iValue;
        location = iLocation;
    }

}
