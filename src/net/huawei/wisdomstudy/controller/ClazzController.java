package net.huawei.wisdomstudy.controller;

import java.io.File;
import java.time.Year;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.huawei.wisdomstudy.cons.State;
import net.huawei.wisdomstudy.controller.domain.CacheDict;
import net.huawei.wisdomstudy.controller.domain.Combobox;
import net.huawei.wisdomstudy.controller.domain.Message;
import net.huawei.wisdomstudy.domain.Clazz;
import net.huawei.wisdomstudy.domain.Dictionary;
import net.huawei.wisdomstudy.service.inter.IClazzService;
import net.huawei.wisdomstudy.util.PropertyUtil;

@PropertySource(value = "classpath:config.properties",encoding = "utf-8")
@Controller
public class ClazzController {
	
	/** 允许上传的扩展名 */
	private static final String[] extensionPermit = { "xls" };
	
	// configProperties就是bean的id，[”]中的值就是要取的配置文件中的key，
	//@Value("#{configProperties['config.admissonYear']}")
	@Value("${config.admissonYear}")
	private String admissonYear;
	 
	@Autowired
	IClazzService clazzService;
	
	/** 日志对象 */
	private Log logger = LogFactory.getLog(this.getClass());
	
	@RequestMapping(value = "/{role}/{username}/manageClazz")
	public String manageClazz(@PathVariable String role, @PathVariable String username) {

		return role + "/clazz-crud";
	}

