package com.principle.checkinproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Attendance {
	@Column(name="id")
    private String id;
	@Column(name="status")	
    private String status;
	@Column(name="note")
    private String note;
	
	@ManyToOne
    private CheckIn checkIn;
    
    @ManyToOne
	@JoinColumn(name = "student",nullable = false,referencedColumnName = "stdID")
    private Student std;


    public Attendance(){}

    public Attendance(Student std, String status, String note){
        this.std = std;
        this.status = status;
        this.note = note;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public CheckIn getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(CheckIn checkIn) {
		this.checkIn = checkIn;
	}

	public Student getStd() {
		return std;
	}

	public void setStd(Student std) {
		this.std = std;
	}
}
