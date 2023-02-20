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

    public Item(String name, int quantity, String units, int value, String location) {
        this(-1, name, quantity, units, value, location);
    }

    public Item(long id, Item item){
        this(id, item.name, item.quantity, item.units, item.value, item.location );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && Objects.equals(name, item.name) && Objects.equals(quantity, item.quantity)
                && Objects.equals(units, item.units) && Objects.equals(value, item.value)
                && Objects.equals(location, item.location);
    }
}
