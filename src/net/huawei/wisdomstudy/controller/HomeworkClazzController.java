package net.huawei.wisdomstudy.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
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

import net.huawei.wisdomstudy.domain.HomeWorkStudent;
import net.huawei.wisdomstudy.domain.HomeworkidClassId;
import net.huawei.wisdomstudy.domain.Student;
import net.huawei.wisdomstudy.service.inter.IClazzService;
import net.huawei.wisdomstudy.service.inter.IHomeworkClazzService;
import net.huawei.wisdomstudy.service.inter.IHomeworkService;
import net.huawei.wisdomstudy.service.inter.IHomeworkStudentService;
import net.huawei.wisdomstudy.service.inter.IStudentService;

@Transactional(readOnly=true)
@Controller
public class HomeworkClazzController {
	/** 日志对象 */
	private Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	IHomeworkClazzService hmClazzService;
	@Autowired
	IHomeworkStudentService hmStudentService;
	@Autowired
	IStudentService studentService;
	@Autowired
	IClazzService clazzService;
	@Autowired
	IHomeworkService hmService;
	@RequestMapping(value = "/{role}/{username}/getHomeWorkClazz")
	public @ResponseBody Map<String,Object> getHomeWorkClazz(@RequestParam(value="rows", required=false)String rows,
			@RequestParam(value="page", required=false)String page,
			@RequestParam(value="homeworkid", required=false)String homeworkid,
			@RequestParam(value="clazzid", required=false)String clazzid,
			HttpServletRequest request,HttpSession session){
		int clazzid2int=0;
		int homeworkid2int=Integer.parseInt(homeworkid);
		if(StringUtils.isNotBlank(clazzid)) {
			clazzid2int=Integer.parseInt(clazzid);
		}
		
		int pageInt = (page==null||page=="0")?0:Integer.parseInt(page);
		int maxResults = (rows == null || rows=="0")?10:Integer.parseInt(rows);
		int firstResult = (pageInt - 1)*maxResults;
		List<HomeworkidClassId> hmClazzList=hmClazzService.getHomeworkClazz(clazzid2int, homeworkid2int, firstResult, maxResults);
		for(HomeworkidClassId hmClazz:hmClazzList) {
			int totalScore=0;
		
			int finishStudentNum=0;
			Double averageScore=0.00;
			//根据班级id查询出学生
			List<Student> studentList=studentService.getStudentListByClazzId(hmClazz.getClazzid());
			int maxScore=0;
			int minScore=0;
			int i=0;
			for(Student student:studentList) {
			
				//根据学生和作业id查出作业得分内容
				HomeWorkStudent hwst=hmStudentService.getHomeworkStudentByHomeworkId(homeworkid2int, student.getId());
				if(i==0) {
					maxScore=hwst.getScore();	
					minScore=hwst.getScore();
				}
				if(hwst.getIssubmit()==1) {
					finishStudentNum++;
				}
				totalScore+=hwst.getScore();
				if(maxScore<hwst.getScore()) {
					maxScore=hwst.getScore();
				}
				
				if(minScore>hwst.getScore()) {
					minScore=hwst.getScore();
				}
				i++;
			}
			
			if(studentList.size()!=0&&totalScore!=0) {
				DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
				String num = df.format((float)totalScore/studentList.size());
				averageScore=Double.parseDouble(num);
			}
		
			
			String clazzName=clazzService.getClazz(hmClazz.getClazzid()).getClazzName();
			String homeWorkName=hmService.getHomeworkById(homeworkid2int).getName();
			hmClazz.setLowestscore(minScore);
			hmClazz.setAveragescore(averageScore);
			hmClazz.setHighestscore(maxScore);
			hmClazz.setStudentnum(studentList.size());
			hmClazz.setCompletionnum(finishStudentNum);
			hmClazz.setClazzName(clazzName);
			hmClazz.setHomeworkName(homeWorkName);
		}
		
		   int count=hmClazzList.size();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("total", count);
			map.put("rows", hmClazzList);

			return map;

		
	}
}
