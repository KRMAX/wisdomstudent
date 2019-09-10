package net.huawei.wisdomstudy.controller;

import java.util.Date;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.huawei.wisdomstudy.controller.domain.CacheDict;
import net.huawei.wisdomstudy.controller.domain.Message;

@Controller
public class HelloController {

	/** 日志对象 */
	private Log logger = LogFactory.getLog(this.getClass());
	
	/*@Autowired 
	IPersonService pservice;
	
	@Autowired
	IKeywordService keywordService;*/
	
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	/**/
	
	/**
	 * 处理班级 增加和修改
	 * @author cexo added on 2018-11-12
	 * @param keywordName
	 * @return
	 */
	@RequestMapping(value = "/disposeKeyword", produces = "application/json;charset=UTF-8")
	//手工添加的如：?id=id&clazzName,用@RequestParam，控件中的用@ModelAttribute获取
	public @ResponseBody Message disposeKeyword(@RequestParam(value="id", required=false)String id,
			@ModelAttribute("keywordName")String keywordName){
		logger.debug("=====ClazzController---disposeKeyword() [begin]=====");

		Message message = new Message();
		System.out.println("keywordName = " + keywordName);
		if(id != null && !id.equals("")){
			try{
				
				//clazzService.updateClazz(Integer.parseInt(id), clazzName, institute, admissionYear);
				message.setSuccess(true);
				message.setSuccessMsg("修改成功!");
			}catch(DataIntegrityViolationException e){
				message.setSuccess(false);
				message.setErrorMsg("关键字不能重复!");
			}catch(RuntimeException e){
				message.setSuccess(false);
				message.setErrorMsg("修改时发生异常!");
			}
		}else{
			try{
				//clazzService.addClazz(clazzName, institute, admissionYear);
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
		
		logger.debug("=====ClazzController---disposeKeyword() [end]=====");		
		
		return message;
	}
	
	@RequestMapping("/testJson")
	@ResponseBody
	public Message testJson() {
		
		Message message = new Message();
		message.setSuccess(true);
		System.out.println(CacheDict.dictItemMap.get(1).getItemName() + "==国家级");
		message.setSuccessMsg("成功！");
		message.setCreateDate(new Date());
		return message;
	}
	
	@RequestMapping("/testJson1")
	@ResponseBody
	public String testJson1() throws JsonProcessingException {
		
		Message message = new Message();
		message.setSuccess(true);
		message.setSuccessMsg("chenggong！");
		//message.setSuccessMsg(pservice.getPerson(1).getName());
		message.setCreateDate(new Date());
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(message);
		
	}
	
	@RequestMapping(value = "/searchDocument", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String searchDocument() {

		return "searchDocument";
	}
	
	@RequestMapping(value = "/keyword", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String keyword() {

		return "keyword";
	}
	
	/**
	 * 用户登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/user-login-page" }, method = RequestMethod.GET)
	public String loginPage(Model model, @RequestParam(value = "result", required = false, defaultValue = "") String result) {
		
		if("failed".equals(result)){
			model.addAttribute("result", "无效的用户名或者密码");
		}
		return "login";
	}
}
