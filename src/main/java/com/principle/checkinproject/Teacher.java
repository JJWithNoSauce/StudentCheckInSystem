package com.principle.checkinproject;

import java.util.List;

public class Teacher implements IInstructor{
    private String name;
    private String teacherID;
    private ClassRoom classRoom;

    public Teacher(){}

    public void checkingStudent(String sbjID, List<String> stdID, List<String> status, List<String> note){
        Subject subject = classRoom.getSubject(sbjID);
        CheckIn check = subject.createCheckIn();

        for (int i = 0; i < stdID.size(); i++) {
            Student std = subject.getStudent(stdID.get(i));

            check.checking(std, status.get(i), note.get(i));
        }
        
    }
}
