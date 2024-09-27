package com.principle.checkinproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
	@JsonBackReference("checkIn-attendance")
	private CheckIn checkIn;

	@ManyToOne
    @JoinColumn(name = "stdID")
    @JsonBackReference("student-attendance")  // Change this to JsonManagedReference for correct serialization
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

	@Override
	public String toString() {
		return "Attendance{" +
				"id=" + id +
				", status='" + status + '\'' +
				", note='" + note + '\'' +
				", student=" + (student != null ? student.getStdID() : "null") +
				'}';
	}
}
