package com.mattieapps.roommates.systems.sql_database;

/**
 * Created by andrewmattie on 3/1/15.
 */
@Deprecated
public class Expense {

    private long id;
    private String name;
    private String price;
    private String status;
    private String involvedParties;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvolvedParties() {
        return involvedParties;
    }

    public void setInvolvedParties(String involvedParties) {
        this.involvedParties = involvedParties;
    }

    public String toString() {
        return name + "\n" + price + "\n" + status;
    }
}
