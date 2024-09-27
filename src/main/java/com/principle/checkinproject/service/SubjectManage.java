package com.principle.checkinproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.principle.checkinproject.model.Student;
import com.principle.checkinproject.model.Subject;
import com.principle.checkinproject.repository.AttendanceRepository;
import com.principle.checkinproject.repository.CheckInRepository;
import com.principle.checkinproject.repository.StudentRespository;
import com.principle.checkinproject.repository.SubjectRepository;
import com.principle.checkinproject.model.Attendance;
import com.principle.checkinproject.model.CheckIn;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectManage {
    @Autowired
    CheckInRepository checkInRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    StudentRespository studentRepository;
    @Autowired
    AttendanceRepository attendanceRepository;

    public void registerStudent(Student std, Subject sbj) {
        sbj.getStudents().add(std);
        std.getSubject().add(sbj);
        subjectRepository.save(sbj);
        studentRepository.save(std);
    }

    public void deregisterStudent(Student std, Subject sbj) {
        sbj.getStudents().remove(std);
        std.getSubject().remove(sbj);
        subjectRepository.save(sbj);
        studentRepository.save(std);
    }

    @Transactional
    public CheckIn checkInStudent(String sbjId, List<Attendance> attendances) {
        try {
            Subject subject = subjectRepository.findById(sbjId)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + sbjId));

            if (attendances == null || attendances.isEmpty()) {
                throw new IllegalArgumentException("Attendances list is null or empty");
            }

            CheckIn check = new CheckIn(attendances, subject);
            checkInRepository.save(check);

            for (Attendance attendance : attendances) {
                if (attendance == null) {
                    throw new IllegalArgumentException("Attendance object is null");
                }
                attendance.setCheckIn(check);
                attendanceRepository.save(attendance);
            }

            subject.getCheckIns().add(check);
            subjectRepository.save(subject);
            return check;
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Rethrow as RuntimeException to be handled by global exception handler
            throw new RuntimeException("Error during check-in process: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Subject createSubject(Subject subject) {
        subject.setCheckIns(new ArrayList<CheckIn>());
        subject.setStudents(new ArrayList<Student>());
        return subjectRepository.save(subject);
    }

    public Subject getSubjectById(String id) {
        return subjectRepository.findById(id).orElse(null);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Transactional
    public Subject updateSubject(Subject updatedSubject) {
        return subjectRepository.save(updatedSubject);
    }

    @Transactional
    public void deleteSubject(String id) {
        Subject subject = getSubjectById(id);
        subjectRepository.delete(subject);
    }

    public List<Student> getAllStudentsInSubject(String subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new RuntimeException("Subject not found with id: " + subjectId));
        System.out.println(subject.getSbjID() + "999999999999999999999999999");
        return subject.getStudents();
    }

    public List<CheckIn> getAllCheckInInSubject(String subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new RuntimeException("Subject not found with id: " + subjectId));
        return subject.getCheckIns();
    }

    public CheckIn getCheckInInSubject(String subjectId, int period) {
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new RuntimeException("Subject not found with id: " + subjectId));
        return subject.getCheckIns().get(period - 1);
    }

}
