package net.huawei.wisdomstudy.dao.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.huawei.wisdomstudy.controller.domain.QuestionQueryResult;
import net.huawei.wisdomstudy.dao.inter.IQuestionDao;
import net.huawei.wisdomstudy.domain.KnowledgePoint;
import net.huawei.wisdomstudy.domain.Question;
import net.huawei.wisdomstudy.domain.QuestionContent;
import net.huawei.wisdomstudy.domain.QuestionType;

@Repository
public class QuestionDaoImpl extends HibernateDaoSupport implements IQuestionDao {

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	/**
	 * 添加试题
	 * @author cexo added on 2018-6-20
	 * @param question
	 * @param questTypeId
	 * @param kpId
	 */
	@Override
	public void addQuestion(Question question, int questTypeId, int kpId) {

		if(null != question){
			Timestamp createTime = new Timestamp(new Date().getTime());
			question.setCreateTime(createTime);
			QuestionType qt = getHibernateTemplate().get(QuestionType.class, questTypeId);
			question.setQuestionType(qt);
			question.setQuestionTypeStr(qt.getName());
			KnowledgePoint kp = getHibernateTemplate().get(KnowledgePoint.class, kpId);
			question.setKnowledgePoint(kp);
			getHibernateTemplate().save(question);
		}
			
	}

	/**
	 * 批量添加试题
	 * @author cexo added on 2018-5-12
	 * @param questionList
	 * @param questTypeId
	 * @param kpId
	 */
	@Override
	public void addQuestion(List<Question> questionList, int questTypeId, int kpId) {

		if(null != questionList && questionList.size() != 0){
			QuestionType qt = getHibernateTemplate().load(QuestionType.class, questTypeId);
			KnowledgePoint kp = getHibernateTemplate().load(KnowledgePoint.class, kpId);
			for(Question question:questionList){
				Timestamp createTime = new Timestamp(new Date().getTime());
				question.setCreateTime(createTime);
				question.setQuestionType(qt);
				question.setQuestionTypeStr(qt.getName());
				question.setKnowledgePoint(kp);
				
				getHibernateTemplate().save(question);
			}
		}
		
	}
	
	/**
	 * 按知识点OID查找所含试题的数量
	 * @author cexo addey on 2017-12-14 
	 * @param KnowledgePointId 知识点OID 
	 * @return int 试题数量 
	 */
	@Override
	public int questionCount(int KnowledgePointId) {

		String hql = "select count(*) from Question q where q.knowledgePoint.id = ?";
		//Long l = (long)getHibernateTemplate().find(hql, KnowledgePointId).iterator().next();
		//return l.intValue();
		int l = getHibernateTemplate().find(hql, KnowledgePointId).size();
		return l;
	}

	/**
	 * 按知识点OID及题型OID查找所有习题
	 * @author cexo added on 2018-3-15
	 * @param kpId
	 * @param questTypeId
	 * @return List<Question>
	 */
	@Override
	public List<Question> getQuestions(int kpId, int questTypeId) {

		String hql = "from Question q where q.knowledgePoint.id = ? and q.questionType.id = ?";
		
		@SuppressWarnings("unchecked")
		List<Question> qList = (List<Question>) getHibernateTemplate().find(hql, kpId,questTypeId);
		
		// added on 2018-6-19	
		ObjectMapper objectMapper = new ObjectMapper();
		Iterator<Question> it = qList.iterator();
		while(it.hasNext()){
			try {
				Question q = it.next();
				QuestionContent qc = objectMapper.readValue(q.getContent(), QuestionContent.class);
				q.setQc(qc);
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
		}		
		
		return qList;
	}

	/**
	 * 按知识点OID查找所有习题
	 * @author cexo added on 2019-6-20
	 * @param kpId
	 * @return List<QuestionQueryResult>
	 */
	@Override
	public List<QuestionQueryResult> getQuestions(int kpId) {

		String hql = "from Question q where q.knowledgePoint.id = ? ";
		
		@SuppressWarnings("unchecked")
		List<Question> qList = (List<Question>) getHibernateTemplate().find(hql, kpId);
		List<QuestionQueryResult> qqrList = new ArrayList<QuestionQueryResult>();
		
		// added on 2018-6-19
		ObjectMapper objectMapper = new ObjectMapper();
		Iterator<Question> it = qList.iterator();
		while(it.hasNext()){
			try {
				Question q = it.next();
				QuestionContent qc = objectMapper.readValue(q.getContent(), QuestionContent.class);
				QuestionQueryResult qqr = new QuestionQueryResult();
				qqr.setQuestionId(q.getId());
				qqr.setContent(qc);
				qqr.setAnswer(q.getAnswer());
				qqr.setAnalysis(q.getAnalysis());
				qqr.setQuestionTypeId(q.getQuestionType().getId());
				qqr.setQuestionTypeName(q.getQuestionType().getName());
				qqr.setKnowledgePointId(q.getKnowledgePoint().getId());
				qqr.setKpDescribe(q.getKnowledgePoint().getKpDescribe());
				qqr.setCreator(q.getCreator());
				qqr.setCreateTime(q.getCreateTime());
				qqrList.add(qqr);
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
		}
		return qqrList;
	}
	
	/**
	 * 按习题OID查询Question对象
	 * @author cexo added on 2018-3-17
	 * @param questionId
	 * @return Question
	 */
	@Override
	public Question getQuestion(int id) {

		Question q = getHibernateTemplate().get(Question.class, id);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			QuestionContent qc = objectMapper.readValue(q.getContent(), QuestionContent.class);
			q.setQc(qc);
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
		return q;
	}

	/**
	 * 按Question的OID列表查询并返回QuestionQueryResult列表
	 * @author cexo added on 2018-7-4
	 * @param idList
	 * @return List<QuestionQueryResult>
	 */
	@Override
	public List<QuestionQueryResult> getQuestions(List<Integer> idList) {

		List<QuestionQueryResult> qqrList = new ArrayList<QuestionQueryResult>();
		for(Iterator<Integer> it = idList.iterator();it.hasNext();){
			int id = it.next().intValue();
			Question q = getQuestion(id);
			QuestionQueryResult qqr = new QuestionQueryResult();
			qqr.setQuestionId(q.getId());
			qqr.setContent(q.getQc());
			qqr.setAnswer(q.getAnswer());
			qqr.setAnalysis(q.getAnalysis());
			qqr.setKnowledgePointId(q.getKnowledgePoint().getId());
			qqr.setKpDescribe(q.getKnowledgePoint().getKpDescribe());
			qqr.setQuestionTypeId(q.getQuestionType().getId());
			qqr.setQuestionTypeName(q.getQuestionType().getName());
			qqrList.add(qqr);
		}
		
		return qqrList;
	}

	@Override
	public QuestionQueryResult getQuestionsById(int id) {
		QuestionQueryResult qqr = new QuestionQueryResult();
		ObjectMapper objectMapper = new ObjectMapper();
		Question q = getHibernateTemplate().get(Question.class, id);

			
			QuestionContent qc;
			try {
				qc = objectMapper.readValue(q.getContent(), QuestionContent.class);
				qqr.setContent(qc);
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
			
			qqr.setQuestionId(q.getId());
			qqr.setAnswer(q.getAnswer());
			qqr.setAnalysis(q.getAnalysis());
			qqr.setKnowledgePointId(q.getKnowledgePoint().getId());
			qqr.setKpDescribe(q.getKnowledgePoint().getKpDescribe());
			qqr.setQuestionTypeId(q.getQuestionType().getId());
			qqr.setQuestionTypeName(q.getQuestionType().getName());

		return qqr;

	}
	
}
