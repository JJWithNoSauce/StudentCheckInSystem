package com.principle.checkinproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.principle.checkinproject.model.AInstructor;
import com.principle.checkinproject.model.Teacher;
import com.principle.checkinproject.model.ClassRoom;
import com.principle.checkinproject.repository.TeacherRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherManage implements IManageInstructor {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ClassRoomManage classRoomManage;

    // Create
    public Teacher addInstructor(AInstructor instructor) {
        Teacher ins = (Teacher) instructor;
        Teacher savedTeacher = teacherRepository.save(ins);
        // Then create the classroom with the saved teacher
        ClassRoom classRoom = classRoomManage.createClassRoom(savedTeacher);
        savedTeacher.setClassRoom(classRoom);
        return teacherRepository.save(savedTeacher);
    }

    // Read
    public Teacher getTeacherById(String id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacher.orElse(null);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // Update
    public Teacher updateTeacher(String id, Teacher updatedTeacher) {
        if (teacherRepository.existsById(id)) {
            updatedTeacher.setTeacherID(id);
            return teacherRepository.save(updatedTeacher);
        }
        return null;
    }

    // Delete
    public void removeInstructor(AInstructor instructor) {
        if (instructor instanceof Teacher) {
            teacherRepository.delete((Teacher) instructor);
        } else {
            throw new IllegalArgumentException("Instructor must be of type Teacher");
        }
    }

    // Test ใหม่ ลบอาจารย์จาก ID
    public void deleteTeacherById(String id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + id));
        
        teacherRepository.delete(teacher); 
    }
}
