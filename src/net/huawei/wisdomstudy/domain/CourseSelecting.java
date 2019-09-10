package net.huawei.wisdomstudy.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 选课POJO
 * @author cexo added on 2017-11-8
 * id:OID
 * 课程对象:course
 * 班级对象:clazz
 * 任课教师:teacher
 */
@Entity
@Table(name = "t_course_selecting", catalog = "wisdomstudy")
public class CourseSelecting implements Serializable{

	private static final long serialVersionUID = -5257869178023417091L;
	
	private int id;

	private Course course;
	
	private Clazz clazz;
	
	private Teacher teacher;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	public Course getCourse() {

		return course;
	}

	public void setCourse(Course course) {

		this.course = course;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clazz_id", nullable = false)
	public Clazz getClazz() {

		return clazz;
	}

	public void setClazz(Clazz clazz) {

		this.clazz = clazz;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id", nullable = false)
	public Teacher getTeacher() {

		return teacher;
	}

	public void setTeacher(Teacher teacher) {

		this.teacher = teacher;
	}
	
}
