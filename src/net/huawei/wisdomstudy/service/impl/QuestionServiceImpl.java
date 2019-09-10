package net.huawei.wisdomstudy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.controller.domain.QuestionQueryResult;
import net.huawei.wisdomstudy.dao.inter.IQuestionDao;
import net.huawei.wisdomstudy.domain.Question;
import net.huawei.wisdomstudy.service.inter.IQuestionService;

@Service
@Transactional
public class QuestionServiceImpl implements IQuestionService {

	@Autowired
	IQuestionDao qDao;
	
	/**
	 * 按知识点OID及题型OID查找所有习题
	 * @author cexo added on 2018-3-15
	 * @param kpId
	 * @param questTypeId
	 * @return List<Question>
	 */
	@Override
	public List<Question> getQuestions(int kpId, int questTypeId) {

		return qDao.getQuestions(kpId, questTypeId);
	}
	
	/**
	 * 按知识点OID查找所有习题
	 * @author cexo added on 2018-6-20
	 * @param questTypeId
	 * @return List<QuestionQueryResult>
	 */
	@Override
	public List<QuestionQueryResult> getQuestions(int kpId) {

		return qDao.getQuestions(kpId);
	}

	/**
	 * 按习题OID查询Question对象
	 * @author cexo added on 2018-3-17
	 * @param questionId
	 * @return Question
	 */
	@Override
	public Question getQuestion(int questionId) {

		return qDao.getQuestion(questionId);
	}

	@Override
	public QuestionQueryResult getQuestionsById(int id) {
		// TODO Auto-generated method stub
		return qDao.getQuestionsById(id);
	}
}
