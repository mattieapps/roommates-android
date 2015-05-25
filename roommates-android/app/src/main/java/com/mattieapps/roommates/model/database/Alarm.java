package com.mattieapps.roommates.model.database;

import io.realm.RealmObject;

/**
 * Created by andrewmattie on 4/7/15.
 */
public class Alarm extends RealmObject {

    private long id;
    private int frequency;
    private String expenseName;
    private String expensePrice;
    private int alarmDay;
    private int alarmMonth;
    private int alarmYear;
    private int alarmHour;
    private int alarmMinuite;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpensePrice() {
        return expensePrice;
    }

    public void setExpensePrice(String expensePrice) {
        this.expensePrice = expensePrice;
    }

    public int getAlarmDay() {
        return alarmDay;
    }

    public void setAlarmDay(int alarmDay) {
        this.alarmDay = alarmDay;
    }

    public int getAlarmMonth() {
        return alarmMonth;
    }

    public void setAlarmMonth(int alarmMonth) {
        this.alarmMonth = alarmMonth;
    }

    public int getAlarmYear() {
        return alarmYear;
    }

    public void setAlarmYear(int alarmYear) {
        this.alarmYear = alarmYear;
    }

    public int getAlarmHour() {
        return alarmHour;
    }

    public void setAlarmHour(int alarmHour) {
        this.alarmHour = alarmHour;
    }

    public int getAlarmMinuite() {
        return alarmMinuite;
    }

    public void setAlarmMinuite(int alarmMinuite) {
        this.alarmMinuite = alarmMinuite;
    }
}
