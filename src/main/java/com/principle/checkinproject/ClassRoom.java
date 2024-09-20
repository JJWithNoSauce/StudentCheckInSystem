package com.principle.checkinproject;

import java.util.List;

public class ClassRoom {
    private String classID;
    private List<Subject> subjects;

    public ClassRoom(){}

    public Subject getSubject(String sbjID){
        for (Subject subject : subjects) {
            if(subject.getSbjID() == sbjID) 
                return subject;
        }
        return null;
    }
}
