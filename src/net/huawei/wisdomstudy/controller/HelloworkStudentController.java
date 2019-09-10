package net.huawei.wisdomstudy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huawei.wisdomstudy.domain.HomeWorkStudent;
import net.huawei.wisdomstudy.service.inter.IHomeworkStudentService;
import net.huawei.wisdomstudy.service.inter.IStudentService;
@Transactional(readOnly=true)
@Controller
public class HelloworkStudentController {
	@Autowired
	 IHomeworkStudentService hwstService;
	@Autowired
	IStudentService stService;
	/** 日志对象 */
	private Log logger = LogFactory.getLog(this.getClass());
	@RequestMapping(value = "/{role}/{username}/getHomeWorkStudent")
	public @ResponseBody Map<String,Object> getHomeWorkStudent(@RequestParam(value="rows", required=false)String rows,
			@RequestParam(value="page", required=false)String page,
			@RequestParam(value="homeworkid", required=false)String homeworkid,
			@RequestParam(value="clazzid", required=false)String clazzid,
			@RequestParam(value="studentNo", required=false)String studentNo,
			@RequestParam(value="studentName", required=false)String studentName,
			HttpServletRequest request,HttpSession session){
		int clazzid2int=0;
		int homeworkid2int=Integer.parseInt(homeworkid);
		if(StringUtils.isNotBlank(clazzid)) {
			clazzid2int=Integer.parseInt(clazzid);
		}
		
		int pageInt = (page==null||page=="0")?0:Integer.parseInt(page);
		int maxResults = (rows == null || rows=="0")?10:Integer.parseInt(rows);
		int firstResult = (pageInt - 1)*maxResults;
		List<HomeWorkStudent> hwstList=hwstService.getHomeworkStudentByHomeworkIdAndClazzid(homeworkid2int,clazzid2int);
		List<HomeWorkStudent> filterHwList=new ArrayList<HomeWorkStudent>();
		List<HomeWorkStudent> rtnHwList=new ArrayList<HomeWorkStudent>();
		
		//先将条件过滤一遍，因为根据班级学生人数查询，所以最多只有一个班级的数据，不会很慢
		for(HomeWorkStudent hwst:hwstList) {
			if(StringUtils.isNotBlank(studentNo)&&StringUtils.isNotBlank(studentName)) {
				if(hwst.getStudentNo().indexOf(studentNo)>=0&&hwst.getStudentName().indexOf(studentName)>=0) {
					filterHwList.add(hwst);
				}
			}
			else if(StringUtils.isNotBlank(studentNo)&&StringUtils.isBlank(studentName)) {
				if(hwst.getStudentNo().indexOf(studentNo)>=0) {
					filterHwList.add(hwst);
				}
			}else if(StringUtils.isBlank(studentNo)&&StringUtils.isNotBlank(studentName)) {
				if(hwst.getStudentName().indexOf(studentName)>=0) {
					filterHwList.add(hwst);
				}
			}else {
				filterHwList.add(hwst);
			}
		}
		for (int j=firstResult;j<maxResults;j++){
			if(j<=filterHwList.size()-1){
				rtnHwList.add(filterHwList.get(j));
				
			}

		}
		//根据成绩，冒泡排序
           int length=rtnHwList.size();
            for (int i = length - 1; i > 0; i--) {
                for (int j = length - 1; j > length - 1 - i; j--) {
                    if (rtnHwList.get(j).getScore() > rtnHwList.get(j-1).getScore()) {
                    	HomeWorkStudent temp = rtnHwList.get(j);
                    	rtnHwList.set(j, rtnHwList.get(j-1));
                    	rtnHwList.set(j-1,temp);
                    }
                }
            }
      
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", hwstList.size());
		map.put("rows", rtnHwList);
        
				return map;
		
	}
}
