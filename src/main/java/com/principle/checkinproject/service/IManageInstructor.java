package com.principle.checkinproject.service;

import com.principle.checkinproject.model.AInstructor;
import com.principle.checkinproject.model.Teacher;

public interface IManageInstructor {
    public Teacher addInstructor(AInstructor instructor);
    public void removeInstructor(AInstructor instructor);
}
