package net.huawei.wisdomstudy.dao.inter;

import java.util.List;

import net.huawei.wisdomstudy.controller.domain.QuestionQueryResult;
import net.huawei.wisdomstudy.domain.Question;

public interface IQuestionDao {

	/**
	 * 添加试题
	 * @author cexo added on 2019-5-19
	 * @param question
	 * @param questTypeId
	 * @param kpId
	 */
	public void addQuestion(Question question, int questTypeId, int kpId);
	
	/**
	 * 批量添加试题
	 * @author cexo added on 2018-5-12
	 * @param questionList
	 * @param questTypeId
	 * @param kpId
	 */
	public void addQuestion(List<Question> questionList, int questTypeId, int kpId);
	/**
	 * 按知识点OID查找所含试题的数量
	 * @author cexo addey on 2017-12-14 
	 * @param KnowledgePointId 知识点OID 
	 * @return int 试题数量 
	 */
	public int questionCount(int KnowledgePointId);
	
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
	 * @author cexo added on 2019-6-13
	 * @param kpId
	 * @return List<QuestionQueryResult>
	 */
	public List<QuestionQueryResult> getQuestions(int kpId);
	/**
	 * 按习题OID查询Question对象
	 * @author cexo added on 2018-3-17
	 * @param questionId
	 * @return Question
	 */
	public Question getQuestion(int questionId);
	
	/**
	 * 按Question的OID列表查询并返回QuestionQueryResult列表
	 * @author cexo added on 2018-7-4
	 * @param idList
	 * @return List<QuestionQueryResult>
	 */
	public List<QuestionQueryResult> getQuestions(List<Integer> idList);
	public QuestionQueryResult getQuestionsById(int id);
}
