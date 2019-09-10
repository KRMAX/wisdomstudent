package net.huawei.wisdomstudy.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huawei.wisdomstudy.controller.domain.QuestionQueryResult;
import net.huawei.wisdomstudy.domain.HomeWorkStudent;
import net.huawei.wisdomstudy.domain.Homework;
import net.huawei.wisdomstudy.service.inter.IErrorHomeworkService;
import net.huawei.wisdomstudy.service.inter.IHomeworkService;
import net.huawei.wisdomstudy.service.inter.IHomeworkStudentService;
import net.huawei.wisdomstudy.service.inter.IQuestionService;
@Transactional(readOnly=true)
@Controller
public class ErrorController {
	@Autowired
	 IHomeworkService hwService;
	
	@Autowired
	 IHomeworkStudentService hwstService;
	@Autowired
	IQuestionService questionService;
	
	@Autowired
	IErrorHomeworkService errorHomeworkService;
	/** 日志对象 */
	private Log logger = LogFactory.getLog(this.getClass());
	@RequestMapping(value = "/{role}/{username}/getErrorHomeWork")
	public @ResponseBody Map<String,Object> getHomeWorkStudent(@RequestParam(value="rows", required=false)String rows,
			@RequestParam(value="page", required=false)String page,
			@RequestParam(value="homeworkid", required=false)int homeworkid,
			@RequestParam(value="studentid", required=false)int studentid,
			HttpServletRequest request,HttpSession session){
		    Homework hw=hwService.getHomeworkById(homeworkid);
	        if(hw!=null) {
	        	if(StringUtils.isNotBlank(hw.getQuestionId())) {
	        		String[] questionids=hw.getQuestionId().split(",");
	        	    int i=0;
	        		for(String questionid:questionids) {
	        			i++;
	        			QuestionQueryResult qs=questionService.getQuestionsById(Integer.parseInt(questionid));
	        			String answer=qs.getAnswer();
	        			
	        		}
	        	}else {
	        		logger.error("作业没有题目！");
	        	}
	        }else {
	        	logger.error("未获取作业内容！");
	        }
				return null;
		
	}
}
