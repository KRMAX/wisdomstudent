package net.huawei.wisdomstudy.service.inter;

import java.util.List;

import net.huawei.wisdomstudy.domain.ErrorHomeWork;

public interface IErrorHomeworkService {
	public void addErrorHomeworkService(ErrorHomeWork errorHomework);
	public void deleteErrorHomeworkService(ErrorHomeWork errorHomework);
	public List<ErrorHomeWork> getErrorHomeworkList(int homeworkid,int studentid);
}
