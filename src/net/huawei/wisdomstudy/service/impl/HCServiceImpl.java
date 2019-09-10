package net.huawei.wisdomstudy.service.impl;

import net.huawei.wisdomstudy.domain.HomeworkidClassId;
import net.huawei.wisdomstudy.service.inter.IHCService;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
@Transactional
public class HCServiceImpl extends HibernateDaoSupport implements IHCService {
    @Resource
    public void setSessionFacotry(SessionFactory sessionFacotry) {
        super.setSessionFactory(sessionFacotry);
    }
    @Override
    public List<HomeworkidClassId> getHomeworkidClazzidByClazzId(int clazzId) {
        DetachedCriteria criteria=DetachedCriteria.forClass(HomeworkidClassId.class);

           criteria.add(Restrictions.eq("clazzid", clazzId));

        List<HomeworkidClassId> qList = (List<HomeworkidClassId>)getHibernateTemplate().findByCriteria(criteria);
        System.out.println(qList.size());
        return qList;
    }
}
