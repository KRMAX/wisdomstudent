package net.huawei.wisdomstudy.cons;

import java.util.HashMap;
import java.util.Map;

public enum State {
	OK(200, "上传成功"),
	ERROR(500, "上传失败"),
	OVER_FILE_LIMIT(501, "超过上传大小限制"),
	NO_SUPPORT_EXTENSION(502, "不支持的扩展名"),
	NO_CONFORM_TEMPLATE(503,"内容与文件模板不符"),
	NULL_FILE(504,"空表，数据行<=1"),
	HEADER_ERROR(506,"传入的excel文件表头与模板不符"),
	DATA_DUPLICATION(505,"数据重复");
	
	private int code;
	private String message;
	private State(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", this.code);
		map.put("message", this.message);
		return map;
	}

}
