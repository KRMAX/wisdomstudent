package net.huawei.wisdomstudy.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "t_homework_student", catalog = "wisdomstudy")
public class HomeWorkStudent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int homeworkid;
	private int studentid;
	private int isalldone;
	private int score;
	private String studentanswer;
	private Timestamp submittime;
	private int issubmit;
	//自定义列表字段
	private String studentName;
	private String studentNo;
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
    
    @Column(name = "student_id")
	public int getStudentid() {
		return studentid;
	}

	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}

	@Column(name = "isalldone")
	public int getIsalldone() {
		return isalldone;
	}

	public void setIsalldone(int isalldone) {
		this.isalldone = isalldone;
	}

	@Column(name = "score")
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
    
	@Column(name = "student_answer")
	public String getStudentAnswer() {
		return studentanswer;
	}

	public void setStudentAnswer(String studentanswer) {
		this.studentanswer = studentanswer;
	}
	@Column(name = "submittime")
	public Timestamp getSubmittime() {

		return submittime;
	}

	public void setSubmittime(Timestamp submittime) {

		this.submittime = submittime;
	}
	
	@Column(name = "issubmit")
	public int getIssubmit() {

		return issubmit;
	}

	public void setIssubmit(int issubmit) {

		this.issubmit = issubmit;
	}
	//自定义查询列
	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String  getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	

	
}
