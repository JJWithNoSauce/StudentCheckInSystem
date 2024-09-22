package com.principle.checkinproject.model;

public class Attendacne {
    private String id;
    private CheckIn checkIn;
    private Student std;
    private String staus;
    private String note;

    public Attendacne(){}

    public Attendacne(Student std, String staus, String note){
        this.std = std;
        this.staus = staus;
        this.note = note;
    }
}
