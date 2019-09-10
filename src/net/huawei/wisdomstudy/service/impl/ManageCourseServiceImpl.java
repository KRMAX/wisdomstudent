package net.huawei.wisdomstudy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.controller.domain.Combobox;
import net.huawei.wisdomstudy.controller.domain.SelectCourseResult;
import net.huawei.wisdomstudy.dao.inter.IClazzDao;
import net.huawei.wisdomstudy.dao.inter.ICourseDao;
import net.huawei.wisdomstudy.dao.inter.ICourseSelectingDao;
import net.huawei.wisdomstudy.dao.inter.ITeacherDao;
import net.huawei.wisdomstudy.domain.Clazz;
import net.huawei.wisdomstudy.domain.Course;
import net.huawei.wisdomstudy.domain.Teacher;
import net.huawei.wisdomstudy.service.inter.IManageCourseService;

@Service
@Transactional
public class ManageCourseServiceImpl implements IManageCourseService {
	private Log logger = LogFactory.getLog(this.getClass());
	/** 日志对象 */
	
	@Autowired
	ITeacherDao teacherDao;
	
	@Autowired
	IClazzDao clazzDao;
	
	@Autowired
	ICourseDao courseDao;
	
	@Autowired
	ICourseSelectingDao csDao;
	
	/**
	 * @author cexo
	 * added on 2019-5-18
	 * 获取教师列表
	 */
	@Override
	public List<Teacher> getTeacherList(String major) {
		
		return teacherDao.getTeacherList(major);
	}

	/**
	 * 根据学期获取课程列表
	 * @author cexo added on 2019年5月1日
	 * @param termId
	 * @return List<Course>
	 */
	@Override
	public List<Course> getCourseList(int termId){
		
		return courseDao.getCourseList(termId);
	}

	@Override
	public void addCourseSelecting(List<SelectCourseResult> selectCourseResultList) {
		// TODO Auto-generated method stub
		for(SelectCourseResult scr : selectCourseResultList){
			csDao.addCourseSelecting(scr);
		}
		
	}

	@Override
	public List<SelectCourseResult> getHasChozenCourse() {
		
		return csDao.getHasChozenCourse();
	}

	/**
	 * 按教师OID返回所有选课记录，即所有该教师当前名下的课程-填充下拉框
	 * @author cexo added on 2018-7-4
	 * @param teacherId
	 * @return List<Combobox>
	 */
	@Override
	public List<Combobox> getCourseByTeaId(int teacherId) {

		List<Combobox> comboboxList = new ArrayList<Combobox>();
		List<Course> list = csDao.getCourseByTeaId(teacherId);
		for(Course c : list){
			Combobox combobox = new Combobox();
			combobox.setId(c.getId());
			combobox.setText(c.getName());
			comboboxList.add(combobox);
		}
		return comboboxList;
	}

	/**
	 * 按教师OID、课程OID返回所有班级记录，即该教师指定课程下的所有班级-填充下拉框
	 * @author cexo added on 2018-7-4
	 * @param teacherId, courseId
	 * @return List<Combobox>
	 */
	@Override
	public List<Combobox> getClazzList(int teacherId, int courseId) {
		
		List<Combobox> comboboxList = new ArrayList<Combobox>();
		List<Clazz> list = csDao.getClazzList(teacherId, courseId);
		for(Clazz c : list){
			Combobox combobox = new Combobox();
			combobox.setId(c.getId());
			combobox.setText(c.getClazzName());
			comboboxList.add(combobox);
		}
		return comboboxList;
	}

	@Override
	public List<Combobox> getCourseByClazzId(int clazzid) {
		List<Combobox> comboboxList=courseDao.getCourseByClazzId(clazzid);
		return comboboxList;
	}
}
