package net.huawei.wisdomstudy.service.inter;

import java.util.List;
import java.util.Map;

import net.huawei.wisdomstudy.controller.domain.HwCreatorParam;
import net.huawei.wisdomstudy.controller.domain.Message;
import net.huawei.wisdomstudy.controller.domain.TreeNode;
import net.huawei.wisdomstudy.domain.Clazz;
import net.huawei.wisdomstudy.domain.Homework;
import net.huawei.wisdomstudy.domain.Student;

import javax.servlet.http.HttpSession;

public interface IHomeworkService {

	/**
	 * 根据节点id和层级返回此节点的子节点集合
	 * @author cexo addedy on 2018-3-13
	 * @param id 传入的节点id
	 * @param level 传入的节点层级
	 * @param landerID 教师工号
	 * @return List<Course>
	 */
	public List<TreeNode> getkptreeNodeById(int id, String level, int landerID);
	
	/**
	 * 将生成的homework保存到数据库中
	 * @author cexo added on 2018-7-4
	 * @param HwCreatorParam
	 * @return Message
	 */
	public Message addHomework(HwCreatorParam hmArrJson, HttpSession session);

	/**
	 * 将生成的homework保存到数据库中
	 * @author cexo added on 2018-7-4
	 * @param HwCreatorParam
	 * @return Message
	 */
	public Message editHomework(HwCreatorParam hmArrJson, HttpSession session);
	
	/**
	 * 修改班级信息
	 * @author cexo added on 2018年11月11日
	 * @param clazz
	 * @return void
	 */
	public void updateHomework(HwCreatorParam hmArrJson,HttpSession session);
	public Homework getHomeworkById(int id);
	public Message deleteHomeworkById(int id);
	/**
	 * 到t_homework表中查找并分页查询作业生成的内容
	 * @author cexo added on 2018年11月11日
	 * @param Homework
	 * @param firstResult
	 * @param maxResults
	 * @return Map<String,Object>
	 */
	public List<Homework> getHomeWork(Homework homework, int firstResult, int maxResults);
}
