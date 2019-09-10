package net.huawei.wisdomstudy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.dao.inter.ITeacherDao;
import net.huawei.wisdomstudy.domain.Teacher;
import net.huawei.wisdomstudy.service.inter.ITeacherService;
@Service
@Transactional
public class TeacherServiceImpl implements ITeacherService{
	@Autowired
	ITeacherDao teacherDao;
	@Override
	public Teacher getTeacherByUserId(int userId) {
		return teacherDao.getTeacherByUserId(userId);
	}
	@Override
	public Teacher getTeacherByEmpId(String empId) {
		return teacherDao.getTeacherByEmpId(empId);
	}

	

}
