package net.huawei.wisdomstudy.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import net.huawei.wisdomstudy.dao.inter.IKnowledgePointDao;
import net.huawei.wisdomstudy.domain.Chapter;
import net.huawei.wisdomstudy.domain.KnowledgePoint;

@Repository
public class KnowledgePointDaoImpl extends HibernateDaoSupport implements IKnowledgePointDao {

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	/**
	 * 添加知识点
	 * @author cexo addedy on 2017-12-9
	 * @param kpDescribe
	 * @param chapterId
	 * @return
	 */
	@Override
	public void addKnowledgePoint(String kpDescribe, int chapterId) {

		KnowledgePoint kp = new KnowledgePoint();
		kp.setKpDescribe(kpDescribe);
		Chapter c = getHibernateTemplate().load(Chapter.class, chapterId);
		kp.setChapter(c);
		getHibernateTemplate().save(kp);

	}

	/**
	 * 到KnowledgePoint表中查找并分页查询知识点 added on 2017-11-14
	 * @param chapterId 章节OID
	 * @param firstResult 起始位置
	 * @param maxResults  获取的数量
	 * @author cexo
	 * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> getKnowledgePointList(int chapterId, int firstResult, int maxResults) {

		String hql = "select count(*) from KnowledgePoint k where k.chapter.id = ?";
		Long totalInteger = (Long)getHibernateTemplate().find(hql, chapterId).listIterator().next();
		int total = totalInteger.intValue();
		final int f = firstResult;
		final int m = maxResults;
		final int c = chapterId;
		
		@SuppressWarnings("unchecked")
		List<KnowledgePoint> list = (List<KnowledgePoint>) getHibernateTemplate().execute(
				new HibernateCallback<List<KnowledgePoint>>() {
					public List<KnowledgePoint> doInHibernate(Session session)throws HibernateException {
						List<KnowledgePoint> list2 = session.createQuery("from KnowledgePoint k where k.chapter.id = "+c)
								.setFirstResult(f).setMaxResults(m).list();
						return list2;
					}
				});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		
		return map;
	}

	/**
	 * 添加知识点
	 * @author cexo addedy on 2017-12-9
	 * @param kpDescribe
	 * @param chapterId
	 * @param effectTag
	 * @return
	 */
	@Override
	public void addKnowledgePoint(String kpDescribe, int chapterId, boolean effectTag) {

		KnowledgePoint kp = new KnowledgePoint();
		kp.setKpDescribe(kpDescribe);
		Chapter c = getHibernateTemplate().load(Chapter.class, chapterId);
		kp.setChapter(c);
		kp.setEffectTag(effectTag);
		
		getHibernateTemplate().save(kp);
		
	}

	/**
	 * 按章节id获取知识点
	 * @param chapterId 章节OID
	 * @author cexo addey on 2017-12-9
	 * @return List<KnowledgePoint>
	 */
	@Override
	public List<KnowledgePoint> getKnowledgePointList(int chapterId) {

		String hql = "from KnowledgePoint k where k.chapter.id = ?";
		@SuppressWarnings("unchecked")
		List<KnowledgePoint> kpList = (List<KnowledgePoint>) getHibernateTemplate().find(hql, chapterId);

		return kpList;
	}

	/**
	 * 按章节OID查找所含知识点的数量
	 * @author cexo addey on 2017-12-14 
	 * @param chapterId 章节OID 
	 * @return int 知识点数量 
	 */
	@Override
	public int knowledgePointCount(int chapterId) {

		String hql = "select count(*) from KnowledgePoint k where k.chapter.id = ?";
		Long l = (Long)getHibernateTemplate().find(hql, chapterId).iterator().next();
		return l.intValue();
	}

}
