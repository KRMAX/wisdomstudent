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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/**
 * 章节表
 * @author cexo
 *
 */
@Entity
@Table(name = "t_chapter", catalog = "wisdomstudy")
@JsonIgnoreProperties({"field"})
public class Chapter  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6242829347922678552L;

	private int id;
	
	private String chapterName;
	
	//@JsonIgnore,等同于：@JsonIgnoreProperties({"field"})
	private Field field;
	
	private boolean effectTag = true;//有效标识符

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	@Column(name = "chapter_name", nullable = false, unique = true)
	public String getChapterName() {

		return chapterName;
	}

	public void setChapterName(String chapterName) {

		this.chapterName = chapterName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "field_id", nullable = false)
	public Field getField() {

		return field;
	}

	public void setField(Field field) {

		this.field = field;
	}

	@Column(name = "effect_tag")
	public boolean isEffectTag() {

		return effectTag;
	}

	public void setEffectTag(boolean effectTag) {

		this.effectTag = effectTag;
	}
	
	
	
}
