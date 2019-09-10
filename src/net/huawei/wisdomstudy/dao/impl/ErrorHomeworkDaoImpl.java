package net.huawei.wisdomstudy.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import net.huawei.wisdomstudy.dao.inter.IErrorHomeworkDao;
import net.huawei.wisdomstudy.domain.ErrorHomeWork;
import net.huawei.wisdomstudy.domain.HomeWorkStudent;
@Repository
public class ErrorHomeworkDaoImpl extends HibernateDaoSupport implements IErrorHomeworkDao{
    @Resource
    public void setSessionFacotry(SessionFactory sessionFacotry) {
        super.setSessionFactory(sessionFacotry);
    }

	@Override
	public void addErrorHomeworkService(ErrorHomeWork errorHomework) {
		getHibernateTemplate().save(errorHomework);
		
	}

	@Override
	public void deleteErrorHomeworkService(ErrorHomeWork errorHomework) {
		getHibernateTemplate().delete(errorHomework);
		
	}

	@Override
	public List<ErrorHomeWork> getErrorHomeworkList(int homeworkid, int studentid) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ErrorHomeWork.class);
		criteria.add(Restrictions.eq("homeworkid", homeworkid));
		
		
		criteria.add(Restrictions.eq("studentid", studentid));
		@SuppressWarnings("unchecked")
		List<ErrorHomeWork> errorHmList=(List<ErrorHomeWork>)getHibernateTemplate().findByCriteria(criteria);
	

		return errorHmList;
	
	}

}