	/**
	 * 获取班级表中专业列表填充到Commobox控件中
	 * @author cexo added on 2018-11-14
	 * @return json[{id:1,text:"text"},...]
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getMajorCombobox")
	public List<Combobox> getMajorCombobox(@PathVariable String role, @PathVariable String username,
			@RequestParam(value="department", required=false)String department){
		logger.debug("==================ClazzController---getMajorCombobox() [begin]==================");
		
		List<Combobox> cbbList = new ArrayList<Combobox>();
		List<String> majorList ;
		if(department == null || department == "") {
			majorList = clazzService.getMajorList();
		}else
			majorList = clazzService.getMajorList(department); 
		for(int i=0; i<majorList.size(); i++) {
			Combobox cbox = new Combobox();
			cbox.setId(i + 1);
			cbox.setText(majorList.get(i));
			cbbList.add(cbox);
		}

		logger.debug("==================ClazzController---getMajorCombobox() [end]==================");
		
		return cbbList;
	}
	
	/**
	 * 获取班级表中专业列表填充到Commobox控件中
	 * @author cexo added on 2018-11-14
	 * @return json[{id:1,text:"text"},...]
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getDepartmentCombobox")
	public List<Combobox> getDepartmentCombobox(@PathVariable String role, @PathVariable String username) {
		logger.debug("==================ClazzController---getDepartmentCombobox() [begin]==================");

		List<Combobox> cbbList = new ArrayList<Combobox>();
		List<String> departmentList = clazzService.getDepartmentList();
		for(int i=0; i<departmentList.size(); i++) {
			Combobox cbox = new Combobox();
			cbox.setId(i + 1);
			cbox.setText(departmentList.get(i));
			cbbList.add(cbox);
		}

		logger.debug("==================ClazzController---getDepartmentCombobox() [end]==================");
		
		return cbbList;
	}
	
	/**
	 * 获取班级表中专业列表填充到Commobox控件中
	 * @author cexo added on 2018-11-14
	 * @return json[{id:1,text:"text"},...]
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getAdmissionYearCombobox")
	public List<Combobox> getAdmissionYearCombobox(@PathVariable String role, @PathVariable String username) {
		logger.debug("==================ClazzController---getAdmissionYearCombobox() [begin]==================");
		System.out.println("admissonYear" + admissonYear);
		List<Dictionary> dic = CacheDict.dictTypeMap.get(admissonYear);
		List<Combobox> cbbList = new ArrayList<Combobox>();
		for(Dictionary d : dic) {
			Combobox cbox = new Combobox();
			cbox.setId(d.getId());
			cbox.setText(d.getItemName());
			cbbList.add(cbox);
		}

		logger.debug("==================ClazzController---getAdmissionYearCombobox() [end]==================");
		
		return cbbList;
	}
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getClassCombobox")
	public List<Combobox> getClassCombobox(@PathVariable String role, @PathVariable String username) {
		logger.debug("==================ClazzController---getClassCombobox() [begin]==================");
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		List<Clazz> classList=clazzService.getClazzList("",year);
		List<Combobox> bjList = new ArrayList<Combobox>();
         for(Clazz clazz:classList){
			 Combobox cbox = new Combobox();
			 cbox.setId(clazz.getId());
			 cbox.setText(clazz.getClazzName());
			 bjList.add(cbox);
		 }

		logger.debug("==================ClazzController---getAdmissionYearCombobox() [end]==================");

		return bjList;
	}
	/**
	 * 初始化datagrid，将全部班级分页查出，填充datagrid对象
	 * @author cexo added on 2017-11-30
	 * @param page @RequestParam方式获取page,datagrid自带
	 * @param rows @RequestParam方式获取rows,datagrid自带
	 * @param //institute @RequestParam方式获取institute,可选参数
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getClazzDatagrid")
	public Map<String, Object> getClazzDatagrid(@RequestParam(value="rows", required=false)String rows,
			@RequestParam(value="page", required=false)String page,
			@PathVariable String role,
			@PathVariable String username,
			@RequestParam(value="major", required=false)String major,
			@RequestParam(value="clazzName", required=false)String clazzName,
			@RequestParam(value="admissionYear", required=false)String admissionYear,
			@RequestParam(value="department", required=false)String department,
			HttpServletRequest request) {  // rows:一页显示几行,page:第几页

		logger.debug("=====ClazzController---getClassDatagrid() [begin]=====");
		
		//转换为firstResult和maxResults
		//从datagrid的分页控件pager中返回两个参数
		int pageInt = (page==null||page=="0")?0:Integer.parseInt(page);
		int maxResults = (rows == null || rows=="0")?10:Integer.parseInt(rows);
		int firstResult = (pageInt - 1)*maxResults;
		System.out.println("major=" + major+";"+"admissionYear=" + admissionYear+";" + 
					role + ";" + username + ";"+ "department=" + department+";");
		Map<String,Object> map = null;

		Clazz clazz = new Clazz();
		if(clazzName != null && clazzName != "") {
			clazz.setClazzName(clazzName);
		}
		if(admissionYear != null) {
			clazz.setAdmissionYear(admissionYear);
		}
		if(major != null && major != "") {
			clazz.setMajor(major);
		}
		if(department != null && department != "") {
			clazz.setDepartment(department);
		}
		try{
			map = clazzService.getClazzesWithoutStu(clazz, firstResult, maxResults);
		}catch(RuntimeException e){
			logger.error("执行getClazzes(clazz, firstResult, maxResults)方法时发生异常：" + e.getMessage());
			map = new HashMap<String,Object>();
			map.put("total", 0);
			map.put("rows", null);
		}
		
		logger.debug("=====ClazzController---getClassDatagrid() [end]=====");
		return map;
	}
	
	
	/**
	 * 处理班级 增加和修改
	 * @author cexo added on 2017-11-30
	 * @param clazzName
	 * @return
	 */
	@RequestMapping(value = "/{role}/{username}/disposeClazz")
	//手工添加的如：?id=id&clazzName,用@RequestParam，控件中的用@ModelAttribute获取
	public @ResponseBody Message disposeClazz(@PathVariable String role,
			@PathVariable String username,
			@RequestParam(value="id", required=false)Integer id,
			@ModelAttribute("clazzName")String clazzName,
			@RequestParam("departmentAdd")String department,
			@RequestParam("majorAdd")String major,
			@RequestParam("admissionYearAdd")String admissionYear){
		logger.debug("=====ClazzController---disposeClazz() [begin]=====");

		Message message = new Message();
		System.out.println("id=	" + id +";id.type="+id.getClass()+";major="+ major+";department = " + 
				department +";admissionYear = " + admissionYear);
		Clazz clazz = new Clazz();
		clazz.setClazzName(clazzName);
		clazz.setDepartment(department);
		clazz.setMajor(major);
		clazz.setAdmissionYear(admissionYear);
		if(id != null && !id.equals("")){
			try{
				clazz.setId(id.intValue());
				clazzService.updateClazz(clazz);
				message.setSuccess(true);
				message.setSuccessMsg("修改成功!");
			}catch(DataIntegrityViolationException e){
				message.setSuccess(false);
				message.setErrorMsg("班级名称不能重复!");
			}catch(RuntimeException e){
				message.setSuccess(false);
				message.setErrorMsg("修改时发生异常!");
			}
		}else{
			try{
				clazzService.addClazz(clazz);
				message.setSuccess(true);
				message.setSuccessMsg("添加成功!");
			}catch(DataIntegrityViolationException e){
				logger.debug(e.getRootCause());
				logger.debug(e.getMessage());
				message.setSuccess(false);
				message.setErrorMsg("添加班级失败，班级名称已存在");
			}catch(RuntimeException e){
				message.setSuccess(false);
				message.setErrorMsg("修改时发生异常");
			}
		}

		logger.debug("=====ClazzController---disposeClazz() [end]=====");		
		
		return message;
	}
	
