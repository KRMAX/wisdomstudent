package net.huawei.wisdomstudy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.dao.inter.IHomeworkidClazzidDao;
import net.huawei.wisdomstudy.domain.HomeworkidClassId;
import net.huawei.wisdomstudy.service.inter.IClazzidHomeworkidService;
@Service
@Transactional
public class ClazzidHomeworkidServiceImpl implements IClazzidHomeworkidService{
	@Autowired
	IHomeworkidClazzidDao hwcDao;
	public List<HomeworkidClassId> getHomeworkidClazzidByHomeworkId(int homeworkid){
		return hwcDao.getHomeworkidClazzidByHomeworkId(homeworkid);
		
	};
}
