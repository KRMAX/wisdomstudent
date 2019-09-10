package net.huawei.wisdomstudy.service.inter;

import java.util.List;

import net.huawei.wisdomstudy.domain.Student;

public interface IStudentService {

	public Student getStudentByUserid(int userId);
	public List<Student> getStudentListByClazzId(int clazzid);
	public Student getStudentById(int id);
}
