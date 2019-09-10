package net.huawei.wisdomstudy.dao.test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mchange.util.AssertException;

import net.huawei.wisdomstudy.controller.domain.Message;
import net.huawei.wisdomstudy.dao.inter.IClazzDao;
import net.huawei.wisdomstudy.domain.Clazz;

@ContextConfiguration(locations = { "classpath:spring-hibernate.xml" }) // 用于指定配置文件所在的位置
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@Rollback(true)
@Transactional
public class ClazzDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Resource // 注入Spring容器Bean对象，注意与@Autowired区别
	private IClazzDao clazzDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		System.out.println("***************class ClazzDaoTest begin[]***************");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		System.out.println("***************class ClazzDaoTest  end[]***************");
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}
	
	//@Test
	public void addClazzTest(){ 

		Clazz clazz = new Clazz();
		clazz.setClazzName("15机械1班");
		clazz.setMajor("机械");
		clazz.setDepartment("信息系");
		clazz.setAdmissionYear("2015");
		try {
			clazzDao.addClazz(clazz);
		}catch(DataIntegrityViolationException e) {
			System.out.println(e.getRootCause().getMessage());
			System.out.println("---");
		}
	}
	
	//@Test
	public void updateClazzTest(){
		Clazz clazz = clazzDao.getClazz(1);
		clazz.setClazzName("电气1711");
		
		clazzDao.updateClazz(clazz);
		assertTrue(clazzDao.getClazz(1).getClazzName().equals("电气1711"));	
	}
	/*
	@Test
	public void deleteClazzTest(){
		clazzDao.deleteClazz(1);
	}
	*/
	//@Test
	public void getClazzesTest(){
		Map<String ,Object> clazzMap = clazzDao.getClazzes(0, 10);
		System.out.println(clazzMap.get("total"));
		@SuppressWarnings("unchecked")
		List<Clazz> clazzList = (List<Clazz>) clazzMap.get("rows");
		
		for(Iterator<Clazz> it = clazzList.iterator();it.hasNext();){
			System.out.println(it.next().getClazzName());
		}

		List<Clazz> clazzList1 = clazzDao.getClazzes(2017);
		if(!clazzList1.isEmpty()) {
			for(Iterator<Clazz> it = clazzList1.iterator();it.hasNext();){
				System.out.println(it.next().getClazzName());
			}
		}
	}
	
	//@Test
	public void getClazzesByClazzParamTest() {
		Clazz cp = new Clazz();
/*		cp.setFirstResult(-1);
		cp.setMaxResults(-1);*/
		//cp.setClazzName("人力");
		//cp.setAdmissionYear(2017);
		//cp.setDepartment("管理系");
		cp.setMajor("电气");
		Map<String, Object> clazzMap = clazzDao.getClazzes(cp, 0, 10);
		@SuppressWarnings("unchecked")
		List<Clazz> clazzList = (List<Clazz>) clazzMap.get("rows");
		System.out.println(clazzMap.get("total"));
		for(Iterator<Clazz> it = clazzList.iterator();it.hasNext();){
			System.out.println(it.next().getClazzName());
		}

	}
	
	@Test
	public void getMajorListTest(){
		/*List<String> clazzList = clazzDao.getMajorList();
		for(Iterator<String> it = clazzList.iterator();it.hasNext();){
			System.out.println(it.next());
		}
		clazzList = clazzDao.getMajorList("管理系");
		for(String it : clazzList) {
			System.out.println(it);
		}*/
		List<Clazz> clazzList1 = clazzDao.getClazzList("信息与控制工程系", "2019");
		for(Iterator<Clazz> it = clazzList1.iterator();it.hasNext();){
			System.out.println(it.next().getClazzName());
		}
	}
	/*
	//@Test
	public void getClazzesByInstituteTest(){
		Map<String, Object> map  = clazzDao.getClazzesByInstitute("国际贸易学院", 10, 10);
		System.out.println(map.get("total"));
		@SuppressWarnings("unchecked")
		List<Clazz> clazzList = (List<Clazz>) map.get("rows");
		for(Iterator<Clazz> it = clazzList.iterator();it.hasNext();){
			System.out.println(it.next().getClazzName());
		}
	}*/
}
