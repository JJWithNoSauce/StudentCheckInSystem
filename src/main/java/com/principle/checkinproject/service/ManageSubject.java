package com.principle.checkinproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.principle.checkinproject.model.Student;
import com.principle.checkinproject.model.Subject;
import com.principle.checkinproject.repository.SubjectRepository;

@Service
public class ManageSubject {
    @Autowired
    private SubjectRepository subjectRespository;

    public void registerStudent(Subject sbj, Student std){

    }

    public void deregisterStudent(Subject sbj, Student std){
        
    }
}
