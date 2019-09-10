package net.huawei.wisdomstudy.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huawei.wisdomstudy.cons.CommonConstant;
import net.huawei.wisdomstudy.controller.domain.Combobox;
import net.huawei.wisdomstudy.controller.domain.Message;
import net.huawei.wisdomstudy.controller.domain.TreeNode;
import net.huawei.wisdomstudy.domain.Chapter;
import net.huawei.wisdomstudy.domain.Field;
import net.huawei.wisdomstudy.domain.KnowledgePoint;
import net.huawei.wisdomstudy.security.UserInfo;
import net.huawei.wisdomstudy.service.inter.IChapterService;
import net.huawei.wisdomstudy.service.inter.IHomeworkService;
//import net.huawei.wisdomstudy.service.inter.IHomeworkService;
/*import net.huawei.wisdomstudy.util.ComboboxJsonUtil;*/
import net.huawei.wisdomstudy.util.TreeJsonUtil;

@Controller
public class KnowledgePointController implements Serializable {

	private static final long serialVersionUID = 825885369049960398L;

	/** 日志对象 */
	private Log logger = LogFactory.getLog(this.getClass());

	/*@Autowired
	IKnowledgePointService klpService;*/

	@Autowired
	IChapterService chapService;
	
	@Autowired
	IHomeworkService homeworkService;

	/**
	 * 初始化datagrid，将全部知识点查出，填充datagrid对象 added by cexo on 2016-4-14
	 * 
	 * @return json
	 */
/*	@ResponseBody
	@RequestMapping(value = "/admin/getAllKnowPointDatagrid")
	public String getAllKnowPointDatagrid(@RequestParam(value="rows", required=false)String rrr,
			HttpServletRequest request) {  // HttpServletRequest等价于@RequestParam()

		// System.out.println("field = "+field);
		logger.debug("=====KnowledgePointController---getAllKnowPointDatagrid() [begin]=====");

		String page = request.getParameter("page");//第几页
		String rows = request.getParameter("rows");//一页显示几行
		
		//转换为firstResult和maxResults
		int pageInt = (page==null||page=="0")?0:Integer.parseInt(page);
		int maxResults = (rows == null || rows=="0")?10:Integer.parseInt(rows);
		int firstResult = (pageInt - 1)*maxResults;
		
		//从datagrid的分页控件pager中返回两个参数
		System.out.println("maxResults = "+ maxResults);
		System.out.println("firstResult = "+ firstResult);
		System.out.println("@RequestParam方式获取的page为："+page);
		
		Map<String,Object> map = null;
		try{
			map = klpService.getKnowledgePoints(firstResult, maxResults);
		}catch(RuntimeException e){
			
			logger.error("执行getKnowledgePoints(firstResult, maxResults)方法时发生异常：" + e.getMessage());
			map = new HashMap<String,Object>();
			map.put("total", 0);
			map.put("rows", null);
		}
		String json = null;
		json = JSON.toJSONString(map, true);

		logger.debug("=====KnowledgePointController---getAllKnowPointDatagrid() [end]=====");
		return json;

	}*/

	/**
	 * 按查询条件将知识点查出，填充datagrid对象 added by cexo on 2016-4-14
	 * 
	 * @return json
	 */
	/*@ResponseBody
	@RequestMapping(value = "/admin/getKnowPointDatagrid")
	public String getKnowPointDatagrid(@ModelAttribute("field") String field,
			@ModelAttribute("questType") String questType,
			@ModelAttribute("chapter") String chapter, 
			@ModelAttribute("chapterId") int chapterId,
			@RequestParam(value="rows", required=false)String rows, 
			@RequestParam(value="page", required=false)String page) {

		//转换为firstResult和maxResults
		int pageInt = (page==null||page=="0")?0:Integer.parseInt(page);
		int maxResults = (rows == null || rows=="0")?10:Integer.parseInt(rows);
		int firstResult = (pageInt - 1)*maxResults;

		logger.debug("=====KnowledgePointController---getKnowPointDatagrid() [begin]=====");
		logger.debug("field="+field+";questType="+questType+";chapter="+chapter+";chapterId="+chapterId);
		Map<String,Object> map = null;
		try{
			map = klpService.getKnowledgePointListByChapter(
					field, questType, chapter, chapterId,firstResult, maxResults);
		}catch(RuntimeException e){		
			logger.debug(e.getCause());
			logger.error("执行getKnowledgePointListByChapter(field, questType, " +
					"chapter, firstResult, maxResults)方法时发生异常：" + e.getMessage());
			map = new HashMap<String,Object>();
			map.put("total", 0);
			map.put("rows", "");
		}
		
		String json = null;
		json = JSON.toJSONString(map, true);

		logger.debug("=====KnowledgePointController---getKnowPointDatagrid() [end]=====");
		return json;

	}*/

