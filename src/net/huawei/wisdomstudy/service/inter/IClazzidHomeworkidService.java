package net.huawei.wisdomstudy.service.inter;

import java.util.List;

import net.huawei.wisdomstudy.domain.HomeworkidClassId;

public interface IClazzidHomeworkidService {
	public List<HomeworkidClassId> getHomeworkidClazzidByHomeworkId(int homeworkid);
}
