package net.huawei.wisdomstudy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/*import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;*/
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.huawei.wisdomstudy.controller.domain.CacheDict;
import net.huawei.wisdomstudy.controller.domain.Combobox;
import net.huawei.wisdomstudy.controller.domain.Message;
import net.huawei.wisdomstudy.controller.domain.SelectCourseResult;
import net.huawei.wisdomstudy.domain.Clazz;
import net.huawei.wisdomstudy.domain.Course;
import net.huawei.wisdomstudy.domain.Dictionary;
import net.huawei.wisdomstudy.domain.Teacher;
import net.huawei.wisdomstudy.service.inter.IClazzService;
import net.huawei.wisdomstudy.service.inter.IManageCourseService;
import net.huawei.wisdomstudy.util.PropertyUtil;

@Controller
public class ManageCourseController{
	
	/** 日志对象 */
	private Log logger = LogFactory.getLog(this.getClass());

	/*@Autowired
	ILandingSysService landingSysService;
	
	@Autowired
	IStudentService stuService;
	*/
	@Autowired
	IClazzService clazzService;
	
	@Autowired
	IManageCourseService manageCourService;
	
	
	@RequestMapping(value = "/{role}/{username}/open-course-selecting", method = RequestMethod.GET)
	public String openCourseSelecting(@PathVariable String username,
			@PathVariable String role,
			Model model, HttpServletRequest request) {
		
		return role + "/course-selecting-list";
	}

	/**
	 * 获取教师的系列表填充到Commobox控件中
	 * @author cexo added on 2019-5-16
	 * @return json[{id:1,text:"text"},...]
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getTeacherDepartmentCombobox")
	public List<Combobox> getTeacherDepartmentCombobox(@PathVariable String role, @PathVariable String username) {
		logger.debug("==================ManageCourseController---getTeacherDepartmentCombobox() [begin]==================");
		
		List<Combobox> cbbList = new ArrayList<Combobox>();
		List<Dictionary> departmentList = CacheDict.dictTypeMap.get("系");
		for(Dictionary dic : departmentList) {
			Combobox cbox = new Combobox();
			cbox.setId(dic.getId());
			cbox.setText(dic.getItemName());
			cbbList.add(cbox);
			System.out.println("dic-id" + dic.getId());
		}

		logger.debug("==================ManageCourseController---getTeacherDepartmentCombobox() [end]==================");
		
		return cbbList;
	}
	
	/**
	 * 根据系获取专业列表填充到Commobox控件中
	 * @author cexo added on 2019-5-16
	 * @return json[{id:1,text:"text"},...]
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getMajorByDepId/{departmentId}")
	public List<Combobox> getMajorByDepIdCombobox(@PathVariable String role, 
			@PathVariable int departmentId,
			@PathVariable String username) {
		logger.debug("==================ManageCourseController---getMajorByDepIdCombobox() [begin]==================");
		
		List<Combobox> cbbList = new ArrayList<Combobox>();
		List<Dictionary> departmentList = CacheDict.dictTypeMap.get("专业");
		String itemCode = CacheDict.dictItemMap.get(departmentId).getItemCode();
		for(Dictionary dic : departmentList) {
			if(dic.getItemCode().substring(0,3).equals(itemCode)) {
				Combobox cbox = new Combobox();
				cbox.setId(dic.getId());
				cbox.setText(dic.getItemName());
				cbbList.add(cbox);
			}
		}

		logger.debug("==================ManageCourseController---getMajorByDepIdCombobox() [end]==================");
		
		return cbbList;
	}
	
	/**
	 * added by cexo on 2019-5-15
	 * 进入选课界面时，调用此方法
	 * 获取教师列表、班级列表、课程列表、已选课列表
	 * @param username
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{role}/{username}/getCourseSelecting/{teacherMajor}/{admissionYear}/{department}", 
			method = RequestMethod.GET)
	public String courseSelecting(@PathVariable String username,
			@PathVariable String role,
			@PathVariable String teacherMajor,
			@PathVariable String admissionYear,
			@PathVariable String department,
			Model model, HttpServletRequest request) {

		//自动获取教师列表
		List<Teacher> teacherList = manageCourService.getTeacherList(teacherMajor);
		for(int i=0;i<teacherList.size();i++){
			System.out.println(teacherList.get(i));;
		}
		
		model.addAttribute("teacherList",teacherList);
		
		//自动获取班级列表
		List<Clazz> clazzList = clazzService.getClazzList(department, admissionYear);
		for(int i=0;i<clazzList.size();i++){
			System.out.println(clazzList.get(i));
		}
		model.addAttribute("clazzList",clazzList);
		
		//自动获取课程列表
		String currentSemesterStr = PropertyUtil.getProperty("config.currentSemester");
		int currentSemester = Integer.parseInt(currentSemesterStr);
		List<Course> courseList = manageCourService.getCourseList(currentSemester);
		model.addAttribute("courseList", courseList);
	
		List<SelectCourseResult> scHasChozenList = manageCourService.getHasChozenCourse();
		
		model.addAttribute("scHasChozenList", scHasChozenList);
		
		return role+"/course-selecting";
	}

	@RequestMapping(value = "/submitCourseSelecting", method = RequestMethod.POST)
	@ResponseBody
	public Message submitCourseSelecting(@RequestBody String selectCourseResult, Model model, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		
		logger.debug("==================ManageCourseController---submitCourseSelecting() [begin]==================");
		
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, SelectCourseResult.class);  
	    List<SelectCourseResult> selCourResultList = objectMapper.readValue(selectCourseResult, javaType);

	    manageCourService.addCourseSelecting(selCourResultList);
	    Message message = new Message();
	    message.setSuccess(true);
	    message.setSuccessMsg("分配课程成功！");
		logger.debug("==================ManageCourseController---submitCourseSelecting() [end]==================");
		return message;
	}
		
	@RequestMapping(value = "/getCourseList4Student", method = RequestMethod.POST)
	@ResponseBody
	public Message getclassList(@RequestBody String selectCourseResult, Model model, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		
		logger.debug("==================ManageCourseController---submitCourseSelecting() [begin]==================");
		
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, SelectCourseResult.class);  
	    List<SelectCourseResult> selCourResultList = objectMapper.readValue(selectCourseResult, javaType);

	    manageCourService.addCourseSelecting(selCourResultList);
	    Message message = new Message();
	    message.setSuccess(true);
	    message.setSuccessMsg("分配课程成功！");
		logger.debug("==================ManageCourseController---submitCourseSelecting() [end]==================");
		return message;
	}
	
}
