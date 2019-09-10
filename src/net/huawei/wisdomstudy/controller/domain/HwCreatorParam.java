package net.huawei.wisdomstudy.controller.domain;

import java.util.List;

public class HwCreatorParam {

	private int courseId;
	
	private int teacherId;
	
	private int term;
	
	private String hmName;

	private String hwDiscrib;

	private String clazzsId;
	
	private String homeworkId; 
	
	private List<Integer> idList;
	
	private List<Integer> clazzList;

	public int getCourseId() {

		return courseId;
	}

	public void setCourseId(int courseId) {

		this.courseId = courseId;
	}

	public String getHmName() {

		return hmName;
	}

	public void setHmName(String hmName) {

		this.hmName = hmName;
	}
	public String getHwDiscrib() {

		return hwDiscrib;
	}

	public void setHwDiscrib(String hwDiscrib) {

		this.hwDiscrib = hwDiscrib;
	}

	public List<Integer> getIdList() {

		return idList;
	}

	public void setIdList(List<Integer> idList) {

		this.idList = idList;
	}

	public List<Integer> getClazzList() {

		return clazzList;
	}

	public void setClazzList(List<Integer> clazzList) {

		this.clazzList = clazzList;
	}

	public int getTeacherId() {

		return teacherId;
	}

	public void setTeacherId(int teacherId) {

		this.teacherId = teacherId;
	}

	public int getTerm() {

		return term;
	}

	public void setTerm(int term) {

		this.term = term;
	}
	public String getClazzsId() {

		return clazzsId;
	}

	public void setClazzsId(String clazzsId) {

		this.clazzsId = clazzsId;
	}

	public String getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(String homeworkId) {
		this.homeworkId = homeworkId;
	}
	
}
