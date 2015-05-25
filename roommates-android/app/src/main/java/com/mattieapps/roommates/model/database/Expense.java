package com.mattieapps.roommates.model.database;

import io.realm.RealmObject;

/**
 * Created by andrewmattie on 3/1/15.
 */
public class Expense extends RealmObject {

    private long id;
    private String name;
    private String price;
    private String status;
    private String involvedParties;
    private int alarmId;
    private boolean hasAlarmSet;

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

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public boolean isHasAlarmSet() {
        return hasAlarmSet;
    }

    public void setHasAlarmSet(boolean hasAlarmSet) {
        this.hasAlarmSet = hasAlarmSet;
    }
}
