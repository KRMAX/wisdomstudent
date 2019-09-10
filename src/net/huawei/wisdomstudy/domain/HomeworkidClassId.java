package net.huawei.wisdomstudy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * zuo
 * @author shtao
 *
 */
@Entity
@Table(name = "t_homework_clazz", catalog = "wisdomstudy")
public class HomeworkidClassId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6542867601118004331L;

    private int id;
    private int clazzid;
    private int homeworkid;
    private Double averagescore;
    private int highestscore;
    private int lowestscore;
    private int isalldone;
    private int studentnum;
    private int completionnum;
    
    //自定义查询列表字段
    private String clazzName;
    private String homeworkName;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }


 

    @Column(name = "average_score")
	public Double getAveragescore() {
		return averagescore;
	}



	public void setAveragescore(Double averagescore) {
		this.averagescore = averagescore;
	}

	@Column(name = "highest_score")
	public int getHighestscore() {
		return highestscore;
	}

	public void setHighestscore(int highestscore) {
		this.highestscore = highestscore;
	}

	@Column(name = "lowest_score")
	public int getLowestscore() {
		return lowestscore;
	}

	public void setLowestscore(int lowestscore) {
		this.lowestscore = lowestscore;
	}

	@Column(name = "is_all_done")
	public int getIsalldone() {
		return isalldone;
	}

	public void setIsalldone(int isalldone) {
		this.isalldone = isalldone;
	}

	@Column(name = "student_num")
	public int getStudentnum() {
		return studentnum;
	}

	public void setStudentnum(int studentnum) {
		this.studentnum = studentnum;
	}

	@Column(name = "completion_num")
	public int getCompletionnum() {
		return completionnum;
	}

	public void setCompletionnum(int completionnum) {
		this.completionnum = completionnum;
	}

	@Column(name = "clazz_id")
	public int getClazzid() {
		return clazzid;
	}

	public void setClazzid(int clazzid) {
		this.clazzid = clazzid;
	}

	@Column(name = "homework_id")
	public int getHomeworkid() {
		return homeworkid;
	}

	public void setHomeworkid(int homeworkid) {
		this.homeworkid = homeworkid;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getHomeworkName() {
		return homeworkName;
	}

	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}



}
