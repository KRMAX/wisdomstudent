package net.huawei.wisdomstudy.controller;

import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



import net.huawei.wisdomstudy.domain.*;
import net.huawei.wisdomstudy.service.inter.IErrorHomeworkService;
import net.huawei.wisdomstudy.service.inter.IHCService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.huawei.wisdomstudy.controller.domain.HwCreatorParam;
import net.huawei.wisdomstudy.controller.domain.Message;
import net.huawei.wisdomstudy.security.UserInfo;
import net.huawei.wisdomstudy.service.inter.IHomeworkService;
import net.huawei.wisdomstudy.service.inter.IHomeworkStudentService;
import net.huawei.wisdomstudy.service.inter.IManageCourseService;
import net.huawei.wisdomstudy.service.inter.IQuestionService;
import net.huawei.wisdomstudy.service.inter.IStudentService;
import net.huawei.wisdomstudy.service.inter.ITeacherService;
import net.huawei.wisdomstudy.controller.domain.CacheDict;
import net.huawei.wisdomstudy.controller.domain.Combobox;

@Transactional(readOnly=true)
@Controller
public class HomeworkController  {

	/** 日志对象 */
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	 IHomeworkService hwService;
	
	@Autowired
	 IManageCourseService manageCourseService;

	@Autowired
	 IStudentService stService;
	
	@Autowired
	 IHomeworkStudentService hwstService;
	
	@Autowired
	ITeacherService teacherService;

	@Autowired
	IHCService hcService;
	
	@Autowired
	IQuestionService questionService;
	
	@Autowired
	IErrorHomeworkService errorHomeworkService;
	
