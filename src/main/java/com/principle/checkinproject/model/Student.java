package com.principle.checkinproject.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="stdID")
    private String stdID;
	
	@Column(name="name")
    private String name;

	@OneToMany(mappedBy = "std", cascade = CascadeType.ALL)
	private List<Attendance> attendances;
	
    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "stdID"), 
            inverseJoinColumns = @JoinColumn(name = "sbjID") 
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

	public List<Subject> getSubject() {
		return subject;
	}


	public void setSubject(List<Subject> subject) {
		this.subject = subject;
	}


	public void setStdID(String stdID) {
		this.stdID = stdID;
	}


	public List<Attendance> getAttendances() {
		return attendances;
	}


	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}
}