	/**
	 * 到Chapter表中查找并返回所有章节列表 added by cexo on 2019-5-28
	 * @return List<Chapter>
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getChapterWithAll", method = RequestMethod.POST)
	public List<Chapter> getChapterWithAll(@ModelAttribute("field")int field,
			@ModelAttribute("withAllTag")String withAllTag) {

		logger.debug("=====KnowledgePointController---getChapterWithAll() [begin]=====");

		List<Chapter> chapterList = new ArrayList<Chapter>();
		if(withAllTag.equals("1")){
			Chapter c = new Chapter();
			c.setId(0);//id为0对应的查询条件为全部
			c.setChapterName(CommonConstant.SEARCH_CONDITION_ALL);
			chapterList.add(c);
		}
		List<String> chapterNameList = new ArrayList<String>();
		if(withAllTag.equals("") || !withAllTag.equals("0"))
			chapterNameList.add("全部");
		/*//如果传入的questType为“全部”，则不动态传回章节列表
		if(text.equals(CommonConstant.SEARCH_CONDITION_ALL)){
			String json = "";
			return json;
		}
		chapterNameList.addAll(chapService.getChapterNameList(field));
		String json = ComboboxJsonUtil.ListToComboboxJson(chapterNameList);
		
		//如果传入的questType为“--全部--”，则不动态传回章节列表
		if(field.equals(CommonConstant.SEARCH_CONDITION_ALL)){
			json = "";
			return json;
		}
		*/
		try{
			chapterList.addAll(chapService.getChapterListByField(field));
		}catch(DataAccessException e){
			logger.debug(e.getMessage());
			logger.debug(e.getRootCause());
		}
		