	@RequestMapping(value = "/{role}/{username}/homeworkManage")
	public String homeworkManage(@PathVariable String role) {

		return role + "/homework-manage";
	}
	@RequestMapping(value = "/{role}/{username}/homeworkManageStudent")
	public String homeworkManageStudent(@PathVariable String role) {
        System.out.println(role);
		return role + "/homework-manage-student";
	}
	@RequestMapping(value = "/{role}/{username}/openHomeWork")
	public String openHomeWork(@PathVariable String role) {
		System.out.println(role);
		return role + "/openHomeWork";
	}
	/**
	 * 获取课程列表填充到Commobox控件中
	 * @author cexo added on 2018-7-4
	 * @return json[{id:1,text:"text"},...]
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getCourseComboboxForHw")
	public List<Combobox> getCourseComboboxForHw() {
		logger.debug("==================HomeworkController---getCourseComboboxForHw() [begin]==================");
		UserInfo userDetails = (UserInfo) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		int teacherId = userDetails.getLanderId();
		List<Combobox> cbbList = manageCourseService.getCourseByTeaId(teacherId);

		logger.debug("==================HomeworkController---getCourseComboboxForHw() [end]==================");
		
		return cbbList;
	}
	
	/**
	 * 获取课程列表填充到Commobox控件中
	 * @author cexo added on 2018-7-4
	 * @return json[{id:1,text:"text"},...]
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/getClazzComboboxForHw")
	public List<Combobox> getClazzComboboxForHw(@RequestParam("courseId")Integer courseId) {
		logger.debug("==================HomeworkController---getClazzComboboxForHw() [begin]==================");
		UserInfo userDetails = (UserInfo) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		int teacherId = userDetails.getLanderId();
		List<Combobox> cbbList = manageCourseService.getClazzList(teacherId, courseId);

		logger.debug("==================HomeworkController---getClazzComboboxForHw() [end]==================");		
		return cbbList;
	}

	/**
	 * 获取课程列表填充到Commobox控件中
	 * @author cexo added on 2018-7-4
	 * @return json[{id:1,text:"text"},...]
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getCourseComboboxForSt")
	public List<Combobox> getCourseComboboxForSt(HttpSession session) {
		logger.debug("==================HomeworkController---getCourseComboboxForHw() [begin]==================");
		int userid=0;
		if(session.getAttribute("studentUserId")!=""&&session.getAttribute("studentUserId")!=null){
			userid=(Integer)session.getAttribute("studentUserId");
		}
		int classid=0;
		Student student=stService.getStudentByUserid(userid);
		if(student!=null){
			classid=student.getClazz().getId();
		}
		
		List<Combobox> cbbList = manageCourseService.getCourseByClazzId(classid);

		logger.debug("==================HomeworkController---getCourseComboboxForHw() [end]==================");
		
		return cbbList;
	}
	
	/**
	 * added by cexo on 2018-7-4
	 * @param //method=RequestMethod.GET,produces = "text/html;charset=UTF-8"
	 * @return
	 */
	@RequestMapping(value = "/{role}/{username}/getHomeworkStudent")
	public @ResponseBody Map<String,Object> getHomeworkStudent(@RequestParam(value="rows", required=false)String rows,
														@RequestParam(value="page", required=false)String page,
														@RequestParam(value="institute", required=false)String institute,
														HttpServletRequest request,HttpSession session){ // rows:一页显示几行,page:第几页

		//转换为firstResult和maxResults
		//从datagrid的分页控件pager中返回两个参数

		int userid=0;
		int studentid=0;
		if(session.getAttribute("studentUserId")!=""&&session.getAttribute("studentUserId")!=null){
			userid=(Integer)session.getAttribute("studentUserId");
		}
		int classid=0;
		Student student=stService.getStudentByUserid(userid);
		studentid=student.getId();
		if(student!=null){
			classid=student.getClazz().getId();
		}
		int pageInt = (page==null||page=="0")?0:Integer.parseInt(page);
		int maxResults = (rows == null || rows=="0")?10:Integer.parseInt(rows);
		int firstResult = (pageInt - 1)*maxResults;
		System.out.println("pageInt=" + pageInt + ";maxResults=" + maxResults + ";firstResult = " + firstResult);
		
		List<Homework> hwList=new ArrayList<Homework>();
		//通过班级id，查找作业id
		List<HomeworkidClassId> hcList=hcService.getHomeworkidClazzidByClazzId(classid);
		for(HomeworkidClassId hc:hcList){
			Homework singlehw=new Homework();
			singlehw=hwService.getHomeworkById(hc.getHomeworkid());
			if(singlehw!=null) {
				System.out.println(singlehw.getCreator());
				if(!StringUtils.isEmpty(singlehw.getCreator())) {
					String teacherid=singlehw.getCreator();
					Teacher teacher=teacherService.getTeacherByEmpId(teacherid);
				    HomeWorkStudent hwst=hwstService.getHomeworkStudentByHomeworkId(singlehw.getId(), studentid);

					if(teacher!=null) {
						singlehw.setTeacherName(teacher.getName());
						if(hwst!=null) {
							singlehw.setHomeworkStatus(hwst.getIssubmit()==1?"是":"否");
							singlehw.setScore(hwst.getScore());
						}else {
							singlehw.setHomeworkStatus("否");
						}
						
					}else {
						logger.debug("====根据作业表中教师工号查询教师数据为空！！");
					}
				}
				hwList.add(singlehw);
			}else {
				logger.debug("====有数据为空！！");
			}
			
			
				
			
			
		}
		/*hw.setClazzsId(classid+"");
		List<Homework> hwList = hwService.getHomeWorkByCondition(hw,0,0);
		Iterator<Homework> hwIterator=hwList.iterator();
		while(hwIterator.hasNext()){
			String hwClassid=hwIterator.next().getClazzsId();
			if(hwClassid!=null&&hwClassid!=""){
				String[] ids=hwClassid.split(",");
				//二分查找,去掉类似于1，10得影响
				int index = Arrays.binarySearch(ids,classid);
				//小于0，去掉
				if(index<0){
					hwIterator.remove();
				}
			}
		}*/
		//将过滤后的值塞入返回得list
		List<Homework> rtnHwList=new ArrayList<Homework>();
		for (int j=firstResult;j<maxResults;j++){
			if(j<=hwList.size()-1){
				rtnHwList.add(hwList.get(j));
			}

		}

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", hcList.size());
		map.put("rows", rtnHwList);

		return map;
	}
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getHomework")
	public JSONObject getHomework(@RequestParam(value="id", required=true)int id,
			  HttpServletRequest request) {
		  Homework homeWork=new Homework();
		  JSONObject json=new JSONObject();
		  homeWork=hwService.getHomeworkById(id);
		
		  
		  if(homeWork!=null) {			  
			  json.put("name", homeWork.getName());
			  json.put("discrib", homeWork.getHmDesc());
			  json.put("clazzid", homeWork.getClazzsId());
			  json.put("questionid", homeWork.getQuestionId());
			  json.put("courseid", homeWork.getCourseId());
		  }

		return json;
		
	}
	/**
	 * added by cexo on 2018-7-4
	 * @param //method=RequestMethod.GET,produces = "text/html;charset=UTF-8"
	 * @return
	 */
	
