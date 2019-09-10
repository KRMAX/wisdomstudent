package net.huawei.wisdomstudy.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 班级POJO
 * @author cexo updated on 2018-11-12
 * 唯一标示OID:id
 * 班级名称:clazzName
 * 班级人数:clazzSize
 * 是否结业:graduated
 * 入学年度:admissionYear
 * 所在年级:grade
 * 所学专业:major
 * 所在系：department
 */
@Entity
@Table(name = "t_clazz", catalog = "wisdomstudy")
public class Clazz implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6200321626810531922L;

	private int id;

	private String clazzName;
	
	private String admissionYear;
	
	private String major;
	
	private String department;
	
	private Set<Student> student = new HashSet<Student>();
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "clazz_name", nullable = false, unique = true)
	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	@Column(name = "admission_year", nullable = true)
	public String getAdmissionYear() {

		return admissionYear;
	}

	public void setAdmissionYear(String admissionYear) {

		this.admissionYear = admissionYear;
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

	//删除一个班级时，会删除所有学生
	@OneToMany(mappedBy = "clazz", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public Set<Student> getStudent() {

		return student;
	}

	public void setStudent(Set<Student> student) {

		this.student = student;
	}
}
