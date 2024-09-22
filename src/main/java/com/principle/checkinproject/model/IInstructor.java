package com.principle.checkinproject.model;

import java.util.List;

public interface IInstructor {
    public void checkingStudent(String sbjID, List<String> stdID, List<String> status, List<String> note);
}
