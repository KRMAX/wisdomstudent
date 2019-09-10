package net.huawei.wisdomstudy.dao.impl;

import javax.annotation.Resource;


import net.huawei.wisdomstudy.domain.HomeworkidClassId;


import org.hibernate.SessionFactory;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.dao.inter.IHwDao;
import net.huawei.wisdomstudy.domain.Homework;




/**
 * 作业增删改查
 * @author cexo added on 2018-7-4
 *
 */
@Transactional(readOnly=true)
@Repository
public class HwDaoImpl extends HibernateDaoSupport implements IHwDao{
	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	@Override
	public Homework getHomeworkById(int id) {
		Homework homework=new Homework();
		homework=getHibernateTemplate().get(Homework.class,id);



	return homework;

	}

	@Override
	public int getMaxId() {
		String hql = " select max(id) from Homework ";

		int id = 0;
        if(getHibernateTemplate().find(hql).listIterator().next()!=null){
			id=(Integer)getHibernateTemplate().find(hql).listIterator().next();
		}
		return id;
	}

	@Override
	public void addHomeworkidClassid(HomeworkidClassId hc) {

		// TODO Auto-generated method stub
		getHibernateTemplate().save(hc);
	}
	@Override
	public void addHomework(Homework hw) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(hw);
	}

/*	@Override
	public void updateHomework(HwCreatorParam hmArrJson,HttpSession session) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String creator = userDetails.getUsername();
		
		int userid=0;
		if(session.getAttribute("teacherUserId")!=""&&session.getAttribute("teacherUserId")!=null){
			userid=(Integer)session.getAttribute("teacherUserId");
		}
	
        List<Integer> idList=hmArrJson.getIdList();
        String questionid="";

        for(int id:idList){
			questionid+=id+",";
        }
		int teacherid=0;
		Teacher teacher=teacherDao.getTeacherByUserId(userid);
		if(teacher!=null){
			teacherid=teacher.getId();
		}
		Homework hw =hmService.getHomeworkById(Integer.parseInt(hmArrJson.getHomeworkId()));
		hw.setTeacherId(teacherid);
		hw.setCourseId(hmArrJson.getCourseId());
		hw.setHmDesc(hmArrJson.getHwDiscrib());
		hw.setCreateTime(new Timestamp(new Date().getTime()));
		hw.setCreator(creator);
		hw.setQuestionId(questionid);
		hw.setName(hmArrJson.getHmName());
		hw.setTerm(hmArrJson.getTerm());
		hw.setClazzsId(hmArrJson.getClazzsId());
		
		getHibernateTemplate().update(hw);
		
		List<HomeworkidClassId> hwcList=chwService.getHomeworkidClazzidByHomeworkId(Integer.parseInt(hmArrJson.getHomeworkId()));
		for(HomeworkidClassId hwc:hwcList) {
				getHibernateTemplate().delete(hwc);	
		}
		String[] strArrs=hmArrJson.getClazzsId().split(",");
		for(String str:strArrs){
			int str2Int=Integer.parseInt(str);
			int maxid=getMaxId()+1;
			HomeworkidClassId homeworkidClassId=new HomeworkidClassId();
			homeworkidClassId.setClazzid(str2Int);
			homeworkidClassId.setHomeWorkId(maxid);

			addHomeworkidClassid(homeworkidClassId);
		}
		
		
		
	}*/


}
