package net.huawei.wisdomstudy.controller.domain;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3980307329629129804L;

	private boolean success = false;

	private String errorMsg = null;

	private String successMsg = null;
	
	private Date createDate = null;

	public boolean isSuccess() {

		return success;
	}

	public void setSuccess(boolean success) {

		this.success = success;
	}

	public String getErrorMsg() {

		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {

		this.errorMsg = errorMsg;
	}

	public String getSuccessMsg() {

		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {

		this.successMsg = successMsg;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
