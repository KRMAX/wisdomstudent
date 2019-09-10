package net.huawei.wisdomstudy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import net.huawei.wisdomstudy.controller.domain.Combobox;
import net.huawei.wisdomstudy.dao.inter.ICourseDao;
import net.huawei.wisdomstudy.domain.Clazz;
import net.huawei.wisdomstudy.domain.Course;
import net.huawei.wisdomstudy.domain.CourseSelecting;


@Repository
public class CourseDaoImpl  extends HibernateDaoSupport implements ICourseDao {

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}
	
	@Override
	public void addCourse(Course course) {
		
		getHibernateTemplate().save(course);
		
	}

	@Override
	public void deleteCourse(int id) {

		/*String hql = "from Course c where c.id = ?";
		@SuppressWarnings("unchecked")
		List<Course> courseList = getHibernateTemplate().find(hql,id);
		if(!courseList.equals(null) && courseList.size()>0){
			getHibernateTemplate().delete(courseList.get(0));
		}
		*/
		
	}

	@Override
	public List<Course> getCourseList(int termId) {
		String hql = "from Course c where c.term.id= ?";
		
		@SuppressWarnings("unchecked")
		List<Course> courseList = (List<Course>) getHibernateTemplate().find(hql, termId);
		return courseList;
	}

	@Override
	public List<Combobox> getCourseByClazzId(int clazzid) {
		List<Combobox> comboboxList = new ArrayList<Combobox>();
		String hql = "from CourseSelecting cs where cs.clazz.id= ?";
		@SuppressWarnings("unchecked")
		List<CourseSelecting> courseSelectList = (List<CourseSelecting>) getHibernateTemplate().find(hql, clazzid);
		for(CourseSelecting courseSelect:courseSelectList) {
			Course course= getHibernateTemplate().get(Course.class,courseSelect.getCourse().getId());
			Combobox combobox = new Combobox();
			combobox.setId(course.getId());
			combobox.setText(course.getName());
			comboboxList.add(combobox);
		}
		return comboboxList;
	}



	
}
