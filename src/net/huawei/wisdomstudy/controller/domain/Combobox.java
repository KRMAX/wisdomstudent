package net.huawei.wisdomstudy.controller.domain;

import java.io.Serializable;

public class Combobox implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3086012065055565714L;

	private int id;
	
	private String text;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getText() {

		return text;
	}

	public void setText(String text) {

		this.text = text;
	}
}
