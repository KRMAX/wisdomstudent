package net.huawei.wisdomstudy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.dao.inter.IHomeworkidClazzidDao;
import net.huawei.wisdomstudy.domain.HomeworkidClassId;
import net.huawei.wisdomstudy.service.inter.IHomeworkClazzService;
@Service
@Transactional
public class HomeworkClazzServiceImpl implements IHomeworkClazzService {
	@Autowired
	IHomeworkidClazzidDao hwcDao;
	
	
	@Override
	public List<HomeworkidClassId> getHomeworkClazz(int clazzid, int homeworkid, int firstResult, int maxResults) {
		
		return hwcDao.getHomeworkClazz(clazzid, homeworkid,  firstResult,  maxResults);
	}

	@Override
	public List<HomeworkidClassId> getHomeworkidClazzidByClazzId(int clazzid) {
		// TODO Auto-generated method stub
		return hwcDao.getHomeworkidClazzidByClazzId(clazzid);
	}

	@Override
	public List<HomeworkidClassId> getHomeworkidClazzidByHomeworkId(int homeworkid) {
		// TODO Auto-generated method stub
		return hwcDao.getHomeworkidClazzidByHomeworkId(homeworkid);
	}







	
}
