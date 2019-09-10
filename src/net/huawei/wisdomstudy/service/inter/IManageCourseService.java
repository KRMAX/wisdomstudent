package net.huawei.wisdomstudy.service.inter;

import java.util.List;

import net.huawei.wisdomstudy.controller.domain.Combobox;
import net.huawei.wisdomstudy.controller.domain.SelectCourseResult;
import net.huawei.wisdomstudy.domain.Course;
import net.huawei.wisdomstudy.domain.Teacher;

public interface IManageCourseService {
	/**
	 * @author cexo
	 * added on 2019-5-18
	 * 获取教师列表
	 */
	public List<Teacher> getTeacherList(String major);
	
	/**
	 * 根据学期获取课程列表
	 * @author cexo added on 2019年5月1日
	 * @param termId
	 * @return List<Course>
	 */
	public List<Course> getCourseList(int termId);
	
	public void addCourseSelecting(List<SelectCourseResult> selectCourseResultList);
	
	/**
	 * 返回所有选课记录
	 * @author cexo added on 2019-5-1
	 * @return List<SelectCourseResult>
	 */
	public List<SelectCourseResult> getHasChozenCourse();
	
	/**
	 * 按教师OID返回所有选课记录，即所有该教师当前名下的课程-填充下拉框
	 * @author cexo added on 2018-7-4
	 * @param teacherId
	 * @return List<Combobox>
	 */
	public List<Combobox> getCourseByTeaId(int teacherId);
	/**
	 * 按教师OID返回所有选课记录，即所有该教师当前名下的课程-填充下拉框
	 * @author cexo added on 2018-7-4
	 * @param teacherId
	 * @return List<Combobox>
	 */
	public List<Combobox> getCourseByClazzId(int clazzid);
	
	/**
	 * 按教师OID、课程OID返回所有班级记录，即该教师指定课程下的所有班级-填充下拉框
	 * @author cexo added on 2018-7-4
	 * @param teacherId, courseId
	 * @return List<Combobox>
	 */
	public List<Combobox> getClazzList(int teacherId, int courseId);
	
}
