package net.huawei.wisdomstudy.dao.inter;

import java.util.List;

import net.huawei.wisdomstudy.controller.domain.Combobox;
import net.huawei.wisdomstudy.domain.Course;

public interface ICourseDao {

	/**
	 * 添加课程
	 * @author cexo added on 2018年11月24日
	 * @param course
	 * @return void
	 */
	public void addCourse(Course course);
	
	/**
	 * 根据学期获取课程列表
	 * @author cexo added on 2018年11月24日
	 * @param termId
	 * @return List<Course>
	 */
	public List<Course> getCourseList(int termId);
	
	/**
	 * 删除指定OID的课程
	 * author cexo added on 2017-12-2
	 * @param OID
	 */
	public void deleteCourse(int id);
	
	/**
	 * 通过学生id，找到其对应的课程
	 * @param studentids
	 * @return
	 */
	public List<Combobox> getCourseByClazzId(int clazzid);
}
