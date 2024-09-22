package com.principle.checkinproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.principle.checkinproject.model.IInstructor;
import com.principle.checkinproject.repository.TeacherRepository;

@Service
public class TeacherManage implements IManageInstructor{
    @Autowired
    private TeacherRepository teacherRepository;

    public void addInstructor(IInstructor instructor){

    }


    public void removeInstructor(IInstructor instructor){
        
    }

}
