package net.huawei.wisdomstudy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.huawei.wisdomstudy.domain.Field;

public class ComboboxJsonUtil {

	public static String ListToComboboxJson(List<String> comboboxList) {

		String json = "";
		if (comboboxList == null || comboboxList.isEmpty()) {
			return json;
		} else {
			json = "[";
			for (int i = 0; i < comboboxList.size(); i++) {
				json += "{\"id\":" + (i + 1);
				json += ",";
				/*if(i==0){//第一行默认选中
					json += "\"selected\":"+true;
					json += ",";
				}*/
				json += "\"text\":" + "\"" + comboboxList.get(i) + "\"}";
				if (i < comboboxList.size() - 1) {
					json += ",";
				}
			}
			json += "]";
			System.out.println("用自定义工具输出：");
			System.out.println(json);
			return json;
		}
	}
	
	
	public static String MapToComboboxJson(Map<Integer,String> map) {

		String json = "";
		if (map == null || map.isEmpty()) {
			return json;
		} else {
			json = "[";
			for (int i = 0; i < map.size(); i++) {
				json += "{\"id\":" + (i + 1);
				json += ",";
				/*if(i==0){//第一行默认选中
					json += "\"selected\":"+true;
					json += ",";
				}*/
				json += "\"text\":" + "\"" + map.get(i) + "\"}";
				if (i < map.size() - 1) {
					json += ",";
				}
			}
			json += "]";
			System.out.println("用自定义工具输出：");
			System.out.println(json);
			return json;
		}
	}
	
	
	
	
	
	public static String KnowledgePointListToDatagridJson(List<Field> comboboxList) {

		String json = "";
		if (comboboxList.isEmpty() || comboboxList.size() == 0) {
			return json;
		} else {
			json = "[";
			for (int i = 0; i < comboboxList.size(); i++) {
				json += "{\"fieldId\":" + (comboboxList.get(i).getId());
				json += ",";
				/*if(i==0){//第一行默认选中
					json += "\"selected\":"+true;
					json += ",";
				}*/
				json += "\"fieldName\":" + "\"" + comboboxList.get(i).getName() + "\"}";
				if (i < comboboxList.size() - 1) {
					json += ",";
				}
			}
			json += "]";
			System.out.println(json);
			return json;
		}
	}
	
	
	public static void main(String[] args) {
/*
		List<String> comboboxList = new ArrayList<String>();
		comboboxList.add("单选题");
		comboboxList.add("多选题");
		String s = ComboboxJsonUtil.ListToComboboxJson(comboboxList);*/
		
		List<Field> fieldList = new ArrayList<Field>();
		Field field = new Field();
		field.setId(net.huawei.wisdomstudy.cons.Field.GAO_DENG_SHU_XUE.getCode());
		field.setName(net.huawei.wisdomstudy.cons.Field.GAO_DENG_SHU_XUE.getCourseName());
		fieldList.add(field);
		field = new Field();
		field.setId(net.huawei.wisdomstudy.cons.Field.XIAN_XING_DAI_SHU.getCode());
		field.setName(net.huawei.wisdomstudy.cons.Field.XIAN_XING_DAI_SHU.getCourseName());
		fieldList.add(field);
		ComboboxJsonUtil.KnowledgePointListToDatagridJson(fieldList);

	}
}
