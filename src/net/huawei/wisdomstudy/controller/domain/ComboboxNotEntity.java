package net.huawei.wisdomstudy.controller.domain;

import java.io.Serializable;

public class ComboboxNotEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5216099029552453156L;

	private String id;
	
	private String text;

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public String getText() {

		return text;
	}

	public void setText(String text) {

		this.text = text;
	}

}
