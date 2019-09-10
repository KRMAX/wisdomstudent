package net.huawei.wisdomstudy.service.inter;

import java.util.List;

import net.huawei.wisdomstudy.domain.HomeworkidClassId;

public interface IHomeworkClazzService {

	  public List<HomeworkidClassId> getHomeworkidClazzidByClazzId(int clazzid);
	    public List<HomeworkidClassId> getHomeworkidClazzidByHomeworkId(int homeworkid);
	    public List<HomeworkidClassId> getHomeworkClazz(int clazzid,int homeworkid, int firstResult, int maxResults);
}
