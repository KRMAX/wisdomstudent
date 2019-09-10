package net.huawei.wisdomstudy.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

/**
 * @author cexo added on 2018-6-20
 * 
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@SelectBeforeUpdate(true)//需要添加@SelectBeforeUpdate(true)，因为@DynamicInsert注解是在插入数据之前进行一次查询，再进行更新，所以必须是同意个连接才能生效
/*hibernate在初始化时，默认配置是表预定义的SQL语句放入session中，比如insert操作都是对表所有字段操作，
如果一个表有很多字段，在初次insert为空值时，会报异常，即使数据库中设了默认值，所以应使用：
dynamicInsert=true, dynamicUpdate=true，这样Hibernate会动态生成SQL语句，没有的值不会出现在语句中。*/
@Table(name = "t_homework", catalog = "wisdomstudy")
public class Homework implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 81099388653321707L;
	
	private int id;
	
	private int courseId;
	
	private int teacherId;
	
	//作业名称
	private String name;
	
	//当前学期--数据字典
	private int term;
	
	//学期内第几次作业
	private int timesInTerm;
	
	private String homeworkPaper;
	
	private String answerPaper;
	
	private int fullScore;
	
	private int score;
	
	private boolean isReleased;
	
	private boolean isEnabled;

	private String questionId;

	private String hmDesc;

	private String creator;

	private Timestamp createTime;
	
	private String teacherName;
	
	private String homeworkStatus;
	/**
	 * 作业发布次数
	 */
	private int citation;

	private String clazzsid;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	@Column(name = "course_id", nullable = false)
	public int getCourseId() {

		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	@Column(name = "teacher_id", nullable = false)
	public int getTeacherId() {

		return teacherId;
	}

	public void setTeacherId(int teacherId) {

		this.teacherId = teacherId;
	}

	@Column(name = "name", nullable = false)
	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Column(name = "term")
	public int getTerm() {

		return term;
	}

	public void setTerm(int term) {

		this.term = term;
	}

	@Column(name = "times_in_term")
	public int getTimesInTerm() {

		return timesInTerm;
	}

	public void setTimesInTerm(int timesInTerm) {

		this.timesInTerm = timesInTerm;
	}

	@Column(name = "homework_paper")
	public String getHomeworkPaper() {

		return homeworkPaper;
	}

	public void setHomeworkPaper(String homeworkPaper) {

		this.homeworkPaper = homeworkPaper;
	}

	@Column(name = "answer_paper")
	public String getAnswerPaper() {

		return answerPaper;
	}

	public void setAnswerPaper(String answerPaper) {

		this.answerPaper = answerPaper;
	}
	
	@Column(name = "full_score")
	public int getFullScore() {

		return fullScore;
	}

	public void setFullScore(int fullScore) {

		this.fullScore = fullScore;
	}

	@Column(name = "is_released")
	public boolean isReleased() {

		return isReleased;
	}

	public void setReleased(boolean isReleased) {

		this.isReleased = isReleased;
	}

	@Column(name = "is_enabled")
	public boolean isEnabled() {

		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {

		this.isEnabled = isEnabled;
	}

	@Column(name = "citation")
	public int getCitation() {

		return citation;
	}

	public void setCitation(int citation) {

		this.citation = citation;
	}
	@Column(name = "question_id")
	public String getQuestionId() {

		return questionId;
	}

	public void setQuestionId(String questionId) {

		this.questionId = questionId;
	}
	@Column(name = "hmDesc")
	public String getHmDesc() {

		return hmDesc;
	}

	public void setHmDesc(String hmDesc) {

		this.hmDesc = hmDesc;
	}
	@Column(name = "creator")
	public String getCreator() {

		return creator;
	}

	public void setCreator(String creator) {

		this.creator = creator;
	}
	@Column(name = "createTime")
	public Timestamp getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {

		this.createTime = createTime;
	}
	@Column(name = "clazzs_id")
	public String getClazzsId() {

		return clazzsid;
	}

	public void setClazzsId(String clazzsid) {

		this.clazzsid = clazzsid;
	}

	//教师中文名
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	//作业状态
	public String getHomeworkStatus() {
		return homeworkStatus;
	}

	public void setHomeworkStatus(String homeworkStatus) {
		this.homeworkStatus = homeworkStatus;
	} 
	//学生查询时作业得分
	public int  getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	} 

	
}
