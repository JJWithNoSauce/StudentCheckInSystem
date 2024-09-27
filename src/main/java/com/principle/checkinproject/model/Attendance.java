package com.principle.checkinproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "status")
	private String status;

	@Column(name = "note")
	private String note;

	@ManyToOne
	@JoinColumn(name = "checkIn_id")
	@JsonBackReference
	private CheckIn checkIn;

	@ManyToOne
	@JoinColumn(name = "stdID")
	@JsonBackReference
	private Student student;

	public Attendance() {
	}

	public Attendance(Student std, String status, String note) {
		this.student = std;
		this.status = status;
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
