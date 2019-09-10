package net.huawei.wisdomstudy.dao.inter;

import java.util.List;


import net.huawei.wisdomstudy.domain.HomeWorkStudent;

public interface IHomeworkStudentDao {
	public void addHomeworkStudent(HomeWorkStudent hwst);
	public List<HomeWorkStudent> getHomeworkStudentListByHomeworkId(int homewokid);
	public HomeWorkStudent getHomeworkStudentByHomeworkId(int homewokid,int studentid);
	public void updateHomeworkStudent(HomeWorkStudent hwst);
	public List<HomeWorkStudent> getHomeworkStudentList();
	public List<HomeWorkStudent> getHomeworkStudentByHomeworkIdAndClazzid(int homewokid, int clazzid);
}
