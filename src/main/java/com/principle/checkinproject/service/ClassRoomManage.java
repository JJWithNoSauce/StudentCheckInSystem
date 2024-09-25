package com.principle.checkinproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.principle.checkinproject.model.ClassRoom;
import com.principle.checkinproject.model.Subject;
import com.principle.checkinproject.model.Teacher;
import com.principle.checkinproject.repository.ClassRoomRespository;

import java.util.List;
import java.util.ArrayList;

@Service
public class ClassRoomManage {
    
    @Autowired
    private ClassRoomRespository classRoomRespository;

    @Autowired
    private SubjectManage subjectManage;

    public ClassRoom createClassRoom(Teacher teacher){
        ClassRoom classRoom = new ClassRoom(teacher);
        return classRoomRespository.save(classRoom);
    }

    public Subject addSubject(ClassRoom classRoom, Subject subject) {
        if (classRoom.getSubjects() == null) {
            classRoom.setSubjects(new ArrayList<>());
        }
        
        if (!classRoom.getSubjects().contains(subject)) {
            
            subject.setClassRoom(classRoom);
            classRoom.getSubjects().add(subject);
            classRoomRespository.save(classRoom);
            
            return subjectManage.createSubject(subject);
        }
        return null;
    }

    public void removeSubject(ClassRoom classRoom, Subject subject) {
        if (classRoom == null || subject == null) {
            throw new IllegalArgumentException("ClassRoom and Subject must not be null");
        }
        
        classRoom.getSubjects().remove(subject);
        subjectManage.deleteSubject(subject.getSbjID());
        classRoomRespository.save(classRoom);

    }

    public List<Subject> getAllSubjects(ClassRoom classRoom) {
        return classRoom.getSubjects() != null ? classRoom.getSubjects() : new ArrayList<>();
    }

    public boolean hasSubject(ClassRoom classRoom, Subject subject) {
        return classRoom.getSubjects() != null && classRoom.getSubjects().contains(subject);
    }

    public Subject getSubjectById(ClassRoom classRoom, String subjectId) {
        return classRoom.getSubjects().stream()
                .filter(subject -> subject.getSbjID().equals(subjectId))
                .findFirst()
                .orElse(null);
    }

    public ClassRoom getClassRoomById(Long id) {
        return classRoomRespository.findById(id).orElse(null);
    }

    public List<ClassRoom> getAllClassRooms() {
        return classRoomRespository.findAll();
    }

    public void deleteClassRoom(Long id) {
        classRoomRespository.deleteById(id);
    }
}
