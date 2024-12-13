package com.dataorb.api;

public class EmployeeRecord {

    private int sequenceNo;
    private String empID;
    private String empFName;
    private String empLName;
    private String designation;
    private String event;
    private String value;
    private String eventDate;
    private String notes;

    public EmployeeRecord(int sequenceNo, String empID, String empFName, String empLName, String designation, String event, String value, String eventDate, String notes) {
        this.sequenceNo = sequenceNo;
        this.empID = empID;
        this.empFName = empFName;
        this.empLName = empLName;
        this.designation = designation;
        this.event = event;
        this.value = value;
        this.eventDate = eventDate;
        this.notes = notes;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getEmpFName() {
        return empFName;
    }

    public void setEmpFName(String empFName) {
        this.empFName = empFName;
    }

    public String getEmpLName() {
        return empLName;
    }

    public void setEmpLName(String empLName) {
        this.empLName = empLName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
