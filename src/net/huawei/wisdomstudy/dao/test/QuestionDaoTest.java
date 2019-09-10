package net.huawei.wisdomstudy.dao.test;

import java.io.IOException;
import java.util.ArrayList;
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

import net.huawei.wisdomstudy.controller.domain.QuestionQueryResult;
import net.huawei.wisdomstudy.dao.inter.IQuestionDao;
import net.huawei.wisdomstudy.domain.KnowledgePoint;
import net.huawei.wisdomstudy.domain.Question;
import net.huawei.wisdomstudy.domain.QuestionContent;
import net.huawei.wisdomstudy.domain.QuestionType;

@ContextConfiguration(locations = { "classpath:spring-hibernate.xml" }) // 用于指定配置文件所在的位置
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@Rollback(true)
@Transactional
public class QuestionDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Resource // 注入Spring容器Bean对象，注意与@Autowired区别
	private IQuestionDao questionDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		System.out.println("***************class QuestionDaoTest begin[]***************");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		System.out.println("***************class QuestionDaoTest  end[]***************");
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}
	
	
	//@Test
	public void addQuestionTest(){
		
		/**
		 * 
		ObjectMapper mapper = new ObjectMapper();
      	String jsonString = "{\"name\":\"Mahesh\", \"age\":21}";

      //map json to student
      try {
         Student student = mapper.readValue(jsonString, Student.class);
         System.out.println(student);
         mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
         jsonString = mapper.writeValueAsString(student);
         System.out.println(jsonString);

      } catch (JsonParseException e) {
         e.printStackTrace();
      } catch (JsonMappingException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
		 */
		/*Question question = new Question();

		HwCreatorParam hcp = new HwCreatorParam();
		hcp.setCourseId(1);
		hcp.setHmName("作业名称");
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(1);idList.add(3);idList.add(4);
		hcp.setIdList(idList);

		QuestionContent qc = new QuestionContent();
		qc.setTitle("");
		qc.setTitleImg("D:/apache-tomcat-8.0.32/webapps/upload/question/高等数学/第一章 数列和极限/数列定义/2018-5-16-64395.png");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String sss="";
		try {
			sss = objectMapper.writeValueAsString(qc);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sss);
		String json = JSON.toJSONString(hcp, false);
		System.out.println(json);
		question.setQuestionDesc("这是测试题描述信息ssssssssssssssss！");
		question.setContent(sss);
		//question.setQuestionDesc("dddddd");
		question.setAnswer("B;C");

		
		questionDao.addQuestion(question, 1, 1);*/
	}
	
	//@Test
	public void questionCountTest(){
		System.out.println(questionDao.questionCount(1));
	}
	
	//@Test
	public void getQuestionsTest(){

		/*Question question = questionDao.getQuestion(74);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			QuestionContent qc = objectMapper.readValue(question.getContent(), QuestionContent.class);
			question.setQc(qc);
			System.out.println(question.getQc().getTitle()+";;;"+question.getQc().getTitleImg());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(question.getContent());*/
		
	}
	
	@Test
	public void getQuestions1Test(){
		List<QuestionQueryResult> qlist = questionDao.getQuestions(1);
		System.out.println("---"+qlist.get(0).getContent().getTitle()+qlist.get(0).getCreator());
	}
	
	//@Test
		public void getQuestions2Test(){
			List<Question> qlist = questionDao.getQuestions(1,1);
			System.out.println("---"+qlist.get(0).getQc().getTitleImg());
		}
}
