package net.huawei.wisdomstudy.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.huawei.wisdomstudy.security.UserInfo;
import net.huawei.wisdomstudy.util.StandardPasswordEncoderForSha1;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	public static final String USERNAME = "j_username";
	public static final String PASSWORD = "j_password";
	
	private static Logger log = Logger.getLogger(AuthenticationFilter.class);
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	/*@Autowired
	private ILandingSysService landingService;*/
	
	public Authentication attemptAuthentication(HttpServletRequest request, 
			HttpServletResponse response) throws AuthenticationException {

		if(!request.getMethod().equals("POST")){
			throw new AuthenticationServiceException("Authentication method not supported: " 
					+ request.getMethod());
		}
		String username = this.obtainUsername(request);
		String password = this.obtainPassword(request);
		
		//加盐
		String sh1Password = password + "{" + username + "}";
		PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
		String result = passwordEncoder.encode(sh1Password);
		UserInfo userDetails = (UserInfo) userDetailsService.loadUserByUsername(username);
		System.out.println(userDetails.getAuthorities().toString());
		System.out.println("username="+username+"  password=" + password + "  password_para=" + userDetails.getPassword());
		System.out.println(userDetails.getUsername());
		System.out.println(result);
		/*this.checkValidateCode(request);*/
		if(!passwordEncoder.matches(userDetails.getPassword(), result) || userDetails == null){
			//System.out.println("用户名或密码错误！");
			throw new AuthenticationServiceException("用户名或密码错误！");
		}
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		this.setDetails(request, authRequest);
		Authentication authentication = null;
		try{
			authentication = this.getAuthenticationManager().authenticate(authRequest);
		}catch(Exception e){
			e.printStackTrace();
		}
		log.info("用户名"+username+"登录成功"+userDetails.toString());
		if(userDetails.getRole().getId()==1){
			request.getSession().setAttribute("adminUserId",userDetails.getUserid());
			request.getSession().setAttribute("adminUserName",userDetails.getUsername());
		}else if(userDetails.getRole().getId()==2){
			request.getSession().setAttribute("teacherUserId",userDetails.getUserid());
			request.getSession().setAttribute("teacherUserName",userDetails.getUsername());
		}else if(userDetails.getRole().getId()==3){
			request.getSession().setAttribute("studentUserId",userDetails.getUserid());
			request.getSession().setAttribute("studentUserName",userDetails.getUsername());
		}

		return authentication;
	}
	
}
