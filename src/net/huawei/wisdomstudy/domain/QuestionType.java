package net.huawei.wisdomstudy.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_question_type", catalog = "wisdomstudy")
public class QuestionType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3819888741488322171L;

	private int id;
	
	private String name;//题目类型
	
	private boolean subjective = false;//是否主观题

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	@Column(name = "name", nullable = false, unique = true)
	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Column(name = "subjective")
	public boolean isSubjective() {

		return subjective;
	}

	public void setSubjective(boolean subjective) {

		this.subjective = subjective;
	}
	
	
}
