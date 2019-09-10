package net.huawei.wisdomstudy.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import net.huawei.wisdomstudy.dao.inter.ITeacherDao;
import net.huawei.wisdomstudy.domain.Teacher;

@Repository
public class TeacherDaoImpl extends HibernateDaoSupport implements ITeacherDao {
	
	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}


	/**
	 * 通过工号获取Teacher对象
	 * @param empId
	 * @return Teacher Object
	 */
	@Override
	public Teacher getTeacherByEmpId(String empId) {
		
		String hql = "from Teacher t where t.empId=?";
		@SuppressWarnings("unchecked")
		List<Teacher> teacherList = (List<Teacher>) getHibernateTemplate().find(hql,empId);
		if(teacherList.isEmpty()){
			return null;
		}else{
			return teacherList.get(0);
		}
	}

	@Override
	public List<Teacher> getTeacherList(String major) {

		String hql = "from Teacher t where t.major = ?";
		@SuppressWarnings("unchecked")
		List<Teacher> teacherList = (List<Teacher>) getHibernateTemplate().find(hql, major);
		/**
		 * 因延迟加载，hibernate的session结束后teacherList中每个teacher对象的user对象未从数据库中查出，
		 * 导致游离状态下的teacher对象代理不能初始化，抛出异常。
		 * 取消teacher类中对User类的oneToOne映射可以解决，或者变成立即加载。
		 * 还可以用OpenSessionInView解决
		 */
		for(int i=0;i<teacherList.size();i++){
			getHibernateTemplate().initialize(teacherList.get(i).getUser());
		}
		
		return teacherList;
	}

	/**
	 * 根据用户OID查找Teacher对象
	 * @param userId
	 * @return Object Teacher
	 */
	@Override
	public Teacher getTeacherByUserId(int userId) {

		String hql = "from Teacher t where t.user.userId = ?";
		@SuppressWarnings("unchecked")
		List<Teacher> teacherList = (List<Teacher>) getHibernateTemplate().find(hql, userId);
		
		return teacherList.get(0);
	}




}
