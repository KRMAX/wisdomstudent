package net.huawei.wisdomstudy.controller.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.huawei.wisdomstudy.domain.Dictionary;

/**
 *  缓存数据字典  
 * @author cexo added on 2018-6-21
 *
 */
public class CacheDict {

	//key: 数据字典中的类型代码，value: 此类型代码的全部项目代码对应的Dictionary对象
	public static Map<String, List<Dictionary>> dictTypeMap = new HashMap<String, List<Dictionary>>();
	
	public static Map<Integer, Dictionary> dictItemMap = new HashMap<Integer, Dictionary>();
	
}