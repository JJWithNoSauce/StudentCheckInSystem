package com.principle.checkinproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.principle.checkinproject.model.ClassRoom;
import com.principle.checkinproject.model.Subject;
import com.principle.checkinproject.model.Teacher;
import com.principle.checkinproject.repository.ClassRoomRespository;

@Service
public class ClassRoomManage {
    
    @Autowired
    private ClassRoomRespository classRoomRespository;

    public ClassRoom createClassRoom(Teacher teacher){
        ClassRoom classRoom = new ClassRoom(teacher);
        return classRoomRespository.save(classRoom);
    }

    public void addSubject(ClassRoom classRoom,Subject sbj){

    }

    public void removeSubject(ClassRoom classRoom,Subject sbj){
        
    }
}
