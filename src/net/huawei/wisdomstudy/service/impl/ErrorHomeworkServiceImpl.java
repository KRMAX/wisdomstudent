package net.huawei.wisdomstudy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.dao.inter.IErrorHomeworkDao;
import net.huawei.wisdomstudy.domain.ErrorHomeWork;
import net.huawei.wisdomstudy.service.inter.IErrorHomeworkService;
@Service
@Transactional
public class ErrorHomeworkServiceImpl implements IErrorHomeworkService {
	@Autowired
	IErrorHomeworkDao errorHomeworkDao;
	@Override
	public void addErrorHomeworkService(ErrorHomeWork errorHomework) {
		errorHomeworkDao.addErrorHomeworkService(errorHomework);
	}
	@Override
	public void deleteErrorHomeworkService(ErrorHomeWork errorHomework) {
		errorHomeworkDao.deleteErrorHomeworkService(errorHomework);
		
	}
	@Override
	public List<ErrorHomeWork> getErrorHomeworkList(int homeworkid, int studentid) {
		List<ErrorHomeWork> errorHomeworkList=errorHomeworkDao.getErrorHomeworkList(homeworkid, studentid);
		return errorHomeworkList;
	}

}
