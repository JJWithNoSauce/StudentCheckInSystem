package com.principle.checkinproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.principle.checkinproject.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher,String>{
    
}
