package net.huawei.wisdomstudy.service.inter;

import java.io.File;
import java.util.List;
import java.util.Map;

import net.huawei.wisdomstudy.domain.Clazz;

public interface IClazzService {

	/**
	 * 添加班级信息
	 * @author cexo added on 2018年11月11日
	 * @param clazz
	 * @return void
	 */
	public void addClazz(Clazz clazz);
	
	/**
	 * 根据班级OID获取班级
	 * @author cexo added on 2018年11月11日
	 * @param id
	 * @return Clazz
	 */
	public Clazz getClazz(int id);
	
	/**
	 * 修改班级信息
	 * @author cexo added on 2018年11月11日
	 * @param clazz
	 * @return void
	 */
	public void updateClazz(Clazz clazz);
	
	/**
	 * 删除指定id的班级
	 * 删除此班级时将会删除选课信息表中所有此班级有关的选课记录
	 * @author cexo addedy on 2018-11-11
	 * @param id
	 * @return void
	 */
	//public void deleteClazz(int id);
	
	public List<Clazz> getClazzList(String department, String admissionYear);
	
	/**
	 * 到Clazz表中查找并分页查询班级 
	 * @author cexo added on 2018-11-11
	 * @param firstResult 起始位置
	 * @param maxResults  获取的数量
	 * @return Map<String,Object>
	 */
	public Map<String,Object> getClazzes(int firstResult, int maxResults);
	
	/**
	 * 到Clazz表中查找并分页查询班级，班级不关联学生对象
	 * @author cexo added on 2018年11月11日
	 * @param clazz
	 * @param firstResult
	 * @param maxResults
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getClazzesWithoutStu(Clazz clazz, int firstResult, int maxResults);
	
	/**
	 * 到Clazz表及选课表中查找此role下所有已分配的班级，并分页查询班级，班级不关联学生对象
	 * @author cexo added on 2019年6月4日
	 * @param role
	 * @param username
	 * @param clazz
	 * @param firstResult
	 * @param maxResults
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getClazzesWithoutStu(String role,String username, Clazz clazz, int firstResult, int maxResults);
	
	/**
	 * 获取专业列表
	 * @author cexo added on 2018年11月12日
	 * @return List<String>
	 */
	public List<String> getMajorList();
	
	/**
	 * 
	 * @author cexo added on 2018年11月16日
	 * @param department
	 * @return List<String>
	 */
	public List<String> getMajorList(String department);
	/**
	 * 获取所属系列表
	 * @author cexo added on 2018年11月12日
	 * @return List<String>
	 */
	public List<String> getDepartmentList();
	
	/**
	 * 
	 * @author cexo added on 2018年11月18日
	 * @param file
	 * @return Map<String,Object>
	 */
	public Map<String, Object> importClazzExcel(File file);

}
