package com.principle.checkinproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.principle.checkinproject.model.ClassRoom;

public interface ClassRoomRespository extends JpaRepository<ClassRoom, Long> {
    
}
