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

/*    public Item(Item i){
        this(i.id, i.name, i.quantity, i.units, i.value, i.location);
    }
*/
}
