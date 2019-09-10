package net.huawei.wisdomstudy.controller;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huawei.wisdomstudy.controller.domain.QuestionQueryResult;
import net.huawei.wisdomstudy.domain.Homework;
import net.huawei.wisdomstudy.domain.Question;
import net.huawei.wisdomstudy.service.inter.IQuestionService;

@Controller
public class QuestionController {

	/** 日志对象 */
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	IQuestionService questService;
	
	/**
	 * 题库页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/{username}/questionfilter-{fieldId}-{knowledge}-{questionType}-{searchParam}-{page}.html", method = RequestMethod.GET)
	public String questionListFilterPage(Model model,
			@PathVariable String username,
			@PathVariable("fieldId") int fieldId,
			@PathVariable("knowledge") int knowledge,
			@PathVariable("questionType") int questionType,
			@PathVariable("searchParam") String searchParam,
			@PathVariable("page") int page) {
			
/*		
		QuestionFilter qf = new QuestionFilter();
		qf.setFieldId(fieldId);
		qf.setKnowledge(knowledge);
		qf.setQuestionType(questionType);
		if (searchParam.equals("0"))
			searchParam = "-1";
		qf.setSearchParam(searchParam);

		Page<Question> pageModel = new Page<Question>();
		pageModel.setPageNo(page);
		pageModel.setPageSize(20);

		List<Question> questionList = questionService.getQuestionList(
				pageModel, qf);

		String pageStr = PagingUtil.getPageBtnlink(page,
				pageModel.getTotalPage());

		model.addAttribute("fieldList", questionService.getAllField(null));

		model.addAttribute("knowledgeList",
				questionService.getKnowledgePointByFieldId(fieldId,null));

		model.addAttribute("questionTypeList",
				questionService.getQuestionTypeList());

		model.addAttribute("questionFilter", qf);
		model.addAttribute("questionList", questionList);
		model.addAttribute("pageStr", pageStr);
		
		//保存筛选信息，删除后跳转页面时使用
		model.addAttribute("fieldId", fieldId);
		model.addAttribute("knowledge", knowledge);
		model.addAttribute("questionType", questionType);
		model.addAttribute("searchParam", searchParam);*/

		for(int i = 0;i<1;i++)
		{
			System.out.println("realname = ");
			
		}
		return "admin/question-import1";
	}

	
	
	@RequestMapping(value = "/admin/question-crud", method = RequestMethod.GET)
	public String getQuestionCrud() {

		return "admin/question-crud";
	}
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getQuestionById")
	public JSONArray getQuestionById(@RequestParam(value="ids", required=false)String ids,
												  HttpServletRequest request) {

		logger.debug("=====QuestionController---getQuestionById() [begin]=====");

		JSONArray jsonArray=new JSONArray();
		Question question=new Question();

		System.out.println(ids);
        String[] strs=ids.split(",");
       for(String id:strs){
		   if(id!=null&&id!="") {
			   question = questService.getQuestion(Integer.parseInt(id));
			   if(question!=null){
				   JSONObject jsonObject =new JSONObject();
				   jsonObject.put("id",question.getId());
				   jsonObject.put("content",question.getContent());
				   jsonObject.put("questiontype",question.getQuestionType().getId());
				   jsonObject.put("questiontypestr",question.getQuestionTypeStr());
				   jsonObject.put("answer",question.getAnswer());
				   jsonArray.add(jsonObject);
			   }
		   }

	   }


        return jsonArray;

	}
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getQuestionDatagrid",  method = RequestMethod.POST)
	public Map<String,Object> getQuestionDatagrid(@RequestParam(value="rows", required=false)String rows,
			@RequestParam(value="page", required=false)String page,
			@RequestParam(value="knowledgePointId", required=true)int knowledgePointId,
			@RequestParam(value="questTypeId", required=false)Integer questTypeId,
			HttpServletRequest request) {  // rows:一页显示几行,page:第几页
		
		logger.debug("=====QuestionController---getQuestionDatagrid() [begin]=====");
		
		//转换为firstResult和maxResults
		//从datagrid的分页控件pager中返回两个参数
		int pageInt = (page==null||page=="0")?0:Integer.parseInt(page);
		int maxResults = (rows == null || rows=="0")?10:Integer.parseInt(rows);
		int firstResult = (pageInt - 1)*maxResults;
				
		Map<String,Object> map = null;
		map = new HashMap<String,Object>();
		map.put("total", 10);
		List<QuestionQueryResult> qlist = null;
		/*if(null!= questTypeId){
			qlist = questService.getQuestions(knowledgePointId, questTypeId);
		}else{*/
			qlist = questService.getQuestions(knowledgePointId);
		//}
		
		map.put("rows", qlist);
		
		logger.debug("=====QuestionController---getQuestionDatagrid() [end]=====");
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/{role}/{username}/getQuestionDatagridEdit")
	public Map<String,Object> getQuestionDatagridEdit(@RequestParam(value="rows", required=false)String rows,
			@RequestParam(value="page", required=false)String page,
			@RequestParam(value="ids", required=true)String ids,
			HttpServletRequest request) {  // rows:一页显示几行,page:第几页
		
		logger.debug("=====QuestionController---getQuestionDatagrid() [begin]=====");
		
		//转换为firstResult和maxResults
		//从datagrid的分页控件pager中返回两个参数
		int pageInt = (page==null||page=="0")?0:Integer.parseInt(page);
		int maxResults = (rows == null || rows=="0")?10:Integer.parseInt(rows);
		int firstResult = (pageInt - 1)*maxResults;
				
		Map<String,Object> map = null;
		map = new HashMap<String,Object>();
		
		List<QuestionQueryResult> qlist = new ArrayList<QuestionQueryResult>();
		  String[] strs=ids.split(",");
		  for(String str:strs) {
			  System.out.println(str);
			  QuestionQueryResult question=questService.getQuestionsById(Integer.parseInt(str));
			  if(question!=null) {
				  qlist.add(question);
			
			  }
			
		  }
		  System.out.println("11111");
			//将过滤后的值塞入返回得list
			List<QuestionQueryResult> rtnHwList=new ArrayList<QuestionQueryResult>();
			for (int j=firstResult;j<maxResults;j++){
				if(j<=qlist.size()-1){
					rtnHwList.add(qlist.get(j));
				}

			}
		map.put("total", rtnHwList.size());
		map.put("rows", rtnHwList);
		
		logger.debug("=====QuestionController---getQuestionDatagrid() [end]=====");
		return map;
	}
	
	
	/**
	 * 根据习题OID返回图片习题的内容 added on 2018-3-17
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/{role}/{username}/getQuestImg/{id}")  
    public void getQuestImg(@PathVariable("id")int id, 
    			HttpServletRequest request,HttpServletResponse response){
		FileInputStream in;  
	    response.setContentType("application/octet-stream;charset=UTF-8");  
	    try {  
	        //图片读取路径  
	       //in=new FileInputStream("E:\\picture\\"+photoName);
	       //  String imagePath = questService.getQuestion(id).getContent();
	       String imagePath = questService.getQuestion(id).getQc().getTitleImg();
	       in=new FileInputStream(imagePath);
	       int i=in.available();  
	       byte[]data=new byte[i];  
	       in.read(data);  
	       in.close();  
	          
	       //写图片  
	       OutputStream outputStream=new BufferedOutputStream(response.getOutputStream());  
	       outputStream.write(data);  
	       outputStream.flush();  
	       outputStream.close();  
	    }catch (Exception e) {
	       // TODO Auto-generated catch block  
	       e.printStackTrace();  
	   }
	}
	
	
}
