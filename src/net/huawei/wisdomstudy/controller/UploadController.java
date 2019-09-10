package net.huawei.wisdomstudy.controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.huawei.wisdomstudy.cons.State;
import net.huawei.wisdomstudy.controller.domain.Combobox;
import net.huawei.wisdomstudy.service.inter.IChapterService;
import net.huawei.wisdomstudy.service.inter.IExcelImportService;
import net.huawei.wisdomstudy.util.PropertyUtil;

@Controller
public class UploadController implements Serializable {

	private static final long serialVersionUID = 4001589390617412711L;

	/** 日志对象 */
	private Log logger = LogFactory.getLog(this.getClass());

	/** 上传目录名 */
	private static final String uploadFolder = "upload";

	/** 上传目录名 */
	private static final String uploadQuestionFolder = "question";
	
	/** 允许上传的扩展名 */
	private static final String[] extensionPermit = { "xls" };

	/**
	 * Spring MVC的Controller用的是Servlet的思想，单例性能好，但线程不安全。
	 * 有三种方式解决线程安全性
	 * 1、设置Controller的Scope注解，但是除了默认的单例外，性能会下降，
	 * 建议用默认单例方式，实现要共享对象属性。
	 * 	
	 * @Controller
		@RequestMapping("/fui")
		public class FuiController extends SpringController {
		这么定义的话就是单例

		@Controller
		@Scope("prototype")
		@RequestMapping("/fui")
		public class FuiController extends SpringController {
		每次都创建

		@Controller
		@Scope("session")
		@RequestMapping("/fui")
		public class FuiController extends SpringController {
		一个会话创建一个
		
	  * 2、用ThreadLocal保护。
	  
	  * 3、用SynchronizedMap和ConcurrentHashMap方法。
	  * SynchronizedMap性能低于ConcurrentHashMap，Collections为HashMap提供了一个并发版本SynchronizedMap。
	  * java5中新增了ConcurrentMap接口和它的一个实现类ConcurrentHashMap。ConcurrentHashMap提供了和Hashtable
	  * 以及SynchronizedMap中所不同的锁机制。Hashtable中采用的锁机制是一次锁住整个hash表，从而同一时刻只能由一个
	  * 线程对其进行操作；而ConcurrentHashMap中则是一次锁住一个桶。ConcurrentHashMap默认将hash表分为16个桶，
	  * 诸如get,put,remove等常用操作只锁当前需要用到的桶。这样，原来只能一个线程进入，现在却能同时有16个写线程执行，
	  * 并发性能的提升是显而易见的。
	**/
	//private static Map<Integer, byte[]> imgList =  Collections.synchronizedMap(new HashMap<Integer, byte[]>());
	//private static Map<Integer, byte[]> imgMap =  new ConcurrentHashMap<Integer, byte[]>();
	
	@Autowired
	IChapterService chapterService;
	@Autowired
	IExcelImportService exlService;
	
	@RequestMapping(value = "/{role}/{username}/questImport", method = RequestMethod.GET)
	public String questImport(@PathVariable String role, @PathVariable String username) {

		return role + "/question-import2";
	}

	/**
	 * 获取题库名称列表填充到Commobox控件中
	 * @return json[{id:1,text:"text"},...]
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getFieldCombobox", method = RequestMethod.GET)
	public List<Combobox> getFieldCombobox() {

		logger.debug("==================UploadController---getFieldCombobox() [begin]==================");
		
		List<Combobox> cbbList = chapterService.getFieldList();

		logger.debug("==================UploadController---getFieldCombobox() [end]==================");
		
		return cbbList;
	}
	
	/**
	 * 将页面的题库名称、题目类型、章节号、知识点描述与文件进行比较
	 * 如果与文件中的表头一致，则将此文件存入数据库中。
	 * @param afile 上传的文件
	 * @param questType
	 * @return Map<String, Object> JSON
	 */
	@RequestMapping(value = "/{role}/{username}/fileUpload", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> fileUpload(@RequestParam("afile") CommonsMultipartFile afile,
			@ModelAttribute("field")String field, @RequestParam("fieldId")int fieldId,
			@RequestParam("questTypeId")int questTypeId,@ModelAttribute("questType")String questType,
			@ModelAttribute("chapter")String chapter,  @RequestParam("chapterId")int chapterId,
			@ModelAttribute("knowledgePoint")String knowledgePoint,
			@RequestParam("kpId")int kpId, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.info("==================UploadController---fileUpload() [begin]==================");

		//先判断4个下拉框是否都取值
		System.out.println("----------"+fieldId+questType+chapterId+knowledgePoint);
		
		/**
		 * 第二种方式：
		 * String curProjectPath = session.getServletContext().getRealPath("/");
		 * curProjectPath = curProjectPath.replaceAll("20171101", "upload");
		 * String saveDirectoryPath = curProjectPath + "/" + uploadFolder + "/" + uploadQuestionFolder;
		 */

		String saveDirectoryPath = PropertyUtil.getProperty("basicUploadDir").replaceAll("\\\\", "/");
		saveDirectoryPath = saveDirectoryPath + "/" + uploadQuestionFolder;
		//saveDirectoryPath = "/" + uploadQuestionFolder;
		saveDirectoryPath += "/" + fieldId + "/" + chapterId + "/" + kpId;
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

			Map<String, Object>map = exlService.importQuestionExcel(file, knowledgePoint, chapter, questTypeId, 
					questType, field,kpId, saveDirectoryPath);
			logger.debug("==================UploadController---fileUpload() [end]==================");
			return map;
		}
	}


	

}
