package com.principle.checkinproject;

import java.util.List;

public class Subject {
    private String sbjID;
    private String name;
    private String time;
    private List<CheckIn> checkIns;
    private List<Student> students;

    public Subject(){}

    public CheckIn createCheckIn(){
        CheckIn check = new CheckIn();
        checkIns.add(check);

        return check;
    }

    public String getSbjID(){
        return this.sbjID;
    }

    public Student getStudent(String stdID){
        for (Student student : students) {
            if(student.getStdID() == stdID) 
                return student;
        }
        return null;
    }
}