		logger.debug("=====KnowledgePointController---getChapterWithAll() [end]=====");
		return chapterList;
	}

	/**
	 * 到Chapter表中查找并返回所有题目类型列表 added by cexo on 2016-4-14
	 * 
	 * @return String JSON
	 * 
	 * 没用了#####
	 */
	/*@ResponseBody
	@RequestMapping(value = "/admin/getQuestTypeFromChap", method = RequestMethod.POST)
	public String getQuestTypeFromChap(@ModelAttribute("text")String text, @ModelAttribute("withAllTag")String withAllTag) {

		logger.debug("=====KnowledgePointController---getQuestTypeFromChap() [begin]=====");

		List<String> questTypeList = new ArrayList<String>();
		
		if(withAllTag.equals("") || !withAllTag.equals("0"))
			questTypeList.add(CommonConstant.SEARCH_CONDITION_ALL);
		//如果传入的field为“全部”，则不动态传回题目类型列表
		if(text.equals(CommonConstant.SEARCH_CONDITION_ALL)){
			String json = "";
			return json;
		}

		String json = ComboboxJsonUtil.ListToComboboxJson(questTypeList);
		logger.debug("=====KnowledgePointController---getQuestTypeFromChap() [end]=====");
		return json;
	}*/

	/**
	 * 用于显示从全部到知识点的tree结构中的根节点，根节点为课程节点
	 * 返回根节点
 	 * @author cexo added by on 2019-6-16
	 * @return String JSON
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getkptreeCourseNode/{courseId}", produces = "text/html;charset=UTF-8")//, method = RequestMethod.POST
	public String getkptreeCourseNode(@PathVariable String role,
										@PathVariable int courseId) {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode();
		node.setLevel(CommonConstant.KP_TREE_COURSE_LEVEL);
		node.setId(courseId);
		node.setLeaf(false);
		node.setName(CommonConstant.KP_TREE_ROOT_TEXT);
		treeNodeList.add(node);
		String json =TreeJsonUtil.treeNodeListToJson(treeNodeList);
		return json;
	}
	/**
	 * 用于异步显示从全部到知识点的tree结构，知识点层级全为叶子节点
	 * 按OID及对应层级返回所有直接子节点
 	 * @author cexo added by on 2019-6-16
	 * @return String JSON
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getkptreeNodeByIdAndLevel", produces = "text/html;charset=UTF-8")//, method = RequestMethod.POST
	public String getkptreeNodeById(@ModelAttribute("id")int id, @ModelAttribute("level")String level,
									@PathVariable String role) {

		logger.debug("=====KnowledgePointController---getkptreeNodeById() [begin]=====");

		UserInfo userDetails = (UserInfo) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();
		
		int empID = userDetails.getLanderId();
		System.out.println("empID=" + empID);
		List<TreeNode> treeNodeList	= homeworkService.getkptreeNodeById(id, level, empID);
		
		String json =TreeJsonUtil.treeNodeListToJson(treeNodeList);
		logger.debug("=====KnowledgePointController---getkptreeNodeById() [end]=====");
		return json;
	}
	
	
	/**
	 * 添加知识点
	 * added by cexo on 2016-4-14
	 * @return String JSON
	 */
	/*@RequestMapping(value = "/admin/addKnowledgePoint", method = RequestMethod.POST)
	public @ResponseBody String addKnowledgePoint(@ModelAttribute("knowledgeDescAdd")String knowledgeDesc, 
			@ModelAttribute("chapNameAdd")String chapName,
			@ModelAttribute("questTypeAdd")String questType,
			@ModelAttribute("fieldAdd")String field) {

		logger.debug("=====KnowledgePointController---addKnowledgePoint() [begin]=====");
		System.out.println("页面传入的knowledgeDesc = "+knowledgeDesc +";chapName = "+chapName+";questType = "+questType+";field="+field);
		String msg = klpService.addKnowledgePoint(knowledgeDesc, chapName, questType, field);
		Message message = new Message();
		if(msg.equals("success")){
			message.setSuccess(true);
			
		}else{
			message.setSuccess(false);
			message.setErrorMsg(msg);
		}
		String json = JSON.toJSONString(message,true);
		System.out.println(json);
		logger.debug("=====KnowledgePointController---addKnowledgePoint() [end]=====");		
		
		return json;
	}*/
	
	/**
	 * 编辑知识点
	 * @ModelAttribute("id")主键传入
	 * added by cexo on 2016-4-16
	 * @return String JSON
	 */
	/*@RequestMapping(value = "/admin/editKnowledgePoint", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String editKnowledgePoint(@ModelAttribute("id")int id,
			@ModelAttribute("knowledgePointDesc")String knowledgePointDesc) {

		Message message = new Message();
		
		logger.debug("=====KnowledgePointController---editKnowledgePoint() [begin]=====");
		System.out.println("页面传入的id = "+id);
		try{
			klpService.updateKnowledgePoint(id, knowledgePointDesc);
			message.setSuccess(true);
		
		}catch(DataIntegrityViolationException e){
			message.setSuccess(false);
			message.setErrorMsg("知识点描述不能重复");
		}catch(RuntimeException e){
			message.setSuccess(false);
			message.setErrorMsg("捕获异常");
		}

		String json = JSON.toJSONString(message);
		logger.debug("=====KnowledgePointController---editKnowledgePoint() [end]=====");
		return json;		
	}*/
	
	
	/**
	 * 删除知识点
	 * @ModelAttribute("id")主键传入
	 * added by cexo on 2016-4-16
	 * @return String JSON
	 */
	/*@RequestMapping(value = "/admin/destroyKnowledgePoint", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String destroyKnowledgePoint(@ModelAttribute("id")int id) {

		Message message = new Message();
		
		logger.debug("=====KnowledgePointController---destroyKnowledgePoint() [begin]=====");
		System.out.println("页面传入的id = "+id);
		try{
			klpService.deleteKnowledgePoint(id);
			message.setSuccess(true);
		
		}catch(DataIntegrityViolationException e){
			message.setSuccess(false);
			message.setErrorMsg("删除失败");
		}catch(DataAccessException e){
			message.setSuccess(false);
			message.setErrorMsg("捕获异常");
		}

		String json = JSON.toJSONString(message);
		logger.debug("=====KnowledgePointController---destroyKnowledgePoint() [end]=====");
		return json;		
	}*/
	
	/**
	 * 按chapterId到KnowledgePoint表中查找并返回所有知识点列表 added by cexo on 2019-5-30
	 * 
	 * @return List<Combobox>
	 */
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getKnowledgePointCombobox", method = RequestMethod.POST)
	public List<Combobox> getKnowledgePointCombobox(@ModelAttribute("chapId")int chapId) {

		logger.debug("=====KnowledgePointController---getKnowledgePointCombobox() [begin]=====");
		//kpList.addAll();
		List<Combobox> cboxList = chapService.getKnowledgePointByChapId(chapId);
	
		logger.debug("=====KnowledgePointController---getKnowledgePointCombobox() [end]=====");
		return cboxList;
	}
}
