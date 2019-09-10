package net.huawei.wisdomstudy.dao.inter;

import java.util.List;
import java.util.Map;

import net.huawei.wisdomstudy.domain.KnowledgePoint;

public interface IKnowledgePointDao {

	/**
	 * 添加知识点
	 * @author cexo addedy on 2017-12-9
	 * @param kpDescribe
	 * @param chapterId
	 * @return
	 */
	public void addKnowledgePoint(String kpDescribe, int chapterId);
	
	/**
	 * 添加知识点
	 * @author cexo addedy on 2017-12-9
	 * @param kpDescribe
	 * @param chapterId
	 * @param effectTag
	 * @return
	 */
	public void addKnowledgePoint(String kpDescribe, int chapterId, boolean effectTag);
	
	/**
	 * 按章节id分页获取知识点
	 * @param chapterId 章节OID
	 * @param firstResult 起始位置
	 * @param maxResults  获取的数量
	 * @author cexo addey on 2017-11-14
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getKnowledgePointList(int chapterId, int firstResult, int maxResults);
	
	/**
	 * 按章节id获取知识点
	 * @param chapterId 章节OID
	 * @author cexo addey on 2017-12-9
	 * @return List<KnowledgePoint>
	 */
	public List<KnowledgePoint> getKnowledgePointList(int chapterId);
	
	/**
	 * 按章节OID查找所含知识点的数量
	 * @author cexo addey on 2017-12-14
	 * @param chapterId 章节OID 
	 * @return int 知识点数量
	 */
	public int knowledgePointCount(int chapterId);

}
