package com.principle.checkinproject.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class Student {
	@Column(name="stdID")
    private String stdID;
	@Column(name="name")
    private String name;

	@OneToMany(mappedBy = "student")
	private Attendance attendance;
	
    @ManyToMany
	@JoinColumn(name = "subject",nullable = false,referencedColumnName = "sbjID")
    private List<Subject> subject;
    
    public Student(){}


    public String getStdID(){
        return stdID;
    }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Attendance getAttendance() {
		return attendance;
	}


	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}


	public List<Subject> getSubject() {
		return subject;
	}


	public void setSubject(List<Subject> subject) {
		this.subject = subject;
	}


	public void setStdID(String stdID) {
		this.stdID = stdID;
	}
}
