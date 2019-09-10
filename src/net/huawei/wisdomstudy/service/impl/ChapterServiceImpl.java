package net.huawei.wisdomstudy.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.controller.domain.Combobox;
import net.huawei.wisdomstudy.dao.inter.IChapterDao;
import net.huawei.wisdomstudy.dao.inter.IKnowledgePointDao;
import net.huawei.wisdomstudy.domain.Chapter;
import net.huawei.wisdomstudy.domain.Field;
import net.huawei.wisdomstudy.domain.KnowledgePoint;
import net.huawei.wisdomstudy.service.inter.IChapterService;

@Service
@Transactional
public class ChapterServiceImpl implements IChapterService{

	@Autowired
	IChapterDao cDao;
	@Autowired
	IKnowledgePointDao kpDao;
	
	/**
	 * 根据field获取所有章节
	 * @author cexo addey on 2018-3-13
	 * @param fieldId
	 * @return List<Chapter>
	 */
	@Override
	public List<Chapter> getChapterListByField(int fieldId) {

		return cDao.getChapterListByField(fieldId);
	}

	/**
	 * 按chapterId获取所有Combobox类型知识点
	 * @author cexo addey on 2018-3-14
	 * @param chapterId
	 * @return List<Combobox>
	 */
	@Override
	public List<Combobox> getKnowledgePointByChapId(int chapterId) {

		List<KnowledgePoint> kpPoint = kpDao.getKnowledgePointList(chapterId);
		List<Combobox> cbList = new ArrayList<Combobox>();
		Iterator<KnowledgePoint> it = kpPoint.iterator();
		while(it.hasNext()){
			KnowledgePoint kp = it.next();
			Combobox cb = new Combobox();
			cb.setId(kp.getId());
			cb.setText(kp.getKpDescribe());
			cbList.add(cb);
		}
		return cbList;

	}

	/**
	 * 获取所有Combobox类型题库列表
	 * @author cexo added on 2019年5月21日
	 * @return List<Combobox>
	 */
	@Override
	public List<Combobox> getFieldList() {
		
		List<Field> fieldList = cDao.getFieldList();
		List<Combobox> cbList = new ArrayList<Combobox>();
		Iterator<Field> it = fieldList.iterator();
		while(it.hasNext()){
			Field f = it.next();
			Combobox cb = new Combobox();
			cb.setId(f.getId());
			cb.setText(f.getName());
			cbList.add(cb);
		}
		return cbList;
	}

}
