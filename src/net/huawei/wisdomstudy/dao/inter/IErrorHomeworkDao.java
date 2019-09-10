package net.huawei.wisdomstudy.dao.inter;

import java.util.List;

import net.huawei.wisdomstudy.domain.ErrorHomeWork;

public interface IErrorHomeworkDao {
	public void addErrorHomeworkService(ErrorHomeWork errorHomework);
	public void deleteErrorHomeworkService(ErrorHomeWork errorHomework);
	public List<ErrorHomeWork> getErrorHomeworkList(int homeworkid, int studentid);
}
