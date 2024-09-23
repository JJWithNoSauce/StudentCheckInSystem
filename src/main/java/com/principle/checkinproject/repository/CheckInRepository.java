package com.principle.checkinproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.principle.checkinproject.model.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn,Long>{
    
}
