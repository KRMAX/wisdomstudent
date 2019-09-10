package net.huawei.wisdomstudy.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author cexo added on 2019-5-19
 * 
 *
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@SelectBeforeUpdate(true)//需要添加@SelectBeforeUpdate(true)，因为@DynamicInsert注解是在插入数据之前进行一次查询，再进行更新，所以必须是同意个连接才能生效
/*hibernate在初始化时，默认配置是表预定义的SQL语句放入session中，比如insert操作都是对表所有字段操作，
如果一个表有很多字段，在初次insert为空值时，会报异常，即使数据库中设了默认值，所以应使用：
dynamicInsert=true, dynamicUpdate=true，这样Hibernate会动态生成SQL语句，没有的值不会出现在语句中。*/
@Table(name = "t_question", catalog = "wisdomstudy")
public class Question  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1833373319540036600L;

	private int id;
	
	private String questionDesc;
	
	private String content;
	
	private QuestionContent qc;
	
	private QuestionType questionType;
	
	private String questionTypeStr;
	
	private KnowledgePoint knowledgePoint;
	
	private boolean isVisible;
	
	private Timestamp createTime;
	
	private String creator;
	
	private Timestamp lastModify;
	
	private String answer;
	
	private int referenceTimes;
	
	private int rightTimes;
	
	private int wrongTimes;
	
	private String analysis;

	private String keyword;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	@Column(name = "question_desc", nullable = false)
	public String getQuestionDesc() {

		return questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {

		this.questionDesc = questionDesc;
	}

	@Column(name = "content", nullable = false)
	public String getContent() {

		return content;
	}

	public void setContent(String content) {

		this.content = content;
	}

	@Transient	//@Transient的作用是指定该属性或字段不是永久的。 它用于注释实体类，映射超类或可嵌入类的属性或字段。
				//一般来说用@Transient是希望该属性不会在数据表中产生字段，但又可以在程序中使用它。
	public QuestionContent getQc() {

		return qc;
	}

	public void setQc(QuestionContent qc) {

		this.qc = qc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_type_id", nullable = false)
	@JsonIgnore
	public QuestionType getQuestionType() {

		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {

		this.questionType = questionType;
	}

	@Column(name = "question_type")
	public String getQuestionTypeStr() {

		return questionTypeStr;
	}

	public void setQuestionTypeStr(String questionTypeStr) {

		this.questionTypeStr = questionTypeStr;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "know_point_id", nullable = false)
	//@JsonIgnore
	public KnowledgePoint getKnowledgePoint() {

		return knowledgePoint;
	}

	public void setKnowledgePoint(KnowledgePoint knowledgePoint) {

		this.knowledgePoint = knowledgePoint;
	}

	//columnDefinition用法：由hibernate指定默认值。@Column(name = "is_visible", columnDefinition="bit default 0 ")
	@Column(name = "is_visible")
	public boolean isVisible() {

		return isVisible;
	}

	public void setVisible(boolean isVisible) {

		this.isVisible = isVisible;
	}

	@Column(name = "create_time")
	public Timestamp getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {

		this.createTime = createTime;
	}

	@Column(name = "creator")
	public String getCreator() {

		return creator;
	}

	public void setCreator(String creator) {

		this.creator = creator;
	}

	@Column(name = "last_modify")
	public Timestamp getLastModify() {

		return lastModify;
	}

	public void setLastModify(Timestamp lastModify) {

		this.lastModify = lastModify;
	}

	@Column(name = "answer", nullable = false)
	public String getAnswer() {

		return answer;
	}

	public void setAnswer(String answer) {

		this.answer = answer;
	}

	@Column(name = "reference_times")
	public int getReferenceTimes() {

		return referenceTimes;
	}

	public void setReferenceTimes(int referenceTimes) {

		this.referenceTimes = referenceTimes;
	}

	@Column(name = "right_times")
	public int getRightTimes() {

		return rightTimes;
	}

	public void setRightTimes(int rightTimes) {

		this.rightTimes = rightTimes;
	}

	@Column(name = "wrong_times")
	public int getWrongTimes() {

		return wrongTimes;
	}

	public void setWrongTimes(int wrongTimes) {

		this.wrongTimes = wrongTimes;
	}

	@Column(name = "analysis")
	public String getAnalysis() {

		return analysis;
	}

	public void setAnalysis(String analysis) {

		this.analysis = analysis;
	}

	@Column(name = "keyword")
	public String getKeyword() {

		return keyword;
	}

	public void setKeyword(String keyword) {

		this.keyword = keyword;
	}
	
	
}
