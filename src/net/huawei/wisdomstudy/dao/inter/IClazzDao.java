package net.huawei.wisdomstudy.dao.inter;

import java.util.List;
import java.util.Map;

import net.huawei.wisdomstudy.domain.Clazz;

public interface IClazzDao {

	/**
	 * 添加班级信息
	 * @author cexo added on 2018年11月16日
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
	 * 根据班级名称获取班级
	 * @author cexo added on 2018年11月19日
	 * @param clazzName
	 * @return Clazz
	 */
	public Clazz getClazz(String clazzName);
	/**
	 * 修改班级信息
	 * @author cexo added on 2018年11月16日
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
	
	/**
	 * 到Clazz表中查找并分页查询班级 
	 * @author cexo added on 2018-11-11
	 * @param firstResult 起始位置
	 * @param maxResults  获取的数量
	 * @return Map<String,Object>
	 */
	public Map<String,Object> getClazzes(int firstResult, int maxResults);
	
	/**
	 * 分配课程时调用 根据入学年度查询所有班级
	 * @author cexo added on 2018-11-11
	 * @param admissionYear
	 * @return List<Clazz>
	 */
	public List<Clazz> getClazzes(int admissionYear);
	
	/**
	 * 根据clazz对象中赋值情况查询对应的Clazz对象列表
	 * @author cexo added on 2018年11月11日
	 * @param clazz
	 * @param firstResult
	 * @param maxResults
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getClazzes(Clazz clazz, int firstResult, int maxResults);
	
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
	 * @return
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
	 * 选课时获取班级列表
	 * @author cexo added on 2019年5月17日
	 * @param department
	 * @param admissionYear
	 * @return List<Clazz>
	 */
	public List<Clazz> getClazzList(String department, String admissionYear);
	
	public List<Clazz> getClazzList();

}
