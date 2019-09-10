package net.huawei.wisdomstudy.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学科POJO
 * @author cexo
 * added on 2018年11月20日
 * 唯一标示OID:id
 * 学科名称:name
 */
@Entity
@Table(name = "t_field", catalog = "wisdomstudy")
public class Field implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4957663707708917635L;

	private int id;
	
	private String name;

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
}
