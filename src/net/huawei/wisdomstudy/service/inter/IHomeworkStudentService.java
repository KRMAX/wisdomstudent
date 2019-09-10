package net.huawei.wisdomstudy.service.inter;
import java.util.List;

import net.huawei.wisdomstudy.domain.HomeWorkStudent;

public interface IHomeworkStudentService {
	
	public void addHomeworkStudent(HomeWorkStudent hwst);
	public void updateHomeworkStudent(HomeWorkStudent hwst);
	public List<HomeWorkStudent> getHomeworkStudentListByHomeworkId(int homewokid);
	public HomeWorkStudent getHomeworkStudentByHomeworkId(int homewokid,int studentid);
	public List<HomeWorkStudent> getHomeworkStudentList();
	public List<HomeWorkStudent> getHomeworkStudentByHomeworkIdAndClazzid(int homewokid, int clazz);
}
