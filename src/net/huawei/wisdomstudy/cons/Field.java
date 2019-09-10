package net.huawei.wisdomstudy.cons;

import java.util.HashMap;
import java.util.Map;

public enum Field {

	GAO_DENG_SHU_XUE(1, "高等数学"), 
	WEI_JI_FEN(2, "微积分"), 
	XIAN_XING_DAI_SHU(3, "线性代数"), 
	SHU_LI_TONG_JI(4, "概率论与数理统计");

	private int code;

	private String courseName;

	private Field(int code, String courseName) {
		this.setCode(code);
		this.setCourseName(courseName);
	}

	public int getCode() {

		return code;
	}

	public void setCode(int code) {

		this.code = code;
	}

	public String getCourseName() {

		return courseName;
	}

	public void setCourseName(String courseName) {

		this.courseName = courseName;
	}

	public Map<String, Object> toMap() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", this.code);
		map.put("courseName", this.courseName);
		return map;
	}

}
