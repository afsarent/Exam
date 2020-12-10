package com.example.model;

import org.springframework.web.multipart.MultipartFile;

public class Data {
	String student_id;
	String classtest_id;
	String school_id;
	MultipartFile file;
	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}
	public String getClasstest_id() {
		return classtest_id;
	}
	public void setClasstest_id(String classtest_id) {
		this.classtest_id = classtest_id;
	}
	public String getSchool_id() {
		return school_id;
	}
	public void setSchool_id(String school_id) {
		this.school_id = school_id;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	

}
