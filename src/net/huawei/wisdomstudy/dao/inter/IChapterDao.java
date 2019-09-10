package net.huawei.wisdomstudy.dao.inter;

import java.util.List;

import net.huawei.wisdomstudy.domain.Chapter;
import net.huawei.wisdomstudy.domain.Field;

public interface IChapterDao {

	/**
	 * 添加班级信息
	 * @author cexo addedy on 2017-12-8
	 * @param chapterName
	 * @param fieldId
	 * @return
	 */
	public void addChapter(String chapterName, int fieldId);
	
	/**
	 * 
	 * 目前是将所有章节一次性展示
	 * @author cexo addey on 2017-12-9 
	 * @return List<Chapter>
	 */
	public List<Chapter> getChapterList(int courseId);
	
	/**
	 * 根据field获取所有章节
	 * @author cexo addey on 2018-3-13
	 * @param fieldId
	 * @return List<Chapter>
	 */
	public List<Chapter> getChapterListByField(int fieldId);
	
	/**
	 * 按课程OID查找所含章节的数量
	 * @author cexo addey on 2017-12-14 
	 * @param courseId
	 * @return int 
	 */
	public int chapterCount(int courseId);

	/**
	 * 获取题库列表
	 * @author cexo added on 2019年5月21日
	 * @return List<Field>
	 */
	public List<Field> getFieldList();
}
