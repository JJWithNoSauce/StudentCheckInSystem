package com.principle.checkinproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.principle.checkinproject.repository.StudentRespository;

@Service
public class StudentManage {
    @Autowired
    private StudentRespository studentRespository;

    public void registerStudent(){

    }

    public void deregisterStudent(){
        
    }
}
