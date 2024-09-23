package com.principle.checkinproject.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Student {
	@Id
    @Column(name="stdID")
    private String stdID;
    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "std")
    private List<Attendance> attendance;
    
    @ManyToMany
    @JoinTable(
        name = "student_subject",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
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


	public List<Attendance> getAttendance() {
		return attendance;
	}


	public void setAttendance(List<Attendance> attendance) {
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
