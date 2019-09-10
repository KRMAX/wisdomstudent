package net.huawei.wisdomstudy.dao.inter;

import java.util.List;
import java.util.Map;

import net.huawei.wisdomstudy.domain.Dictionary;

/**
 *  数据字典Dao操作  
 * @author cexo added on 2019-5-16
 *
 */
public interface IDictionaryDao {

	public Map<String, List<Dictionary>> getAllDicType();
	
	public Map<Integer, Dictionary> getAllDicItem();
}
