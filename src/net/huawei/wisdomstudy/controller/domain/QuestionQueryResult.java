package net.huawei.wisdomstudy.controller.domain;

import java.sql.Timestamp;

import net.huawei.wisdomstudy.domain.QuestionContent;

/**
 * 与页面交互的Question的替代对象
 * @author cexo
 * added on 2019年6月3日
 */
public class QuestionQueryResult {

	private int questionId;
	private QuestionContent content;
	private String answer;
	private String analysis;
	private int questionTypeId;
	private String questionTypeName;
	private float questionPoint;
	private int knowledgePointId;
	private String kpDescribe;
	private Timestamp createTime;
	private String creator;
	
	public int getQuestionId() {

		return questionId;
	}
	public void setQuestionId(int questionId) {

		this.questionId = questionId;
	}
	public QuestionContent getContent() {

		return content;
	}
	public void setContent(QuestionContent content) {

		this.content = content;
	}
	public String getAnswer() {

		return answer;
	}
	public void setAnswer(String answer) {

		this.answer = answer;
	}
	public String getAnalysis() {

		return analysis;
	}
	public void setAnalysis(String analysis) {

		this.analysis = analysis;
	}
	public int getQuestionTypeId() {

		return questionTypeId;
	}
	public void setQuestionTypeId(int questionTypeId) {

		this.questionTypeId = questionTypeId;
	}
	public String getQuestionTypeName() {

		return questionTypeName;
	}
	public void setQuestionTypeName(String questionTypeName) {

		this.questionTypeName = questionTypeName;
	}

	public float getQuestionPoint() {

		return questionPoint;
	}
	public void setQuestionPoint(float questionPoint) {

		this.questionPoint = questionPoint;
	}
	public int getKnowledgePointId() {

		return knowledgePointId;
	}
	public void setKnowledgePointId(int knowledgePointId) {

		this.knowledgePointId = knowledgePointId;
	}
	public String getKpDescribe() {

		return kpDescribe;
	}
	public void setKpDescribe(String kpDescribe) {

		this.kpDescribe = kpDescribe;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
}