	@RequestMapping(value = "/{role}/{username}/getHomeworkList")
	public @ResponseBody Map<String,Object> getHomeworkList(@RequestParam(value="rows", required=false)String rows,
			@RequestParam(value="page", required=false)String page,
			@RequestParam(value="institute", required=false)String institute,
			HttpServletRequest request){ // rows:一页显示几行,page:第几页
		
		//转换为firstResult和maxResults
		//从datagrid的分页控件pager中返回两个参数
		int pageInt = (page==null||page=="0")?0:Integer.parseInt(page);
		int maxResults = (rows == null || rows=="0")?10:Integer.parseInt(rows);
		int firstResult = (pageInt - 1)*maxResults;
		System.out.println("pageInt=" + pageInt + ";maxResults=" + maxResults + ";firstResult = " + firstResult);
		Homework hw = new Homework();
		List<Homework> hwList = hwService.getHomeWork(hw,firstResult,maxResults);
		List<Homework> newHwList=new ArrayList<Homework>();
		for(Homework singlehw:hwList){
			if(singlehw!=null) {
				if(!StringUtils.isEmpty(singlehw.getCreator())) {
					String teacherid=singlehw.getCreator();
					Teacher teacher=teacherService.getTeacherByEmpId(teacherid);
					if(teacher!=null) {
						singlehw.setTeacherName(teacher.getName());
					}else {
						logger.debug("====根据作业表中教师工号查询教师数据为空！！");
					}
				}
				newHwList.add(singlehw);
			}else {
				logger.debug("====有数据为空！！");
			}
			
			
				
			
			
		}
        int count=hwService.getHomeWork(hw,0,0).size();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", count);
		map.put("rows", newHwList);

		return map;
	}
	
	
	/**
	 * added by cexo on 2018-5-22
	 * @param
	 * @return
	 * ,  method = RequestMethod.POST, produces = "text/html;charset=UTF-8"
	 */
	@RequestMapping(value = "/{role}/{username}/addHomework")
	public @ResponseBody Message
	addHomework(@PathVariable String role, @RequestBody HwCreatorParam hmArrJson, HttpSession session){
		
		Message message = new Message();
		try{
			message = hwService.addHomework(hmArrJson,session);
	
			System.out.println(CacheDict.dictItemMap.get(1).getItemName() + "==2108春季学期");

		}catch(Exception e){
			logger.error("添加作业失败", e);
			message.setSuccess(false);
			message.setErrorMsg("添加作业失败");
		}
		return message;
		
	}
	/**
	 * added by cexo on 2018-5-22
	 * @param
	 * @return
	 * ,  method = RequestMethod.POST, produces = "text/html;charset=UTF-8"
	 */
	@RequestMapping(value = "/{role}/{username}/editHomework")
	public @ResponseBody Message
	editHomework(@PathVariable String role, @RequestBody HwCreatorParam hmArrJson, HttpSession session){
		
		Message message = new Message();
		try{
	
			
            hwService.updateHomework(hmArrJson,session);
	
			System.out.println(CacheDict.dictItemMap.get(1).getItemName() + "==2108春季学期");

		}catch(Exception e){
			logger.error("修改作业失败", e);
			message.setSuccess(false);
			message.setErrorMsg("修改作业失败");
		}
			message.setSuccess(true);
			message.setErrorMsg("修改作业成功");
		return message;
		
	}
	/**
	 * added by cexo on 2018-5-22
	 * @param
	 * @return
	 * ,  method = RequestMethod.POST, produces = "text/html;charset=UTF-8"
	 */
	@RequestMapping(value = "/{role}/{username}/deleteHomework")
	public @ResponseBody Message
	deleteHomework(@PathVariable String role, @RequestParam(value="id", required=false)Integer id){
		
		Message message = new Message();
	
	
			
			 message=hwService.deleteHomeworkById(id);
	
			System.out.println(CacheDict.dictItemMap.get(1).getItemName() + "==2108春季学期");

           if(message.getSuccessMsg()==null){
       		message.setSuccess(true);
			message.setSuccessMsg("删除作业成功");
           } 
		return message;
		
	}
	
	@RequestMapping(value = "/{role}/doHomeworkTest")
	public ModelAndView doHomeworkTest(@PathVariable String role){
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		Question q = new Question();
		q.setId(111);
		q.setAnswer("A");
		QuestionContent qc = new QuestionContent();
		qc.setTitleImg("/upload/question/1/1/1/2018-7-3-96210.png");
		q.setQc(qc);
		//Map<Integer, Question> singleOptList = new LinkedHashMap<Integer, Question>();
		List<Question> singleOptList = new ArrayList<Question>();
		singleOptList.add(q);
		q = new Question();
		q.setId(112);
		q.setAnswer("B");
		qc = new QuestionContent();
		qc.setTitleImg("/upload/question/1/1/1/2018-7-3-96211.png");
		q.setQc(qc);
		singleOptList.add(q);
		q = new Question();
		q.setId(3);
		q.setAnswer("C");
		qc = new QuestionContent();
		qc.setTitleImg("/upload/question/1/1/1/2018-7-3-96212.png");
		q.setQc(qc);
		singleOptList.add(q);
		
		map.put("singleOptList", singleOptList);
		map.put("htmll", "<p>我是后\"台传过来的消息!</p>");
		return new ModelAndView( role + "/doHomework", map);
	}
	
