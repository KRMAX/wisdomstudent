package net.huawei.wisdomstudy.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import net.huawei.wisdomstudy.dao.inter.IRedisDao;
import net.huawei.wisdomstudy.domain.Clazz;
import net.huawei.wisdomstudy.domain.Student;
import redis.clients.jedis.Jedis;
@Component("RedisService")
public class RedisService {
@Autowired
private IRedisDao rsDao;




	public void insertRdeis() {
	    List<Clazz> clazzList=rsDao.getClazzList();
	       @SuppressWarnings("resource")
	       //打开redis链接
		      Jedis jedis = new Jedis("localhost",6379);   
	          System.out.println("Connection to server sucessfully");  
	          //查看服务是否运行
       for(Clazz clazz:clazzList) {
     	  //先删除
     	  jedis.del(String.valueOf(clazz.getId())); 
     	  List<Student> stList=rsDao.getStudentListByClazzId(clazz.getId());
     	  for(Student st:stList) {
     		  //以list方式插入
	        	  jedis.rpush(String.valueOf(clazz.getId()),String.valueOf(st.getId()));
     	  }
       }
 
       jedis.close();
	}

}
