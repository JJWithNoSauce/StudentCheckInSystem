package com.principle.checkinproject.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class ClassRoom {
	@Id
    @Column(name="classID")
    private String classID;
    
    @OneToMany(mappedBy = "classRoom")
    private List<Subject> subjects;
    
    @OneToOne
    @JoinColumn(name = "teacher",nullable = false,referencedColumnName = "teacherID")
    private Teacher teacher;


    public ClassRoom(){}

    public Subject getSubject(String sbjID){
        for (Subject subject : subjects) {
            if(subject.getSbjID() == sbjID) 
                return subject;
        }
        return null;
    }

	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
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
		this.teacher = teacher;
	}
}
