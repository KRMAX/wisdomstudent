package net.huawei.wisdomstudy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.dao.inter.IHomeworkStudentDao;
import net.huawei.wisdomstudy.domain.HomeWorkStudent;
import net.huawei.wisdomstudy.domain.Student;
import net.huawei.wisdomstudy.service.inter.IHomeworkStudentService;
@Service
@Transactional
public class HomeworkStudentServiceImpl implements IHomeworkStudentService{
	@Autowired
	IHomeworkStudentDao hwstDao;
	@Override
	public void addHomeworkStudent(HomeWorkStudent hwst) {
		 hwstDao.addHomeworkStudent(hwst);
	}

	@Override
	public List<HomeWorkStudent> getHomeworkStudentListByHomeworkId(int homewokid) {
		return hwstDao.getHomeworkStudentListByHomeworkId(homewokid);
	}

	@Override
	public HomeWorkStudent getHomeworkStudentByHomeworkId(int homewokid, int studentid) {
		return hwstDao.getHomeworkStudentByHomeworkId(homewokid,studentid);
	}

	@Override
	public void updateHomeworkStudent(HomeWorkStudent hwst) {
		hwstDao.updateHomeworkStudent(hwst);
		
	}

	@Override
	public List<HomeWorkStudent> getHomeworkStudentList() {
		List<HomeWorkStudent> hwstList=hwstDao.getHomeworkStudentList();
		return hwstList;
	}

	@Override
	public List<HomeWorkStudent> getHomeworkStudentByHomeworkIdAndClazzid(int homewokid, int clazzid) {
		
		return hwstDao.getHomeworkStudentByHomeworkIdAndClazzid(homewokid, clazzid);
	}

}
