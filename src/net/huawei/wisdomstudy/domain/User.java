package net.huawei.wisdomstudy.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_user",catalog="wisdomstudy")
public class User implements Serializable
{
	
	private static final long serialVersionUID = -159095508804126069L;

	private int userId;
	
	private String username;
	
	private String realname;
	
	private String password;
	
	private int role;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getUserId()
	{

		return userId;
	}

	public void setUserId(int userId)
	{

		this.userId = userId;
	}

	@Column(name="username", nullable=false, unique=true)
	public String getUsername()
	{

		return username;
	}

	public void setUsername(String username)
	{

		this.username = username;
	}

	@Column(name="realname", nullable=false)
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	@Column(name="password", nullable=false)
	public String getPassword()
	{

		return password;
	}

	public void setPassword(String password)
	{

		this.password = password;
	}
	
	@Column(name = "role",nullable = false)
	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String toString() { 
		return "[username: " + username + ", password: " + password + "]"; 
	}


	
}
