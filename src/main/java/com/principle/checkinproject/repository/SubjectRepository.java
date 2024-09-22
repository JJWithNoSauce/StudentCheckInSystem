package com.principle.checkinproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.principle.checkinproject.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject,String>{
    
}
