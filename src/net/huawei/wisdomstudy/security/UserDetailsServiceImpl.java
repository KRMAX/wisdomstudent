package net.huawei.wisdomstudy.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import net.huawei.wisdomstudy.cons.CommonConstant;
import net.huawei.wisdomstudy.dao.inter.ITeacherDao;
import net.huawei.wisdomstudy.dao.inter.IUserDao;
import net.huawei.wisdomstudy.domain.Role;
import net.huawei.wisdomstudy.domain.Teacher;
import net.huawei.wisdomstudy.domain.User;

@Component("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

	//扩展User类，即实现了UserDetails接口，可作为一个被Spring Security识别的UserDetails对象
	private UserInfo userInfo;
	
	@Autowired
	private IUserDao userDao;//利用Dao层获取实体对象User
	
	@Autowired
	private ITeacherDao teacherDao;
	
	public UserInfo getUserInfo() {
		return userInfo;
	}


	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


	public IUserDao getUserDao() {
		return userDao;
	}


	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = userDao.getUserByName(username);
		if(user==null){
			throw new UsernameNotFoundException("用户未找到 !");
		}
		
		//这里对Role与User的表关系可以优化，暂且如此
		Role role = userDao.getRoleByUserId(user.getUserId());
		String roles = role.getAuthority();
		String rolesName = role.getName();
		List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
		userInfo = new UserInfo(username,user.getPassword(),true,true,true,true,authorities);
		
		userInfo.setRole(role);
		userInfo.setUserid(user.getUserId());
		userInfo.setTrueName(user.getRealname());
		userInfo.setRolesName(rolesName);

		if(role.getId() == CommonConstant.USER_ROLE_TEACHER){
			Teacher teacher = teacherDao.getTeacherByUserId(user.getUserId());
			userInfo.setLanderId(teacher.getId());
		}
		
		return userInfo;
	}

}
