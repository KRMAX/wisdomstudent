package net.huawei.wisdomstudy.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import net.huawei.wisdomstudy.dao.inter.IChapterDao;
import net.huawei.wisdomstudy.domain.Chapter;
import net.huawei.wisdomstudy.domain.Course;
import net.huawei.wisdomstudy.domain.Field;

@Repository
public class ChapterDaoImpl extends HibernateDaoSupport implements IChapterDao{

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	/**
	 * 添加班级信息
	 * @author cexo addedy on 2017-12-8
	 * @param chapterName
	 * @param fieldId
	 * @return
	 */
	@Override
	public void addChapter(String chapterName, int fieldId) {

		Chapter chap = new Chapter();
		chap.setChapterName(chapterName);
		
		String hql = "from Field f where f.id = ?";		
		@SuppressWarnings("unchecked")
		List<Field>fieldList = (List<Field>) getHibernateTemplate().find(hql, fieldId);
		if(!fieldList.isEmpty()){
			chap.setField(fieldList.get(0));
		}
		
		getHibernateTemplate().save(chap);

	}

	/**
	 * 
	 * 按课程OID获取所有章节
	 * @author cexo addey on 2019-5-18
	 * @return List<Chapter>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Chapter> getChapterList(int courseId) {

		Course course = (Course) getHibernateTemplate().get(Course.class, courseId);
		
		String hql = "from Chapter c where c.field = ?";
		List<Chapter>ChapterList = (List<Chapter>) getHibernateTemplate().find(hql, course.getField());

		return ChapterList;
	}

	/**
	 * 按课程OID查找所含章节的数量
	 * @author cexo addey on 2017-12-14 
	 * @param courseId
	 * @return int 返回数量 
	 */
	@Override
	public int chapterCount(int courseId) {

		String hql = "from Course c where c.id = ?";
		Course course = getHibernateTemplate().get(Course.class, courseId);
		
		hql = "select count(*) from Chapter ch where ch.field.id = ?";
		Long i = (Long)getHibernateTemplate().find(hql, course.getField().getId()).iterator().next();
		return i.intValue();
	}

	/**
	 * 根据field获取所有章节
	 * @author cexo addey on 2018-3-13
	 * @param fieldId
	 * @return List<Chapter>
	 */
	@Override
	public List<Chapter> getChapterListByField(int fieldId) {

		String hql = "from Chapter c where c.field.id = ?";
		
		@SuppressWarnings("unchecked")
		List<Chapter>ChapterList = (List<Chapter>) getHibernateTemplate().find(hql, fieldId);

		return ChapterList;
	}

	@Override
	public List<Field> getFieldList() {
		
		String hql = "from Field ";
		
		@SuppressWarnings("unchecked")
		List<Field> fieldList = (List<Field>) getHibernateTemplate().find(hql);

		return fieldList;
	}

}
