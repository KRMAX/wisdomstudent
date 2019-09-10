package net.huawei.wisdomstudy.service.test;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.dao.inter.IClazzDao;
import net.huawei.wisdomstudy.domain.Clazz;
import net.huawei.wisdomstudy.service.inter.IClazzService;

@ContextConfiguration(locations = { "classpath:spring-hibernate.xml" }) // 用于指定配置文件所在的位置
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@Rollback(true)
@Transactional
public class ClazzServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	
	
	@Resource // 注入Spring容器Bean对象，注意与@Autowired区别
	private IClazzService clazzService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		System.out.println("***************class ClazzServiceTest begin[]***************");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		System.out.println("***************class ClazzServiceTest  end[]***************");
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}
	
	@Test
	public void importClazzExcelTest() {
		String saveDirectoryPath = "D:\\\\apache-tomcat-8.0.32\\webapps\\upload\\clazz\\clazz.xls";
		FileSystemResource resource = new FileSystemResource(saveDirectoryPath.replaceAll("\\\\", "/"));
        File file = resource.getFile();
        System.out.println(file.getTotalSpace());
		Map<String, Object>map = clazzService.importClazzExcel(file);
		System.out.println("map = " + map.toString());
	}
	
	//@Test
	public void addClazzTest() {
		Clazz clazz = new Clazz();
		clazz.setClazzName("第一个班级");
		clazz.setAdmissionYear("2015");
		clazz.setMajor("水水");
		clazz.setDepartment("谁");
		clazzService.addClazz(clazz);
	}
}
