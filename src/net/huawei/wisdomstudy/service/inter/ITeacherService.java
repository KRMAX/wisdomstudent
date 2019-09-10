package net.huawei.wisdomstudy.service.inter;

import net.huawei.wisdomstudy.domain.Teacher;

public interface ITeacherService {
	
	public Teacher getTeacherByUserId(int userId);
	/**
	 * 根据教师工号查找Teacher对象
	 * @param empId
	 * @return Object Teacher
	 */
	public Teacher getTeacherByEmpId(String empId);
}
