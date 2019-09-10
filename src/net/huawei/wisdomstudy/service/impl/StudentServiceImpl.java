package net.huawei.wisdomstudy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.dao.inter.IStudentDao;
import net.huawei.wisdomstudy.domain.Student;
import net.huawei.wisdomstudy.service.inter.IStudentService;
@Service
@Transactional
public class StudentServiceImpl extends HibernateDaoSupport  implements IStudentService{
     @Autowired
     IStudentDao stDao;
	 @Resource
	    public void setSessionFacotry(SessionFactory sessionFacotry) {
	        super.setSessionFactory(sessionFacotry);
	    }
	/**
	 * 根据学号查找Student对象
	 * @param userId
	 * @return Object Student
	 */
	@Override
	public Student getStudentByUserid(int userId) {

		String hql = "from Student s where s.user.id=?";
		@SuppressWarnings("unchecked")
		List<Student> studentList = (List<Student>) getHibernateTemplate().find(hql,userId);
		Student student=new Student();
		
			
			if(!studentList.isEmpty()){
				student=studentList.get(0);
			}
			return student;
	
	
		
	}
	@Override
	public List<Student> getStudentListByClazzId(int clazzid) {
		


			return stDao.getStudentListByClazzId(clazzid);
	
	}
	@Override
	public Student getStudentById(int id) {
		return stDao.getStudentById(id);
	}

}
