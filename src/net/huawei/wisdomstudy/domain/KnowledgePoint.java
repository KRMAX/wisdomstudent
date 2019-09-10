package net.huawei.wisdomstudy.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 知识点对象
 * @author cexo
 */
@Entity
@Table(name = "t_knowledge_point", catalog = "wisdomstudy")
public class KnowledgePoint implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6542867601118004331L;

	private int id;
	
	private String kpDescribe;
	
	private Chapter chapter;
	
	private boolean effectTag = true;//有效标识符
	
	private int homeworkCitations;//作业引用次数
	
	private int examCitations;//考试引用次数
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	@Column(name = "kp_describe", nullable = false, unique = true)
	public String getKpDescribe() {

		return kpDescribe;
	}

	public void setKpDescribe(String kpDescribe) {

		this.kpDescribe = kpDescribe;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chapter_id", nullable = false)
	public Chapter getChapter() {

		return chapter;
	}

	public void setChapter(Chapter chapter) {

		this.chapter = chapter;
	}

	@Column(name = "effect_tag")
	public boolean isEffectTag() {

		return effectTag;
	}

	public void setEffectTag(boolean effectTag) {

		this.effectTag = effectTag;
	}

	@Column(name = "homework_citations")
	public int getHomeworkCitations() {

		return homeworkCitations;
	}

	public void setHomeworkCitations(int homeworkCitations) {

		this.homeworkCitations = homeworkCitations;
	}

	@Column(name = "exam_citations")
	public int getExamCitations() {

		return examCitations;
	}

	public void setExamCitations(int examCitations) {

		this.examCitations = examCitations;
	}

	

	
}
