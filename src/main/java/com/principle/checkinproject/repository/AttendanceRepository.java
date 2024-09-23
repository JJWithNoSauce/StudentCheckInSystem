package com.principle.checkinproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.principle.checkinproject.model.Attendance;


public interface AttendanceRepository extends JpaRepository<Attendance,String>{
    
}
