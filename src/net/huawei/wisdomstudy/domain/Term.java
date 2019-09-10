package net.huawei.wisdomstudy.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学期表
 * @author cexo
 * added on 2018年11月20日
 * id:学期OID
 * termName:学期名
 * currentTerm:是否当前学期
 * expired:是否已过期
 */
@Entity
@Table(name = "t_term", catalog = "wisdomstudy")
public class Term implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3346962378215887520L;

	private int id;
	
	private String termName;
	
	private boolean currentTerm;
	
	private boolean expired;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "term_name", nullable = false, unique = true)
	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	@Column(name = "current_term")
	public boolean isCurrentTerm() {
		return currentTerm;
	}

	public void setCurrentTerm(boolean currentTerm) {
		this.currentTerm = currentTerm;
	}

	@Column(name = "expired")
	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	
}
