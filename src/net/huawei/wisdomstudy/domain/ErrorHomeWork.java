package net.huawei.wisdomstudy.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "t_error_homework", catalog = "wisdomstudy")
public class ErrorHomeWork implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int homeworkid;
	private int questionid;
	private int studentid;
	private String currentAnswer;
	private String errorAnswer;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "homework_id")
	public int getHomeworkid() {
		return homeworkid;
	}
	public void setHomeworkid(int homeworkid) {
		this.homeworkid = homeworkid;
	}
	
	@Column(name = "question_id")
	public int getQuestionid() {
		return questionid;
	}
	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}
	
	@Column(name = "student_id")
	public int getStudentid() {
		return studentid;
	}
	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}
	
	@Column(name = "current_Answer")
	public String getCurrentAnswer() {
		return currentAnswer;
	}
	public void setCurrentAnswer(String currentAnswer) {
		this.currentAnswer = currentAnswer;
	}
	
	@Column(name = "error_Answer")
	public String getErrorAnswer() {
		return errorAnswer;
	}
	public void setErrorAnswer(String errorAnswer) {
		this.errorAnswer = errorAnswer;
	}
	
	

}
