package net.huawei.wisdomstudy.service.impl;

import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.huawei.wisdomstudy.dao.inter.*;
import net.huawei.wisdomstudy.domain.*;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.huawei.wisdomstudy.cons.CommonConstant;

import net.huawei.wisdomstudy.controller.domain.HwCreatorParam;
import net.huawei.wisdomstudy.controller.domain.Message;
import net.huawei.wisdomstudy.controller.domain.QuestionQueryResult;
import net.huawei.wisdomstudy.controller.domain.TreeNode;
import net.huawei.wisdomstudy.service.inter.IHomeworkService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
@Transactional
public class HomeworkServiceImpl extends HibernateDaoSupport  implements IHomeworkService {

	@Autowired
	ICourseDao courseDao;
	
	@Autowired
	IChapterDao chapterDao;
	
	@Autowired
	IKnowledgePointDao kpDao;
	
	@Autowired
	ICourseSelectingDao csDao;
	
	@Autowired
	IQuestionDao qDao;
	
	@Autowired
	IHwDao hwDao;

	@Autowired
	ITeacherDao teacherDao;
	
	@Autowired
	IHomeworkidClazzidDao hwcDao;
	
	@Autowired
	IStudentDao stDao;
	
	@Autowired
	IHomeworkStudentDao hwstDao;
	 @Resource
	    public void setSessionFacotry(SessionFactory sessionFacotry) {
	        super.setSessionFactory(sessionFacotry);
	    }
	/**
	 * 根据节点id和层级返回此节点的子节点集合
	 * @author cexo addedy on 2018-3-13
	 * @param id 传入的节点id
	 * @param level 传入的节点层级
	 * @param landerID 教师工号
	 * @return List<Course>
	 */
	@Override
	public List<TreeNode> getkptreeNodeById(int id, String level, int landerID) {

		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		
		if(level.equals(CommonConstant.KP_TREE_ROOT_LEVEL)){//传入的是根节点
			//csDao.getHasChozenCourse()
			List<Course> courseList = csDao.getCourseByTeaId(landerID);
			for(Course course: courseList){
				TreeNode node = new TreeNode();
				node.setId(course.getId());
				node.setName(course.getName());
				node.setLevel(CommonConstant.KP_TREE_COURSE_LEVEL);
				//按照课程OID查询是否有章节
				if(chapterDao.chapterCount(course.getId()) >= 1){		
					node.setLeaf(false);
				}else{
					node.setLeaf(true);
				}
				treeNodeList.add(node);
			}
		}else if(level.equals(CommonConstant.KP_TREE_COURSE_LEVEL)){ // 传入的是课程节点
			//去Chapter表中按照id查询此课程下是否有chapter
			List<Chapter> chapterList = chapterDao.getChapterList(id);
			for(Chapter chapter : chapterList){
				TreeNode node = new TreeNode();
				node.setId(chapter.getId());
				node.setName(chapter.getChapterName());
				node.setLevel(CommonConstant.KP_TREE_CHAPTER_LEVEL);
				//按照章节OID查询是否有知识点
				if(kpDao.knowledgePointCount(chapter.getId()) >= 1){
					node.setLeaf(false);
				}else{
					node.setLeaf(true);
				}
				treeNodeList.add(node);
			}
		}else if(level.equals(CommonConstant.KP_TREE_CHAPTER_LEVEL)){ // 传入的是章节节点
			
			//id是chapter的id，去KnowledgePoint表中按照id查询此章节下是否有knowledgePoint
			List<KnowledgePoint> kpList = kpDao.getKnowledgePointList(id);
			for(KnowledgePoint kp : kpList){
				TreeNode node = new TreeNode();
				node.setId(kp.getId());
				node.setName(kp.getKpDescribe());
				node.setLevel(CommonConstant.KP_TREE_KNOWLEDGE_LEVEL);
				//知识点算最后一层
				node.setLeaf(true);
				treeNodeList.add(node);
			}
		}
		return treeNodeList;
		
	}


