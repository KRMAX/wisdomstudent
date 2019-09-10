package net.huawei.wisdomstudy.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.huawei.wisdomstudy.controller.domain.CacheDict;
import net.huawei.wisdomstudy.dao.inter.IDictionaryDao;

@Component("DictionaryService")
public class DictionaryService {

	@Autowired
	private IDictionaryDao dictDao;
	
	/**
	 * @author cexo added on 2018-10-31
	 * 为缓存注入数据
	 * 提示一点：在更新、或者添加数据字典的时候，记得重新调用缓存的方法、更新数据
	 */
	public void getCacheDic() {
		
        CacheDict.dictTypeMap.clear();
        CacheDict.dictItemMap.clear();
        CacheDict.dictTypeMap = dictDao.getAllDicType();
        CacheDict.dictItemMap = dictDao.getAllDicItem();
    
	}
	
}