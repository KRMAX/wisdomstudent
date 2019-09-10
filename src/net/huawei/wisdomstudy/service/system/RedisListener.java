package net.huawei.wisdomstudy.service.system;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 
 * @author cexo added on 2018-6-22
 * redis缓存
 *
 */
public class RedisListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

 
	          WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
	          RedisService rs=(RedisService) webApplicationContext.getBean("RedisService");
	          rs.insertRdeis();
	}

}
