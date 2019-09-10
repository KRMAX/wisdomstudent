package net.huawei.wisdomstudy.dao.inter;

import java.util.List;

import net.huawei.wisdomstudy.domain.Teacher;

public interface ITeacherDao {

	/**
	 * 根据教师工号查找Teacher对象
	 * @param empId
	 * @return Object Teacher
	 */
	public Teacher getTeacherByEmpId(String empId);
	
	/**
	 * 根据用户OID查找Teacher对象
	 * @param userId
	 * @return Object Teacher
	 */
	public Teacher getTeacherByUserId(int userId);
	
	/**
	 * 根据专业返回Teacher对象集
	 * @author cexo added on 2019年5月17日
	 * @param major
	 * @return List<Teacher>
	 */
	public List<Teacher> getTeacherList(String major);
	
	
}
