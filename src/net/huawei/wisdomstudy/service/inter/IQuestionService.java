package net.huawei.wisdomstudy.service.inter;

import java.util.List;

import net.huawei.wisdomstudy.controller.domain.QuestionQueryResult;
import net.huawei.wisdomstudy.domain.Question;

public interface IQuestionService {

	/**
	 * 按知识点OID及题型OID查找所有习题
	 * @author cexo added on 2018-3-15
	 * @param kpId
	 * @param questTypeId
	 * @return List<Question>
	 */
	public List<Question> getQuestions(int kpId, int questTypeId);
	
	/**
	 * 按知识点OID查找所有习题
	 * @author cexo added on 2019-6-20
	 * @param questTypeId
	 * @return List<QuestionQueryResult>
	 */
	public List<QuestionQueryResult> getQuestions(int kpId);
	
	public QuestionQueryResult getQuestionsById(int id);
	
	/**
	 * 按习题OID查询Question对象
	 * @author cexo added on 2018-3-17
	 * @param questionId
	 * @return Question
	 */
	public Question getQuestion(int questionId);
}
