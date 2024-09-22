package com.principle.checkinproject.model;

import java.util.ArrayList;
import java.util.List;

public class CheckIn {
    private List<Attendacne> attendacnes;

    public CheckIn(){
        this.attendacnes = new ArrayList<Attendacne>();
    }

    public void checking(Student std, String status, String note) {
        Attendacne attendacne = new Attendacne(std,status,note);
        attendacnes.add(attendacne);
    }

    public List<Attendacne> viewHistory(){
        return attendacnes;
    }
}
