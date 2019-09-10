package net.huawei.wisdomstudy.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * <br>
 * <b>类描述:</b>
 * 
 * <pre>
 * 用户登录主页的映射
 * </pre>
 * 
 * @see
 * @since
 */
@Controller
public class UserController {
	protected static final String ERROR_MSG_KEY = "errorMsg";
	
	/**
	 * 获取保存在Session中的用户对象
	 * 
	 * @param request
	 * @return
	 
	protected User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(
				CommonConstant.USER_CONTEXT);
	}
   
	*
	 * 保存用户对象到Session中
	 * @param request
	 * @param user
	 *
	protected void setSessionUser(HttpServletRequest request,User user) {
		request.getSession().setAttribute(CommonConstant.USER_CONTEXT,
				user);
	}
	**/


	/**
	 * 网站首页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String homePage(Model model, HttpServletRequest request) {
		return "redirect:home";
	}

	/**
	 * 管理员登陆
	 * 登陆时确认是管理员身份，则进入管理员home目录，默认转到习题列表页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/{role}/{username}/home" }, method = RequestMethod.GET)
	public String homePage(@PathVariable String role,
								@PathVariable String username, Model model, HttpServletRequest request) {
		
		return role + "/home";
	}
	
	@RequestMapping(value = { "/{role}/{username}/searchDocument" }, method = RequestMethod.GET)
	public String teachersearchDocumentPage(@PathVariable String role,
								@PathVariable String username, Model model, HttpServletRequest request) {
		
		return role + "/searchDocument";
	}
	
	/**
	 * 判断不同角色返回的页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "home" }, method = RequestMethod.GET)
	public String directToBaseHomePage(Model model, HttpServletRequest request) {

		String result = request.getParameter("result");
		if ("failed".equals(result)) {
			model.addAttribute("result_msg", "登陆失败");
		}

		if (SecurityContextHolder.getContext().getAuthentication() == null){
			//this.appendBaseInfo(model);
			return "redirect:user-login-page";
		}
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().endsWith("anonymousUser")){
			//this.appendBaseInfo(model);
			return "redirect:user-login-page";
		}
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<? extends GrantedAuthority> grantedAuthorities = userDetails.getAuthorities();
		String toUrl = userDetails.getUsername();
		
		
		if (grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			toUrl = "admin/"+toUrl+"/home";
			return "redirect:"+toUrl;
		} else if (grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_TEACHER"))) {
			toUrl = "teacher/"+toUrl;
			return "redirect:"+toUrl+"/home";
		} else if (grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
			//this.appendBaseInfo(model);
			toUrl = "student/"+toUrl+"/home";
			return "redirect:"+toUrl;
		} else {
			return "user-login-page";
		}

	}
	
	
}

