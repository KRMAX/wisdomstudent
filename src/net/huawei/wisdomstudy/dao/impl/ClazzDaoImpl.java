package net.huawei.wisdomstudy.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import net.huawei.wisdomstudy.dao.inter.IClazzDao;
import net.huawei.wisdomstudy.domain.Clazz;

@Repository
public class ClazzDaoImpl extends HibernateDaoSupport implements IClazzDao {

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	@Override
	public void addClazz(Clazz clazz) {
		
		getHibernateTemplate().save(clazz);
	}

	@Override
	public Clazz getClazz(int id) {
		
		return getHibernateTemplate().get(Clazz.class, id);
	}

	@Override
	public Clazz getClazz(String clazzName) {
		String hql = "from Clazz c where c.clazzName = ?";
		@SuppressWarnings("unchecked")
		List<Clazz> clazzList = (List<Clazz>) getHibernateTemplate().find(hql, clazzName);
		if(clazzList.size()==0) {
			Clazz clazz = new Clazz();
			clazz.setId(-1);
			return clazz;
		}
		return clazzList.get(0);
	}
	
	@Override
	public void updateClazz(Clazz clazz) {

		getHibernateTemplate().update(clazz);
	}

	/*@Override
	public void deleteClazz(int id) {
		
		String hql = "from Clazz c where id = ?";	
		
		String hqlCouseSelec = "from CourseSelecting cs where cs.clazz.id = ?";
		
		@SuppressWarnings("unchecked")
		List<Clazz> courseSelectingList = (List<Clazz>) getHibernateTemplate().find(hqlCouseSelec, id);
		if(!courseSelectingList.equals(null) && !courseSelectingList.isEmpty()){
			getHibernateTemplate().deleteAll(courseSelectingList);
		}
		
		getHibernateTemplate().delete(id);
	}*/
	
	@Override
	public Map<String, Object> getClazzes(int firstResult, int maxResults) {

		String hql = "select count(*) from Clazz c";
		Long totalInteger = (Long)getHibernateTemplate().find(hql).listIterator().next();
		int total = totalInteger.intValue();
		final int f = firstResult;
		final int m = maxResults;
		
		@SuppressWarnings("unchecked")
		List<Clazz> list = getHibernateTemplate().execute(
				new HibernateCallback<List<Clazz>>() {
					public List<Clazz> doInHibernate(Session session)throws HibernateException {
						List<Clazz> list2 = session.createQuery("from Clazz c")
								.setFirstResult(f).setMaxResults(m).list();
						return list2;
					}
				});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		
		return map;
	}

	@Override
	public List<Clazz> getClazzes(int admissionYear){
		
		String hql = "from Clazz c where c.admissionYear = ?";
		@SuppressWarnings("unchecked")
		List<Clazz> clazzList = (List<Clazz>) getHibernateTemplate().find(hql, admissionYear);
		return clazzList;
		
	}
	
	@Override
	public Map<String, Object> getClazzes(Clazz clazz, int firstResult, int maxResults) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		/*ArrayList<String> paramNamesList = new ArrayList<String>();
		ArrayList<Object> valuesList = new ArrayList<Object>();

		String hql = "from Clazz c ";
		if(clazz.getClazzName() != null && clazz.getClazzName() != "") {
			hql += " where c.clazzName like:name";
			paramNamesList.add("name");
			valuesList.add("%"+clazz.getClazzName()+"%");
		}
		if(clazz.getAdmissionYear() > 0) {
			if(!paramNamesList.contains("name")) {
				hql += " where c.admissionYear =:admissionYear";
			}else {
				hql += " and c.admissionYear =:admissionYear";
			}
			paramNamesList.add("admissionYear");
			valuesList.add(clazz.getAdmissionYear());
		}
		if(clazz.getMajor() != null && clazz.getMajor() != "") {
			if(paramNamesList.contains("name") || paramNamesList.contains("admissionYear")) {
				hql += " and c.major =:major";
			}else {
				hql += " where c.major =:major";
			}
			paramNamesList.add("major");
			valuesList.add(clazz.getMajor());
		}
		if(clazz.getDepartment() != null && clazz.getDepartment()!= "") {
			if(paramNamesList.contains("name") || paramNamesList.contains("admissionYear")||
					paramNamesList.contains("major")) {
				hql += " and c.department =:department";
			}else {
				hql += " where c.department =:department";
			}
			paramNamesList.add("department");
			valuesList.add(clazz.getDepartment());
		}
		String[] paramNames = new String[paramNamesList.size()];
		paramNamesList.toArray(paramNames);
		Object[] values = new Object[valuesList.size()];
		valuesList.toArray(values);*/
		
