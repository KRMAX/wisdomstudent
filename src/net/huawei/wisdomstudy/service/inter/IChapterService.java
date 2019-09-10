package net.huawei.wisdomstudy.service.inter;

import java.util.List;

import net.huawei.wisdomstudy.controller.domain.Combobox;
import net.huawei.wisdomstudy.domain.Chapter;
import net.huawei.wisdomstudy.domain.Field;
import net.huawei.wisdomstudy.domain.KnowledgePoint;

public interface IChapterService {

	/**
	 * 根据field获取所有章节
	 * @author cexo addey on 2018-3-13
	 * @param fieldId
	 * @return List<Chapter>
	 */
	public List<Chapter> getChapterListByField(int fieldId);
	
	/**
	 * 按chapterId获取所有知识点
	 * @author cexo addey on 2018-3-14
	 * @param chapterId
	 * @return List<Combobox>
	 */
	public List<Combobox> getKnowledgePointByChapId(int chapterId);
	
	/**
	 * 获取所有Combobox类型题库列表
	 * @author cexo added on 2019年5月21日
	 * @return List<Combobox>
	 */
	public List<Combobox> getFieldList();
}
