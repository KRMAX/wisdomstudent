package net.huawei.wisdomstudy.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import net.huawei.wisdomstudy.dao.inter.IUserDao;
import net.huawei.wisdomstudy.domain.Role;
import net.huawei.wisdomstudy.domain.Student;
import net.huawei.wisdomstudy.domain.Teacher;
import net.huawei.wisdomstudy.domain.User;

@Repository
public class UserDaoImpl extends HibernateDaoSupport implements IUserDao {
	
	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	/**
	 * 通过用户名获取User对象
	 * 返回true：含有此用户名对象
	 * 返回false：不含此用户名对象
	 */
	@Override
	public User getUserByName(String username) {
		String hql = "from User u where u.username=?";

		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) getHibernateTemplate().find(hql, username);
		if (userList.size() == 0 || userList.size() > 1) {
			System.out.println("当前用户登录的数量不等于1");
			return null;
		} else {
			User currentUser = userList.get(0);
			return currentUser;
		}
	}

	@Override
	public Role getRoleByUserId(int userId) {
		String hql = "select r from User u, Role r where u.id=? and u.role=r.id";
		
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>) getHibernateTemplate().find(hql, userId);
		if(roleList.size() == 0){
			System.out.println("此用户没有角色分配！");
			return null;
		}else{
			return roleList.get(0);
		}
		
	}

	/**
	 * 创建登录账户
	 * @author cexo on 2016-4-15
	 * @return
	 */
	@Override
	public void createUser(int tid, String username,String password,String realName, int role) {
			
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRealname(realName);
		user.setRole(role);
		getHibernateTemplate().save(user);
		
		String hql = "";
		if(role == 2){
			hql = "from Teacher t where t.id=?";
			
			@SuppressWarnings("unchecked")
			List<Teacher> teacherList = (List<Teacher>) getHibernateTemplate().find(hql,tid);
			Teacher teacher = teacherList.get(0);
			teacher.setUser(user);
			getHibernateTemplate().save(teacher);
		}
			
		if(role == 3){
			hql = "from Student s where s.id=?";
			
			@SuppressWarnings("unchecked")
			List<Student> teacherList = (List<Student>) getHibernateTemplate().find(hql,tid);
			Student teacher = teacherList.get(0);
			teacher.setUser(user);
			getHibernateTemplate().save(teacher);
		}
	}

}
