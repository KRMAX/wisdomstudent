package net.huawei.wisdomstudy.dao.inter;

import java.util.List;

import net.huawei.wisdomstudy.domain.Clazz;
import net.huawei.wisdomstudy.domain.Student;

public interface IRedisDao {
	public List<Clazz> getClazzList();
	public List<Student> getStudentListByClazzId(int clazzid);
}
