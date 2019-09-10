package net.huawei.wisdomstudy.dao.test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import net.huawei.wisdomstudy.dao.inter.IKnowledgePointDao;
import net.huawei.wisdomstudy.domain.KnowledgePoint;

@ContextConfiguration(locations = { "classpath:spring-hibernate.xml" }) // 用于指定配置文件所在的位置
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@Rollback(true)
@Transactional
public class KnowledgePointDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Resource // 注入Spring容器Bean对象，注意与@Autowired区别
	private IKnowledgePointDao kpDao;

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
	public void addKnowledgePointTest(){
		
		String kpDescribe="测试知识点描述1";
		int chapterId = 1;
		kpDao.addKnowledgePoint(kpDescribe, chapterId);
		
	}
	
	@Test
	public void getgetKnowledgePointListTest(){

		Map<String, Object> map = kpDao.getKnowledgePointList(1, 0,2);
		@SuppressWarnings("unchecked")
		List<KnowledgePoint> kpList = (List<KnowledgePoint>) map.get("rows");
		int total = (int) map.get("total");
		
		assertTrue(total == 4);
		assertTrue(kpList.size() == 2);
		
		for(Iterator<KnowledgePoint> it = kpList.iterator();it.hasNext();){
			KnowledgePoint i = it.next();
			System.out.println(i.getChapter().getChapterName()+ ":" + i.getKpDescribe());
		}
	}
	
	//@Test
	public void getKnowledgePointListsTest(){
		List<KnowledgePoint> kp = kpDao.getKnowledgePointList(1);
		for(Iterator<KnowledgePoint> it = kp.iterator(); it.hasNext();){
			KnowledgePoint i = it.next();
			System.out.println(i.getChapter().getChapterName() + ":" + i.getKpDescribe());
		}
	}
	
	//@Test
	public void KnowledgePointCountTest(){
		System.out.println(kpDao.knowledgePointCount(1));
	}
}
