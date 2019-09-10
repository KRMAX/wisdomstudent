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

import net.huawei.wisdomstudy.dao.inter.ICourseDao;
import net.huawei.wisdomstudy.domain.Course;
import net.huawei.wisdomstudy.domain.Field;
import net.huawei.wisdomstudy.domain.Term;

@ContextConfiguration(locations = { "classpath:spring-hibernate.xml" }) // 用于指定配置文件所在的位置
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@Rollback(true)
@Transactional
public class CourseDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Resource // 注入Spring容器Bean对象，注意与@Autowired区别
	private ICourseDao courseDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		System.out.println("***************class ChapterDaoImplTest begin[]***************");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		System.out.println("***************class ChapterDaoImplTest  end[]***************");
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	//@Test
	public void testAddCourse(){
		Course course = new Course();
		course.setCourseNo("158305949");
		course.setName("大学数学3-3(线代3)");
		course.setCredit("4");
		course.setElective(true);
		Field field = new Field();
		field.setId(2);
		course.setField(field);
		Term term = new Term();
		term.setId(2);
		course.setTerm(term);
		courseDao.addCourse(course);
	}
	
	@Test
	public void testGetCourseList(){
		
		List<Course> courseList = courseDao.getCourseList(3);
		for(Iterator<Course> it = courseList.iterator();it.hasNext();){
			Course course = it.next();
			System.out.println(course.getName()+";" + course.getField().getName());
			/*for(Iterator<CourseSelecting> ic = course.getCourseSelectingList().iterator();ic.hasNext();){
				System.out.println(ic.next().getClazz().getClazzName());
			}*/
		}
	}
	
	//@Test
	public void testDeleteCourse(){
		courseDao.deleteCourse(1);
	}
}
