package com.principle.checkinproject.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class CheckIn {
	@OneToMany
	@JoinColumn(name = "attendance",nullable = false,referencedColumnName = "id")	
    private List<Attendance> attendances;

    @ManyToOne
	@JoinColumn(name = "subject",nullable = false,referencedColumnName = "sbjID")	
	private Subject subject;
    
    public CheckIn(){
        this.attendances = new ArrayList<Attendance>();
    }

    public void checking(Student std, String status, String note) {
        Attendance attendance = new Attendance(std,status,note);
        attendances.add(attendance);
    }

    public List<Attendance> viewHistory(){
        return attendances;
    }

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}
