package net.huawei.wisdomstudy.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.cons.CommonConstant;
import net.huawei.wisdomstudy.dao.inter.IStudentDao;
import net.huawei.wisdomstudy.domain.Clazz;
import net.huawei.wisdomstudy.domain.Student;
import net.huawei.wisdomstudy.domain.User;
import net.huawei.wisdomstudy.util.StandardPasswordEncoderForSha1;
@Transactional(readOnly=true)
@Repository
public class StudentDaoImpl extends HibernateDaoSupport implements IStudentDao {

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	/**
	 * 根据学号查找Student对象
	 * @param studentNo
	 * @return Object Student
	 */
	@Override
	public Student getStudentByStudentNo(String studentNo) {
		String hql = "from Student s where s.studentNo like ?";
		@SuppressWarnings("unchecked")
		List<Student> studentList = (List<Student>) getHibernateTemplate().find(hql,"%"+studentNo+"%");
		if(studentList.isEmpty()){
			return null;
		}else{
			return studentList.get(0);
		}
	}



	@Override
	public void addStudent(Student student, int classId) {

		Clazz clazz = new Clazz();
		clazz.setId(classId);
		student.setClazz(clazz);
		
		User user = new User();
		user.setUsername(student.getStudentNo());	
		//加盐
		String sh1Password = CommonConstant.INITIAL_PASSWORD + "{" + student.getStudentNo() + "}";
		PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
		String result = passwordEncoder.encode(sh1Password);
		user.setPassword(result);
		user.setRealname(student.getName());
		user.setRole(CommonConstant.USER_ROLE_STUDENT);
		student.setUser(user);
		
		getHibernateTemplate().save(student);
	}
	@Override
	public List<Student> getStudentListByClazzId(int clazzid) {
		
		String hql = "from Student s where s.clazz.id=?";
		@SuppressWarnings("unchecked")
		List<Student> studentList = (List<Student>) getHibernateTemplate().find(hql,clazzid);

			return studentList;
	
	}

	@Override
	public Student getStudentById(int id) {
		Student st=getHibernateTemplate().get(Student.class, id);
		return st;
	}


}
