package net.huawei.wisdomstudy.dao.test;


import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.controller.domain.SelectCourseResult;
import net.huawei.wisdomstudy.dao.inter.ICourseSelectingDao;
import net.huawei.wisdomstudy.domain.Course;

@ContextConfiguration(locations = { "classpath:spring-hibernate.xml" }) // 用于指定配置文件所在的位置
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@Rollback(false)
@Transactional
public class CourseSelectingDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Resource // 注入Spring容器Bean对象，注意与@Autowired区别
	private ICourseSelectingDao csDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		System.out.println("***************class CourseSelectingDaoTest begin[]***************");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		System.out.println("***************class CourseSelectingDaoTest  end[]***************");
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}
	
	//@Test
	public void addCourseSelectingByClazzTest(){ 
		SelectCourseResult scr = new SelectCourseResult();
		scr.setTeacherId(2);
		scr.setClazzId(1);
		scr.setCourseId(2);
		csDao.addCourseSelecting(scr);
		
	}
	
	//@Test
	public void getHasChozenCourseTest(){ 
		
		List<SelectCourseResult> scrList = csDao.getHasChozenCourse();
		Iterator<SelectCourseResult> it = scrList.iterator();
		while(it.hasNext()){
			System.out.println("----" + it.next() + "----");
		}

	}
	/*
	//@Test
	public void getCourseSelectingByTeaIdTest(){
		List<CourseSelecting> csList = csDao.getCourseSelectingByTeaId(1);
		if(!csList.isEmpty()){
			Iterator<CourseSelecting> it = csList.iterator();
			while(it.hasNext()){
				CourseSelecting cs = it.next();
				System.out.println(cs.getTeacher().getName()+";" +
									cs.getClazz().getClazzName()+";" + 
									cs.getCourse().getName());
			}
		}
	}
	*/
	@Test
	public void getCourseByTeaIdTest(){
		List<Course> csList = csDao.getCourseByTeaId(1);
		System.out.println(csList.size());
		if(!csList.isEmpty()){
			Iterator<Course> it = csList.iterator();
			while(it.hasNext()){
				Course cs = it.next();
				System.out.println(cs.getName());
			}
		}
	}
	//@Test
	/*public void testGetStudents(){
		
		List<Student>studentList = csDao.getStudentsByCourse(4);
		Iterator<Student> it = studentList.iterator();
		while(it.hasNext()){
			String sno = it.next().getStudentNo();
			System.out.println("sno = "+sno);
		}
	}*/
	
	
	/*@Test
	public void testAddExampaperByStudent(){
			
		csDao.addExampaperByStudent(1, 1, "dfw/sdfi/sdfi");
		
	}*/
}
