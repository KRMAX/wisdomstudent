package net.huawei.wisdomstudy.dao.inter;

import java.util.List;
import java.util.Map;

import net.huawei.wisdomstudy.controller.domain.SelectCourseResult;
import net.huawei.wisdomstudy.domain.Clazz;
import net.huawei.wisdomstudy.domain.Course;

public interface ICourseSelectingDao {

	/**
	 * 添加选课
	 * @author cexo added on 2018年11月23日
	 * @param scr
	 * @return void
	 */
	public void addCourseSelecting(SelectCourseResult scr) ;
	
	/**
	 * 
	 * @author cexo added on 2019-5-1
	 * @return List<SelectCourseResult>
	 */
	public List<SelectCourseResult> getHasChozenCourse();
	
	/**
	 * 按教师OID返回所有选课记录，即所有该教师当前名下的课程及班级
	 * @author cexo added on 2018-3-13
	 * @param teacherId
	 * @return List<CourseSelecting>
	 */
	//public List<CourseSelecting> getCourseSelectingByTeaId(int teacherId);
	
	/**
	 * 按教师OID返回所有选课记录，即所有该教师当前名下的课程
	 * @author cexo added on 2018-7-4
	 * @param teacherId
	 * @return List<Course>
	 */
	public List<Course> getCourseByTeaId(int teacherId);
	
	/**
	 * 按教师OID、课程OID返回所有班级记录，即该教师指定课程下的所有班级
	 * @author cexo added on 2018-7-4
	 * @param teacherId, courseId
	 * @return List<Clazz>
	 */
	public List<Clazz> getClazzList(int teacherId, int courseId);
	
	/**
	 * 根据课程OID查询选择此课的学生集合
	 * @author cexo added on 2017-1-18
	 * @param courseId 课程OID
	 * @return List<Student>
	 */
	//public List<Student> getStudentsByCourse(int courseId);
	
	/**
	 * 根据学号添加试卷
	 * @author cexo added on 2017-1-18
	 * @param studentId 学生OID
	 * @param courseId 课程OID
	 * @param address 试卷地址
	 * @return
	 */
	//public void addExampaperByStudent(int studentId, int courseId, String address);
}
