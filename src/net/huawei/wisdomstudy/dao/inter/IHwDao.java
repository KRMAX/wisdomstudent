package net.huawei.wisdomstudy.dao.inter;

import net.huawei.wisdomstudy.controller.domain.HwCreatorParam;
import net.huawei.wisdomstudy.domain.Homework;
import net.huawei.wisdomstudy.domain.HomeworkidClassId;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 作业增删改查
 * @author cexo added on 2018-7-4
 *
 */
public interface IHwDao {
	
	public void addHomework(Homework hw);
	public void addHomeworkidClassid(HomeworkidClassId hc);
	public int getMaxId();
	public Homework getHomeworkById(int id);
}
