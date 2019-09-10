package net.huawei.wisdomstudy.controller.domain;

import java.io.Serializable;

public class TreeNode implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1917571065812305662L;
	
	private int id;
	private String name;
	private String level;
	private boolean isLeaf;
	
	public int getId() {

		return id;
	}
	public void setId(int id) {

		this.id = id;
	}
	public String getName() {

		return name;
	}
	public void setName(String name) {

		this.name = name;
	}
	public String getLevel() {

		return level;
	}
	public void setLevel(String level) {

		this.level = level;
	}
	public boolean isLeaf() {

		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {

		this.isLeaf = isLeaf;
	}
	
}
