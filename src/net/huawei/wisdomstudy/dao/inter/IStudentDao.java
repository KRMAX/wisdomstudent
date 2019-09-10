package net.huawei.wisdomstudy.dao.inter;

import java.util.List;

import net.huawei.wisdomstudy.domain.Student;

public interface IStudentDao {
	
	/**
	 * 根据学号查找Student对象
	 * @param studentNo
	 * @return Object Student
	 */
	public Student getStudentByStudentNo(String studentNo);
	
	/**
	 * 添加学生
	 * @author cexo added on 2018年11月19日
	 * @param student
	 * @param clazzId
	 * @return void
	 */
	public void addStudent(Student student, int clazzId);
	
	public List<Student> getStudentListByClazzId(int clazzid);
	public Student getStudentById(int id);
}
