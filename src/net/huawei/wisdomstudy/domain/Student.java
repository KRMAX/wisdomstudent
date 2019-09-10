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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 学生实体类
 * @author cexo added on 2017-1-16
 * 
 */
@Entity
@Table(name = "t_student", catalog = "wisdomstudy")
public class Student implements Serializable {

	private static final long serialVersionUID = -905130755630483918L;

	private int id;// 学生OID

	private String studentNo;// 学号

	private String name;// 学生姓名
	
	private String sex;//性别


	private User user;// 学生登录账户 外键 可为null

	private Clazz clazz;// 所在班级 外键 多对一单向关联

	//private Set<CourseSelecting> courseSelectingList = new HashSet<CourseSelecting>();//选课列表
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "student_no", nullable = false)
	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getName() {
		return name;
	}

	@Column(name = "name", nullable = false)
	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}


	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)//增加、删除、修改学生时级联处理User对象
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clazz_id", referencedColumnName = "id", nullable = false)
	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

	/*@OneToMany(mappedBy="course",cascade=CascadeType.ALL)
	public Set<CourseSelecting> getCourseSelectingList() {

		return courseSelectingList;
	}

	public void setCourseSelectingList(Set<CourseSelecting> courseSelectingList) {

		this.courseSelectingList = courseSelectingList;
	}*/
	
	@Override
	public String toString(){
		return "[id : " + id + ",studentNo : "+ studentNo + ",name : " + name + ",sex : " + sex
				+",user : " + user + ",clazz : " + clazz + "]";
		
	}

}
