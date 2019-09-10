package net.huawei.wisdomstudy.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 教师实体类
 * @author cexo
 *
 */
@Entity
@Table(name="t_teacher",catalog="wisdomstudy")
public class Teacher implements Serializable{

	private static final long serialVersionUID = -1476030686714888603L;

	private int id;
	
	private String empId;//教师工号
	
	private String name;//教师名称
	
	private String major;//教师专业
	
	private String department;//教师所属系
	
	private User user;//用户表ID，FK

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="empID",nullable=false,unique=true)
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Column(name="name",nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 一对一外键关联，教师可以没有登录账户，因此userId可以为空
	 * @return User
	 */
	@OneToOne(cascade=CascadeType.ALL, optional = true,fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString(){
		return "[id : " + id + ",empId : "+ this.empId + ",name : " + name
				+",user : " + user + "]";
		
	}

	@Column(name = "major")
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Column(name = "department")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
