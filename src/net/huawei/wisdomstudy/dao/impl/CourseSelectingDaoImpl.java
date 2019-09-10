package net.huawei.wisdomstudy.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import net.huawei.wisdomstudy.controller.domain.SelectCourseResult;
import net.huawei.wisdomstudy.dao.inter.ICourseSelectingDao;
import net.huawei.wisdomstudy.domain.Clazz;
import net.huawei.wisdomstudy.domain.Course;
import net.huawei.wisdomstudy.domain.CourseSelecting;
import net.huawei.wisdomstudy.domain.Teacher;

/**
 * @author cexo
 * added on 2019年4月29日
 */
@Repository
public class CourseSelectingDaoImpl extends HibernateDaoSupport implements ICourseSelectingDao {

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}
	
	/**
	 * 添加选课记录
	 * @author cexo
	 * added on 2019-4-29
	 */
	@Override
	public void addCourseSelecting(SelectCourseResult scr) {
		// get(Class entityClass, Serializable id)：根据主键加载特定持久化类的实例
		Clazz clazz = new Clazz();
		clazz.setId(scr.getClazzId());
		Course course = new Course();
		course.setId(scr.getCourseId());
		Teacher teacher = new Teacher();
		teacher.setId(scr.getTeacherId());			
				
		CourseSelecting cs = new CourseSelecting();
		cs.setCourse(course);
		cs.setClazz(clazz);
		cs.setTeacher(teacher);

		//应先检查同一class及课程下是否已分配教师，有且教师id发生变化则更新这条，没有则保存
		String hql = "from CourseSelecting cs where cs.clazz.id = ? and cs.course.id = ?";
				
		@SuppressWarnings("unchecked")
		List<CourseSelecting> csList = (List<CourseSelecting>) getHibernateTemplate().find(hql,scr.getClazzId(), scr.getCourseId());
		if(csList.size() == 0){
			getHibernateTemplate().save(cs);
		}else{
			CourseSelecting cs1 = csList.get(0);
			if(cs1.getTeacher().getId() != scr.getTeacherId()){
				cs1.setTeacher(teacher);
				getHibernateTemplate().update(cs1);
			}	
		}
		
	}

	@Override
	public List<SelectCourseResult> getHasChozenCourse() {
		
		String hql = "from CourseSelecting cs";
		
		List<SelectCourseResult> scrList = new ArrayList<SelectCourseResult>();
		@SuppressWarnings("unchecked")
		List<CourseSelecting> csList = (List<CourseSelecting>) getHibernateTemplate().find(hql);
		if(!csList.isEmpty()){
			Iterator<CourseSelecting> it = csList.iterator();
			while(it.hasNext()){
				CourseSelecting cs = it.next();
				SelectCourseResult scr = new SelectCourseResult();
				scr.setClazzId(cs.getClazz().getId());
				scr.setCourseId(cs.getCourse().getId());
				scr.setTeacherId(cs.getTeacher().getId());
				scr.setTeacherName(cs.getTeacher().getName());
				scrList.add(scr);
			}
		}
		return scrList;

	}

	/**
	 * 按教师OID返回所有选课记录，即所有该教师当前名下的课程及班级
	 * @author cexo added on 2018-3-13
	 * @param teacherId
	 * @return List<CourseSelecting>
	 *//*
	@Override
	public List<CourseSelecting> getCourseSelectingByTeaId(int teacherId) {

		String hql = "from CourseSelecting cs where cs.teacher.id=?";
		
		@SuppressWarnings("unchecked")
		List<CourseSelecting> csList = getHibernateTemplate().find(hql,teacherId);
		
		if(!csList.isEmpty()){
			Iterator<CourseSelecting> it = csList.iterator();
			while(it.hasNext()){
				CourseSelecting cs = it.next();
				
				*//**
				 * 因延迟加载，hibernate的session结束后teacherList中每个teacher对象的user对象未从数据库中查出，
				 * 导致游离状态下的teacher对象代理不能初始化，抛出异常。
				 * 取消teacher类中对User类的oneToOne映射可以解决，或者变成立即加载。
				 * 还可以用OpenSessionInView解决
				 *//*
				getHibernateTemplate().initialize(cs.getClazz());
				getHibernateTemplate().initialize(cs.getCourse());
			}
		}
		return csList;
	}

	*//**
	 * 按教师OID返回所有选课记录，即所有该教师当前名下的课程
	 * @author cexo added on 2018-7-4
	 * @param teacherId
	 * @return List<Course>
	 */
	@Override
	public List<Course> getCourseByTeaId(int teacherId) {

		String hql = "from CourseSelecting cs where cs.teacher.id=? group by cs.course";
		List<Course> courseList = new ArrayList<Course>();
		
		@SuppressWarnings("unchecked")
		List<CourseSelecting> csList = (List<CourseSelecting>) getHibernateTemplate().find(hql,teacherId);
		
		if(csList !=null && !csList.isEmpty()){
			Iterator<CourseSelecting> it = csList.iterator();
			while(it.hasNext()){
				courseList.add(it.next().getCourse());
				
			}
		}
		return courseList;
	}

	/**
	 * 按教师OID、课程OID返回所有班级记录，即该教师指定课程下的所有班级
	 * @author cexo added on 2018-7-4
	 * @param teacherId, courseId
	 * @return List<Clazz>
	 */
	public List<Clazz> getClazzList(int teacherId, int courseId){
		String hql = "from CourseSelecting cs where cs.teacher.id=? and cs.course.id =?";
		List<Clazz> clazzList = new ArrayList<Clazz>();
		@SuppressWarnings("unchecked")
		List<CourseSelecting> csList = (List<CourseSelecting>) getHibernateTemplate().find(hql,teacherId,courseId);
		if(csList !=null && !csList.isEmpty()){
			Iterator<CourseSelecting> it = csList.iterator();
			while(it.hasNext()){
				clazzList.add(it.next().getClazz());	
			}
		}
		return clazzList;
	}
	
	/**
	 * 根据课程OID查询选择此课的学生集合
	 * 
	 * @author cexo added on 2017-1-18
	 * @param courseId 课程OID
	 * @return List<Student>
	 */
	/*@Override
	public List<Student> getStudentsByCourse(int courseId) {

		String hql = "from CourseSelecting cs where cs.course.id = ?";
		*//**
		 * 上面一句等价于下面两句 String hql =
		 * "from CourseSelecting cs where cs.course = ?"; Course course =
		 * getHibernateTemplate().get(Course.class, course);
		 **//*

		@SuppressWarnings("unchecked")
		List<CourseSelecting> csList = getHibernateTemplate().find(hql, courseId);
		List<Student> studentList = new ArrayList<Student>();
		if (null != csList) {
			Iterator<CourseSelecting> it = csList.iterator();
			while (it.hasNext()) {
				Student st = it.next().getStudent();
				studentList.add(st);
			}
		}
		return studentList;

	}*/

	/**
	 * 根据学号添加试卷
	 * 
	 * @author cexo added on 2017-1-18
	 * @param studentId 学生OID
	 * @param courseId 课程OID
	 * @param address 试卷地址
	 * @return
	 */
	/*@Override
	public void addExampaperByStudent(int studentId, int courseId, String address) {

		String hql = "from CourseSelecting cs where cs.student.id = ? and cs.course.id = ?";
		@SuppressWarnings("unchecked")
		List<CourseSelecting> csList = getHibernateTemplate().find(hql, studentId, courseId);
		if(null != csList){
			csList.get(0).setExamPaper(address);
			getHibernateTemplate().update(csList.get(0));
		}
		
	}*/

}
