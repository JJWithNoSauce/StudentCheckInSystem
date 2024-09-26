package com.principle.checkinproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.principle.checkinproject.model.Student;
import com.principle.checkinproject.repository.StudentRespository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentManage {
    @Autowired
    private StudentRespository studentRespository;

    public Student addStudent(Student std) {
        return studentRespository.save(std);
    }

    public List<Student> getAllStudents() {
        return studentRespository.findAll();
    }

    public Student getStudentById(String stdId) {
        return studentRespository.findById(stdId).orElse(null);
    }

    public Student updateStudent(String stdId, Student updatedStudent) {
        Optional<Student> existingStudent = studentRespository.findById(stdId);
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setName(updatedStudent.getName());
            // Update other fields as necessary
            return studentRespository.save(student);
        }
        return null; // Or throw an exception
    }

    public boolean removeStudent(String stdId) {
        if (studentRespository.existsById(stdId)) {
            studentRespository.deleteById(stdId);
            return true;
        }
        return false;
    }
}
