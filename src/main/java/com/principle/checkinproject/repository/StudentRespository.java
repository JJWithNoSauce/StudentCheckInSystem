package com.principle.checkinproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.principle.checkinproject.model.Student;

public interface StudentRespository extends JpaRepository<Student, String>{
    
}
