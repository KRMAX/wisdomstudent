package net.huawei.wisdomstudy.dao.impl;

import net.huawei.wisdomstudy.dao.inter.IHomeworkidClazzidDao;

import java.util.ArrayList;
import java.util.List;

import net.huawei.wisdomstudy.domain.Homework;
import net.huawei.wisdomstudy.domain.HomeworkidClassId;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class HomeworkidClazzidDaoImpl extends HibernateDaoSupport implements IHomeworkidClazzidDao {
    @Resource
    public void setSessionFacotry(SessionFactory sessionFacotry) {
        super.setSessionFactory(sessionFacotry);
    }

    @Override
    public List<HomeworkidClassId> getHomeworkidClazzidByClazzId(int clazzid) {
        String hql = "from HomeworkidClassId  c  where c.clazzid=?";

        @SuppressWarnings("unchecked")
        List<HomeworkidClassId> qList = (List<HomeworkidClassId>) getHibernateTemplate().find(hql,clazzid);
        return qList;
    }

	@Override
	public List<HomeworkidClassId> getHomeworkidClazzidByHomeworkId(int homeworkid) {
		   String hql = " from HomeworkidClassId  c  where c.homeworkid = ?";

	        @SuppressWarnings("unchecked")
	        List<HomeworkidClassId> qList = (List<HomeworkidClassId>) getHibernateTemplate().find(hql,homeworkid);
	        return qList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HomeworkidClassId> getHomeworkClazz(int clazzid, int homeworkid, int firstResult, int maxResults) {
		List<HomeworkidClassId> hmClazzList=new ArrayList<HomeworkidClassId>();
		DetachedCriteria criteria=DetachedCriteria.forClass(HomeworkidClassId.class);
		if(homeworkid!=0) {
			criteria.add(Restrictions.eq("homeworkid", homeworkid));
		}
		if(clazzid!=0) {
			criteria.add(Restrictions.eq("clazzid", clazzid));
		}
		if(firstResult >= 0 && maxResults > 0) {
			hmClazzList=(List<HomeworkidClassId>)getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
		}else {
			 hmClazzList=(List<HomeworkidClassId>)getHibernateTemplate().findByCriteria(criteria);
		}
		return hmClazzList;
	}


}
