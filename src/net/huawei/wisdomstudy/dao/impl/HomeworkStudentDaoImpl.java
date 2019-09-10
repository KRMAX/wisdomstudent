package net.huawei.wisdomstudy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import net.huawei.wisdomstudy.dao.inter.IErrorHomeworkDao;
import net.huawei.wisdomstudy.dao.inter.IHomeworkStudentDao;
import net.huawei.wisdomstudy.domain.HomeWorkStudent;
import net.huawei.wisdomstudy.domain.Student;
import redis.clients.jedis.Jedis;

@Repository
public class HomeworkStudentDaoImpl extends HibernateDaoSupport implements IHomeworkStudentDao{
	@Autowired
	IErrorHomeworkDao erroeHwDao;
	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}
	@Override
	public void addHomeworkStudent(HomeWorkStudent hwst) {
		getHibernateTemplate().save(hwst);
		
	
	}

	@Override
	public List<HomeWorkStudent> getHomeworkStudentListByHomeworkId(int homewokid) {
		String hql = "from HomeWorkStudent s where s.homeworkid=?";
		@SuppressWarnings("unchecked")
		List<HomeWorkStudent> hwstList=(List<HomeWorkStudent>)getHibernateTemplate().find(hql, homewokid);
		return hwstList;
	}
	@Override
	public HomeWorkStudent getHomeworkStudentByHomeworkId(int homewokid, int studentid) {
		HomeWorkStudent hwst=new HomeWorkStudent();
		//自由组合查询条件
				DetachedCriteria criteria=DetachedCriteria.forClass(HomeWorkStudent.class);
				
					criteria.add(Restrictions.eq("homeworkid", homewokid));
				
		
					criteria.add(Restrictions.eq("studentid", studentid));
				
		@SuppressWarnings("unchecked")
		List<HomeWorkStudent> hwstList=(List<HomeWorkStudent>)getHibernateTemplate().findByCriteria(criteria);
		if(hwstList.size()>0) {
			hwst=hwstList.get(0);
		}else {
			hwst=null;
		}

		return hwst;
	}
	@Override
	public void updateHomeworkStudent(HomeWorkStudent hwst) {
		getHibernateTemplate().update(hwst);
		
	}
	@Override
	public List<HomeWorkStudent> getHomeworkStudentList() {
		String hql = "from HomeWorkStudent ";
		@SuppressWarnings("unchecked")
		List<HomeWorkStudent> hwstList=(List<HomeWorkStudent>)getHibernateTemplate().find(hql);
		return hwstList;
	}
	@Override
	public List<HomeWorkStudent> getHomeworkStudentByHomeworkIdAndClazzid(int homewokid, int clazzid) {
		List<HomeWorkStudent> hwstList=new ArrayList<HomeWorkStudent>();
		Jedis jedis = new Jedis("localhost",6379);
		List<String> stIdList=jedis.lrange(String.valueOf(clazzid), 0, -1);
		for(String stId:stIdList) {
			HomeWorkStudent hwst=new HomeWorkStudent();
			//自由组合查询条件
			DetachedCriteria criteria=DetachedCriteria.forClass(HomeWorkStudent.class);
			
				criteria.add(Restrictions.eq("homeworkid", homewokid));
			
	
				criteria.add(Restrictions.eq("studentid",Integer.parseInt(stId)));
				List<HomeWorkStudent> hwstListByClazz=(List<HomeWorkStudent>)getHibernateTemplate().findByCriteria(criteria);
				if(hwstListByClazz.size()>0) {
					hwst=hwstListByClazz.get(0);
					Student st=getHibernateTemplate().get(Student.class, hwst.getStudentid());
					if(st!=null) {
						hwst.setStudentNo(st.getStudentNo());	
						hwst.setStudentName(st.getName());				
					}
					
					hwstList.add(hwst);
				}

		}


		return hwstList;
	
	}

}
