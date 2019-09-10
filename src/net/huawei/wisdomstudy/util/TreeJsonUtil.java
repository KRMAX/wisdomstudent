package net.huawei.wisdomstudy.util;

import java.util.ArrayList;
import java.util.List;
import net.huawei.wisdomstudy.controller.domain.TreeNode;

public class TreeJsonUtil {

	public static String treeNodeToJson(TreeNode node) {

		StringBuffer sb = new StringBuffer();
		if (node == null || node.equals(null)) {
			return sb.toString();
		} else {
	        sb.append("[");
	        sb.append("{\"id\":" + node.getId() + " ,\"text\":" + "\""
                    + node.getName() + "\"" + " ,\"state\":"
                    + " \"closed\" " + " ,\"attributes\":" + "\""
                    + node.getLevel() + "\"");
	       // sb.append(",\"children\":[{}]");
	        sb.append( "},");
	        sb.deleteCharAt(sb.length() - 1);
	        sb.append("]");
			System.out.println("用自定义工具输出：" + sb.toString());
	        return sb.toString();


		}
	}
	
	public static String treeNodeListToJson(List<TreeNode> nodeList) {

		StringBuffer sb = new StringBuffer();
		if(nodeList.size() == 0){
			return sb.toString();
		}
		sb.append("[");
		for(TreeNode node : nodeList){
			
			sb.append("{\"id\":" + node.getId() + " ,\"text\":" + "\""
                    + node.getName() + "\"");// {"id":1,"text":"高等数学"
			if(!node.isLeaf()){
				sb.append(",\"state\":" + " \"closed\""); // {"id":1,"text":"高等数学","state":"closed"																
			}
			sb.append(",\"attributes\":" + "{\"level\":" + "\""  //  {"id":1,"text":"高等数学","state":"closed","attributes":{"level":"课程"}
                    + node.getLevel() + "\"}");
			sb.append( "},");// {"id":1,"text":"高等数学","state":"closed","attributes":{"level":"课程"}},
		}
		sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        System.out.println("用自定义工具输出：" + sb.toString());
        return sb.toString();
	}
	
	/*
	public static String MapToComboboxJson(Map<Integer,String> map) {

		String json = "";
		if (map == null || map.isEmpty()) {
			return json;
		} else {
			json = "[";
			for (int i = 0; i < map.size(); i++) {
				json += "{\"id\":" + (i + 1);
				json += ",";
				if(i==0){//第一行默认选中
					json += "\"selected\":"+true;
					json += ",";
				}
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
				if(i==0){//第一行默认选中
					json += "\"selected\":"+true;
					json += ",";
				}
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
	
*/	
	public static void main(String[] args) {

		TreeNode node = new TreeNode();
		node.setId(1);
		node.setName("高等数学上");
		node.setLevel("课程");
		node.setLeaf(false);
		//TreeJsonUtil.treeNodeToJson(node);
		TreeNode node1 = new TreeNode();
		node1.setId(2);
		node1.setName("线性代数A");
		node1.setLevel("课程");
		node1.setLeaf(true);
		List<TreeNode> tnlist =new ArrayList<TreeNode>();
		tnlist.add(node);
		tnlist.add(node1);
		TreeJsonUtil.treeNodeListToJson(tnlist);
	}
}