	@RequestMapping(value = "/{role}/{username}/getHomeworkStatus")
	public @ResponseBody JSONObject getHomeworkStatus(@PathVariable String role, @RequestParam(value="homeworkid", required=false)Integer homeworkid, HttpSession session){
		int studentid=0;
		int userid=0;
		if(session.getAttribute("studentUserId")!=""&&session.getAttribute("studentUserId")!=null){
			userid=(Integer)session.getAttribute("studentUserId");
		}
		
		Student student=stService.getStudentByUserid(userid);
		studentid=student.getId();
	
	    HomeWorkStudent hwst=hwstService.getHomeworkStudentByHomeworkId(homeworkid, studentid);
	
	    JSONObject json=new JSONObject();
	    if(hwst!=null) {
	    	if(1==hwst.getIssubmit()) {
	    		json.put("issubmit", 1);
	    	}else {
	    		json.put("issubmit", 0);
	    	}
	    	
	    }
	
	    
		return json;
	}
	
	@RequestMapping(value = "/{role}/{username}/countScore")
	public @ResponseBody Message countScore(@PathVariable String role,@RequestBody String[] list, HttpSession session){
		Message message = new Message();
		int score=0;
		int studentid=0;
		int homeworkid=Integer.parseInt(list[0]);
		String studentanswer="";
		int userid=0;
		if(session.getAttribute("studentUserId")!=""&&session.getAttribute("studentUserId")!=null){
			userid=(Integer)session.getAttribute("studentUserId");
		}
	
		Student student=stService.getStudentByUserid(userid);
		studentid=student.getId();
		Map<String, String> map=new HashMap<String, String>();
	    for(int i=1;i<list.length;i++) {
	    	String idAndAnswer=list[i];
	    	String questionidAndNUmber=idAndAnswer.split(":")[0];
	    	String questionid=questionidAndNUmber.split("_")[2];
	    	String answer=idAndAnswer.split(":")[1];
	    	if(!map.containsKey(questionid)) {
	    		map.put(questionid, answer);
	    	}else {
	    		String newValue=map.get(questionid)+answer;
	    		map.put(questionid, newValue);
	    	}
	    	
	    }
	    
	    Iterator<?> it = map.entrySet().iterator();
	    while (it.hasNext()) {
	     @SuppressWarnings("rawtypes")
		Map.Entry entry = (Map.Entry) it.next();
	     String id = entry.getKey().toString();
	     String answer = entry.getValue().toString();
	     Question question=questionService.getQuestion(Integer.parseInt(id));
	     studentanswer+=id+":"+answer+";";
	     //计算分数
	     if(answer.equals(question.getAnswer())) {
	    	 score+=2;
	    	 
	     }else {
	    	 ErrorHomeWork errorHomework=new ErrorHomeWork();
	    	 errorHomework.setHomeworkid(homeworkid);
	    	 errorHomework.setStudentid(studentid);
	    	 errorHomework.setQuestionid(Integer.parseInt(id));
	    	 errorHomework.setCurrentAnswer(question.getAnswer());
	    	 errorHomework.setErrorAnswer(answer);
	    	 try {
	    	 errorHomeworkService.addErrorHomeworkService(errorHomework);
	    	 }catch(Exception e) {
	    		 message.setErrorMsg("作业错误信息保存失败");
	 	    	message.setSuccess(false);
	 	    	
	    	 }
	     }
	     
	     
	    }
	    
	    HomeWorkStudent hwst=hwstService.getHomeworkStudentByHomeworkId(homeworkid, studentid);
	    if(hwst!=null) {
		    Timestamp ts = new Timestamp(new Date().getTime());
		    hwst.setIssubmit(1);
		    hwst.setScore(score);
		    hwst.setSubmittime(ts);
		    hwst.setStudentAnswer(studentanswer);
		    hwstService.updateHomeworkStudent(hwst);
	    }else {
	    	message.setErrorMsg("没有找到对应的学生信息");
	    	message.setSuccess(false);
	    	//回滚
	    	List<ErrorHomeWork> errorHmList=errorHomeworkService.getErrorHomeworkList(homeworkid, studentid);
	    	for(ErrorHomeWork errorHm:errorHmList) {
	    		errorHomeworkService.deleteErrorHomeworkService(errorHm);
	    	}
	    	
	    }

	    message.setSuccessMsg("作业完成！");
    	message.setSuccess(true);
	    
		return message;
	}
}
