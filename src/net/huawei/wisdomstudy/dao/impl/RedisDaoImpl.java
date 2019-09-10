package net.huawei.wisdomstudy.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import net.huawei.wisdomstudy.dao.inter.IClazzDao;
import net.huawei.wisdomstudy.dao.inter.IRedisDao;
import net.huawei.wisdomstudy.dao.inter.IStudentDao;
import net.huawei.wisdomstudy.domain.Clazz;
import net.huawei.wisdomstudy.domain.Student;
@Repository
public class RedisDaoImpl extends HibernateDaoSupport implements IRedisDao {
	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}
	
	@Autowired
	private IClazzDao clazzDao;

	@Autowired
	private IStudentDao stDao;

	
	@Override
	public List<Clazz> getClazzList() {
		// TODO Auto-generated method stub
		return clazzDao.getClazzList();
	}

	@Override
	public List<Student> getStudentListByClazzId(int clazzid) {
		// TODO Auto-generated method stub
		return stDao.getStudentListByClazzId(clazzid);
	}

}
