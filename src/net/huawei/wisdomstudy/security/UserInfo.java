package net.huawei.wisdomstudy.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import net.huawei.wisdomstudy.domain.Role;

public class UserInfo extends User{

	private static final long serialVersionUID = 1L;
	
	private int userid;
	
	private Role role;
	
	private String trueName;
	
	private String rolesName;
	
	private int landerId;//role为教师，则为教师OID，role为admin，则为管理员OID

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role roleList) {
		this.role = roleList;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getRolesName() {
		return rolesName;
	}

	public void setRolesName(String rolesName) {
		this.rolesName = rolesName;
	}


	public UserInfo(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}

	public int getLanderId() {

		return landerId;
	}

	public void setLanderId(int landerId) {

		this.landerId = landerId;
	}


}