	/*@RequestMapping(value = "/admin/destroyClazz",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	//手工添加的如：?id=id&clazzName,用@RequestParam，控件中的用@ModelAttribute获取
	public @ResponseBody String destroyClazz(@ModelAttribute("id")int id){
		
		logger.debug("=====ClazzController---deleteClazz() [begin]=====");

		Message message = new Message();
		try{
			clazzService.deleteClazz(id);
			message.setSuccess(true);
			message.setSuccessMsg("删除成功!");
		}catch(DataIntegrityViolationException e){
			message.setSuccess(false);
			message.setErrorMsg("删除失败!");
		}
		
		String json = JSON.toJSONString(message,true);

		logger.debug("=====ClazzController---deleteClazz() [end]=====");		
		
		return json;
	}
	*/
	/**
	 * 将页面的题库名称、题目类型、章节号、知识点描述与文件进行比较
	 * 如果与文件中的表头一致，则将此文件存入数据库中。
	 * @param afile 上传的文件
	 * @param questType
	 * @return Map<String, Object> JSON
	 */
	@RequestMapping(value = "/{role}/{username}/uploadClazz", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadClazz(@RequestParam("afile") CommonsMultipartFile afile,
			@PathVariable String role,
			@PathVariable String username,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.info("==================UploadController---fileUpload() [begin]==================");
		/**
		 * 第二种方式：
		 * String curProjectPath = session.getServletContext().getRealPath("/");
		 * curProjectPath = curProjectPath.replaceAll("20171101", "upload");
		 * String saveDirectoryPath = curProjectPath + "/" + uploadFolder + "/" + uploadQuestionFolder;
		 */

		String saveDirectoryPath = PropertyUtil.getProperty("clazzUploadDir").replaceAll("\\\\", "/");
		File saveDirectory = new File(saveDirectoryPath);
		if (!saveDirectory.exists()) {
			saveDirectory.mkdirs();
        }
		logger.debug("Project real path [" + saveDirectory.getAbsolutePath() + "]");

		String originalFilename = null;

		// 判断文件是否存在
		if (afile.isEmpty()) {
			logger.debug("上传的文件为空");
			return State.ERROR.toMap();
		}else {
			String fileName = afile.getOriginalFilename();
			String fileExtension = FilenameUtils.getExtension(fileName);
			if (!ArrayUtils.contains(extensionPermit, fileExtension)) {
				return State.NO_SUPPORT_EXTENSION.toMap();
			}
			originalFilename = afile.getOriginalFilename();
			System.out.println("文件原名: " + originalFilename);
			System.out.println("文件名称: " + afile.getName());
			System.out.println("文件长度: " + afile.getSize());
			System.out.println("文件类型: " + afile.getContentType());
			File file = new File(saveDirectory, fileName);
			afile.transferTo(file);

			try {
			Map<String, Object>map = clazzService.importClazzExcel(file);
			logger.debug("==================UploadController---fileUpload() [end]==================");
			return map;
			}catch(Exception e) {
				System.out.println(e.getCause());
				return State.ERROR.toMap();
			}		

		}

	}
	public static void main(String[] args) {
		String clazzUploadDir = PropertyUtil.getProperty("clazzUploadDir").replaceAll("\\\\", "/");
		System.out.println(clazzUploadDir);
	}
}