		//自由组合查询条件
		DetachedCriteria criteria=DetachedCriteria.forClass(Clazz.class);
		if(clazz.getClazzName() != null && clazz.getClazzName() != "") {
			criteria.add(Restrictions.like("clazzName", "%"+clazz.getClazzName()+"%"));
		}
		if(clazz.getAdmissionYear() != null && clazz.getAdmissionYear() != "") {
			criteria.add(Restrictions.eq("admissionYear", clazz.getAdmissionYear()));
		}
		if(clazz.getMajor() != null && clazz.getMajor() != "") {
			criteria.add(Restrictions.eq("major", clazz.getMajor()));
		}
		if(clazz.getDepartment() != null && clazz.getDepartment()!= "") {
			criteria.add(Restrictions.eq("department", clazz.getDepartment()));
		}
		//如果有分页
		if(firstResult >= 0 && maxResults > 0) {
		
			int total = getHibernateTemplate().findByCriteria(criteria).size();	
			
			@SuppressWarnings("unchecked")
			List<Clazz> clazzList = (List<Clazz>)getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
			map.put("total", total);
			map.put("rows", clazzList);
		}else {
			
			@SuppressWarnings("unchecked")
			//List<Clazz> clazzList = (List<Clazz>) getHibernateTemplate().findByNamedParam(hql, paramNames, values);
			List<Clazz> clazzList = (List<Clazz>)getHibernateTemplate().findByCriteria(criteria);
			map.put("total", clazzList.size());
			map.put("rows", clazzList);
		}	
		/*findByExample()会忽略所有值为null的参数，但如果参数包含8种基础类型，它们的默认空值不是null，这样就会导致错误。
		* 所以，请不要将带有基础类型变量的bean用于findByExample。
		* findByExample()不支持主键。
		* 当我们想要通过主键查询一个值时，应当适用HibernateTemplate提供的
		* Object get(String entityClassName, Serializable id)方法，前者是所查的表的实例的类名（注意不是真实的表名），后者为主键。
		List<Clazz> clazzList = (List<Clazz>) getHibernateTemplate().findByExample(clazz);
		*/
		
		return map;
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getMajorList(){

		String hql = "select major from Clazz c group by c.major order by c.id";//默认按照所在班级的第一个id进行排序
		
		List<String>majorList = (List<String>) getHibernateTemplate().find(hql);
		return majorList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getMajorList(String department) {
		
		String hql = "select major from Clazz c where c.department = ? "
				+ "group by c.major order by c.id";//默认按照所在班级的第一个id进行排序
		
		List<String>majorList = (List<String>) getHibernateTemplate().find(hql, department);
		return majorList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDepartmentList() {
		
		String hql = "select department from Clazz c group by c.department order by c.id";//默认按照所在班级的第一个id进行排序
		
		return  (List<String>) getHibernateTemplate().find(hql);
	}

	@Override
	public List<Clazz> getClazzList(String department, String admissionYear) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Clazz.class);
		if(department != null && department!= "") {
			criteria.add(Restrictions.eq("department", department));
		}
		if(admissionYear != null && admissionYear != "") {
			criteria.add(Restrictions.eq("admissionYear", admissionYear));
		}

		@SuppressWarnings("unchecked")
		List<Clazz> clazzList = (List<Clazz>)getHibernateTemplate().findByCriteria(criteria);
		return clazzList;
	}

	@Override
	public List<Clazz> getClazzList() {
		String hql = "from Clazz ";
		@SuppressWarnings("unchecked")
		List<Clazz> clazzList = (List<Clazz>) getHibernateTemplate().find(hql);
		return clazzList;
	}

}
