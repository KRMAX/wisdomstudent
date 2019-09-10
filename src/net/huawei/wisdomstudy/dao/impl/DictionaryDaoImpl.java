package net.huawei.wisdomstudy.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import net.huawei.wisdomstudy.dao.inter.IDictionaryDao;
import net.huawei.wisdomstudy.domain.Dictionary;

/**
 *  数据字典Dao操作  
 * @author cexo added on 2018-10-31
 *
 */
@Repository
public class DictionaryDaoImpl extends HibernateDaoSupport implements IDictionaryDao{
	
	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}


	/**
	 * @author cexo added on 2018-10-31
	 * 获取数据字典中所有类型代码及对应的Dictionary对象，key：类型代码，value：此类型代码下的所有Dictionary对象
	 */
	@Override
	public Map<String, List<Dictionary>> getAllDicType() {

		Map<String, List<Dictionary>> dicMap = new HashMap<String, List<Dictionary>>();
		String hql = "select d.dicTypeName from Dictionary d group by d.dicTypeName ";
		@SuppressWarnings("unchecked")
		List<String> dicTypeList = (List<String>) getHibernateTemplate().find(hql);
		for(String dicTypeName : dicTypeList){
			String hql1 = "from Dictionary d where d.dicTypeName = ?";
			@SuppressWarnings("unchecked")
			List<Dictionary> dicList = (List<Dictionary>) getHibernateTemplate().find(hql1, dicTypeName);
			dicMap.put(dicTypeName, dicList);
		}
		return dicMap;
	}
	
	@Override
	public Map<Integer, Dictionary> getAllDicItem() {

		Map<Integer, Dictionary> dicMap = new HashMap<Integer, Dictionary>();
		String hql = "from Dictionary d ";
		@SuppressWarnings("unchecked")
		List<Dictionary> dicList = (List<Dictionary>) getHibernateTemplate().find(hql);
		for(Dictionary dic : dicList){
			dicMap.put(dic.getId(), dic);
		}
		return dicMap;
	}

}
