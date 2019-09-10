package net.huawei.wisdomstudy.controller.domain;


public class SelectCourseResult {
	
	private int teacherId;
	
	private int clazzId;
	
	private int courseId;
	
	private int termId;

	private String teacherName;
	
	public int getTeacherId() {

		return teacherId;
	}

	public void setTeacherId(int teacherId) {

		this.teacherId = teacherId;
	}

	public int getClazzId() {

		return clazzId;
	}

	public void setClazzId(int clazzId) {

		this.clazzId = clazzId;
	}

	public int getCourseId() {

		return courseId;
	}

	public void setCourseId(int courseId) {

		this.courseId = courseId;
	}
	

	public String toString(){
		return "teacherId = "+teacherId +"clazzId = " + clazzId + "courseId = " + courseId;
	}

	public String getTeacherName() {

		return teacherName;
	}

	public void setTeacherName(String teacherName) {

		this.teacherName = teacherName;
	}

	public int getTermId() {
		return termId;
	}

	public void setTermId(int termId) {
		this.termId = termId;
	}
}
