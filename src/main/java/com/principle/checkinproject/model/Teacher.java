package com.principle.checkinproject.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;


@Entity
public class Teacher extends AInstructor{
	@Column(name="teacherID")
    private String teacherID;
	@Column(name="name")
    private String name;
	@OneToOne(mappedBy= "teacher")
    private ClassRoom classRoom;

    public Teacher(){}


	public String getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClassRoom getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	}

}
