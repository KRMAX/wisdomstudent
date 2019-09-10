package net.huawei.wisdomstudy.service.system;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * @author cexo added on 2018-6-22
 * 数据字典缓存监听器（在web容器启动成功的时候、进行缓存）
 *
 */
public class DictionaryCacheListener  implements javax.servlet.ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {        
    }
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        
        System.out.println("++++++++++++++++++　　数据字典即将缓存　　+++++++++++++++++++++");
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
        DictionaryService dc = (DictionaryService) webApplicationContext.getBean("DictionaryService");
        dc.getCacheDic();   // 调用数据字典Manager的一个方法来缓存
    }

}