	@Override
	public Homework getHomeworkById(int id) {
		Homework homework=new Homework();
		homework=getHibernateTemplate().get(Homework.class,id);



	return homework;

	}
	/**
	 * 将生成的homework保存到数据库中
	 * @author cexo added on 2018-7-4
	 * @param //HwCreatorParam
	 * @return Message
	 */
	@Override
	public Message addHomework(HwCreatorParam hmArrJson,HttpSession session) {
		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		/**1、获取当前题库对应的课程OID、当前教师OID、当前学期
		 * 2、将习题内容保存到hw的content中，将习题答案做成答题卡存入hw的answersheet中
		 * 3、按照习题对应的知识点统计每个知识点占有几道题，然后按知识点内随机选择题目，并存入到作业随机表中
		 */
		int userid=0;
		if(session.getAttribute("teacherUserId")!=""&&session.getAttribute("teacherUserId")!=null){
			userid=(Integer)session.getAttribute("teacherUserId");
		}
		int teacherid=0;
		Teacher teacher=teacherDao.getTeacherByUserId(userid);
		if(teacher!=null){
			teacherid=teacher.getId();
		}
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String creator = userDetails.getUsername();

        List<Integer> idList=hmArrJson.getIdList();
        String questionid="";

        for(int id:idList){
			questionid+=id+",";
        }

		Homework hw = new Homework();
		hw.setTeacherId(teacherid);
		hw.setCourseId(hmArrJson.getCourseId());
		hw.setHmDesc(hmArrJson.getHwDiscrib());
		hw.setCreateTime(new Timestamp(new Date().getTime()));
		hw.setCreator(creator);
		hw.setQuestionId(questionid);
		hw.setName(hmArrJson.getHmName());
		hw.setTerm(hmArrJson.getTerm());
		hw.setClazzsId(hmArrJson.getClazzsId());
		hw.setHomeworkPaper(sw.toString());
		hwDao.addHomework(hw);
		
		//获取当前最大的作业表id
		int maxid=hwDao.getMaxId();
        if(StringUtils.isNotEmpty(hmArrJson.getClazzsId())) {
       	 String[] clazzids=hmArrJson.getClazzsId().split(",");
       	 for(String clazzid:clazzids) {
                List<Student> stList=stDao.getStudentListByClazzId(Integer.parseInt(clazzid));
               for(Student st:stList) {
               	HomeWorkStudent hwst=new HomeWorkStudent();
               	hwst.setHomeworkid(maxid);
               	hwst.setStudentid(st.getId());
               	hwst.setIsalldone(0);
               	hwst.setScore(0);
               	hwstDao.addHomeworkStudent(hwst);
               }
       	 }
       }
      
		String[] strArrs=hmArrJson.getClazzsId().split(",");
		for(String str:strArrs){
			int str2Int=Integer.parseInt(str);
			
			HomeworkidClassId homeworkidClassId=new HomeworkidClassId();
			homeworkidClassId.setClazzid(str2Int);
			homeworkidClassId.setHomeworkid(maxid);

			hwDao.addHomeworkidClassid(homeworkidClassId);
		}
		Message message = new Message();
		List<QuestionQueryResult> qqrList = qDao.getQuestions(hmArrJson.getIdList());



	
		try {
            objectMapper.writeValue(sw, qqrList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            message.setSuccessMsg("添加失败");
            message.setSuccess(false);
        }
	
		

		message.setSuccess(true);
		message.setSuccessMsg("添加作业成功");
		return message;
	}

	@Override
	public List<Homework> getHomeWork(Homework homework, int firstResult, int maxResults) {
		List<Homework> homeworkList=new ArrayList<Homework>();
		//自由组合查询条件
		DetachedCriteria criteria=DetachedCriteria.forClass(Homework.class);
         if(homework.getClazzsId()!=null&&homework.getClazzsId()!=""){
			 criteria.add(Restrictions.like("clazzs",homework.getClazzsId()));
		 }
		//如果有分页
		if(firstResult >= 0 && maxResults > 0) {
			homeworkList = (List<Homework>)getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
		}else {
			//List<Clazz> clazzList = (List<Clazz>) getHibernateTemplate().findByNamedParam(hql, paramNames, values);
			homeworkList = (List<Homework>)getHibernateTemplate().findByCriteria(criteria);
		}
		/*findByExample()会忽略所有值为null的参数，但如果参数包含8种基础类型，它们的默认空值不是null，这样就会导致错误。
		* 所以，请不要将带有基础类型变量的bean用于findByExample。
		* findByExample()不支持主键。
		* 当我们想要通过主键查询一个值时，应当适用HibernateTemplate提供的
		* Object get(String entityClassName, Serializable id)方法，前者是所查的表的实例的类名（注意不是真实的表名），后者为主键。
		List<Clazz> clazzList = (List<Clazz>) getHibernateTemplate().findByExample(clazz);
		*/

		return homeworkList;
	}
	@Override
	public Message editHomework(HwCreatorParam hmArrJson, HttpSession session) {
		return null;}
	@Override
	public void updateHomework(HwCreatorParam hmArrJson,HttpSession session) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String creator = userDetails.getUsername();
		int eidtid=Integer.parseInt(hmArrJson.getHomeworkId());
		int userid=0;
		if(session.getAttribute("teacherUserId")!=""&&session.getAttribute("teacherUserId")!=null){
			userid=(Integer)session.getAttribute("teacherUserId");
		}
	
        List<Integer> idList=hmArrJson.getIdList();
        String questionid="";

        for(int id:idList){
			questionid+=id+",";
        }
		int teacherid=0;
		Teacher teacher=teacherDao.getTeacherByUserId(userid);
		if(teacher!=null){
			teacherid=teacher.getId();
		}
		Homework hw =hwDao.getHomeworkById(eidtid);
		hw.setTeacherId(teacherid);
		hw.setCourseId(hmArrJson.getCourseId());
		hw.setHmDesc(hmArrJson.getHwDiscrib());
		hw.setCreateTime(new Timestamp(new Date().getTime()));
		hw.setCreator(creator);
		hw.setQuestionId(questionid);
		hw.setName(hmArrJson.getHmName());
		hw.setTerm(hmArrJson.getTerm());
		hw.setClazzsId(hmArrJson.getClazzsId());
		
		getHibernateTemplate().update(hw);
		
		List<HomeworkidClassId> hwcList=hwcDao.getHomeworkidClazzidByHomeworkId(eidtid);
		for(HomeworkidClassId hwc:hwcList) {
				getHibernateTemplate().delete(hwc);	
		}
		
		
		String[] strArrs=hmArrJson.getClazzsId().split(",");
		for(String str:strArrs){
			int str2Int=Integer.parseInt(str);
			HomeworkidClassId homeworkidClassId=new HomeworkidClassId();
			homeworkidClassId.setClazzid(str2Int);
			homeworkidClassId.setHomeworkid(eidtid);

			hwDao.addHomeworkidClassid(homeworkidClassId);
		}
		
		
		List<HomeWorkStudent> hwstList=hwstDao.getHomeworkStudentListByHomeworkId(Integer.parseInt(hmArrJson.getHomeworkId()));
		for(HomeWorkStudent hwst:hwstList) {
			getHibernateTemplate().delete(hwst);	
	}
        if(StringUtils.isNotEmpty(hmArrJson.getClazzsId())) {
          	 String[] clazzids=hmArrJson.getClazzsId().split(",");
          	 for(String clazzid:clazzids) {
                   List<Student> stList=stDao.getStudentListByClazzId(Integer.parseInt(clazzid));
                  for(Student st:stList) {
                  	HomeWorkStudent hwst=new HomeWorkStudent();
                  	hwst.setHomeworkid(eidtid);
                  	hwst.setStudentid(st.getId());
                  	hwst.setIsalldone(0);
                  	hwst.setScore(0);
                  	hwst.setIssubmit(0);
                  	hwstDao.addHomeworkStudent(hwst);
                  }
          	 }
          }
		
		
	}
	@Override
	public Message deleteHomeworkById(int id) {
		Message message = new Message();

		List<HomeworkidClassId> hwcList=hwcDao.getHomeworkidClazzidByHomeworkId(id);
		for(HomeworkidClassId hwc:hwcList) {
			try {
				getHibernateTemplate().delete(hwc);	
			}catch(Exception e){
				message.setSuccess(false);
				message.setErrorMsg("作业班级子表删除失败");
				
			}
		}
		
		Homework hw =hwDao.getHomeworkById(id);
		try {
		getHibernateTemplate().delete(hw);
		}catch(Exception e){
			message.setSuccess(false);
			message.setErrorMsg("作业删除失败");
			
		}
		return message;
	}
	

	
}
