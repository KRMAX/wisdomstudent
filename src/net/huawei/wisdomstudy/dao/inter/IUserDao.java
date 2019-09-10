package net.huawei.wisdomstudy.dao.inter;

import net.huawei.wisdomstudy.domain.Role;
import net.huawei.wisdomstudy.domain.User;

public interface IUserDao {
	/**
	 * 查找当前登陆者的用户名密码是否正确
	 * @param username
	 * @return User
	 */
	public User getUserByName(String username);

	/**
	 * 根据用户表id查询对应的Role对象
@
	 * @param userId
	 * @return Role
	 */
	public Role getRoleByUserId(int userId);
	

	/**
	 * 创建登录账户
	 * @author cexo on 2016-4-15
	 * @return
	 */
	public void createUser(int sid,String username,String passsword,String realName, int role);
	
}
