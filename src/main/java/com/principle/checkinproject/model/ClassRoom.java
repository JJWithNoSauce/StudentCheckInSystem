package com.principle.checkinproject.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class ClassRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "classID") 
    private List<Subject> subjects;
    
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "teacher", referencedColumnName = "teacherID")
    private Teacher teacher;

    public ClassRoom() {
        this.subjects = new ArrayList<>();
    }

    public ClassRoom(Teacher teacher) {
        this();
        this.setTeacher(teacher);
    }

    public Subject getSubject(String sbjID) {
        for (Subject subject : subjects) {
            if(subject.getSbjID().equals(sbjID)) 
                return subject;
        }
        return null;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        if (this.teacher != null) {
            this.teacher.setClassRoom(null);
        }
        this.teacher = teacher;
        if (teacher != null) {
            teacher.setClassRoom(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
