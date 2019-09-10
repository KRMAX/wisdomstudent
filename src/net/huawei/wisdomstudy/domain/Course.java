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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 课程POJO
 * @author cexo added on 2018-11-21
 * 唯一标示OID:id
 * 课程编号:courseNo
 * 课程名称:name
 * 所属学科:field
 * 所属学期:term
 * 是否结课:endingTag
 *  <CourseSelecting>
 */
@Entity
@Table(name = "t_course", catalog = "wisdomstudy")
public class Course implements Serializable {

	private static final long serialVersionUID = 219935985733283733L;

	private int id;
	
	private String courseNo;
	
	private String name;
	
	private String credit;
	
	private boolean elective;
	
	private Field field;
	
	private Term term;
	
	private boolean endingTag;
	
	private Set<CourseSelecting> courseSelectingList =new HashSet<CourseSelecting>();//选课列表
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	@Column(name = "course_no", nullable = false, unique = true)
	public String getCourseNo() {

		return courseNo;
	}

	public void setCourseNo(String courseNo) {

		this.courseNo = courseNo;
	}

	@Column(name = "name")
	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Column(name = "credit")
	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	@Column(name = "elective")
	public boolean isElective() {
		return elective;
	}

	public void setElective(boolean elective) {
		this.elective = elective;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "field_id", nullable = false)
	public Field getField() {

		return field;
	}

	public void setField(Field field) {

		this.field = field;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "term_id", referencedColumnName = "id", nullable = false)
	public Term getTerm() {

		return term;
	}

	public void setTerm(Term term) {

		this.term = term;
	}

	@Column(name = "ending_tag")
	public boolean isEndingTag() {

		return endingTag;
	}

	public void setEndingTag(boolean endingTag) {

		this.endingTag = endingTag;
	}

	@OneToMany(mappedBy="course",cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	public Set<CourseSelecting> getCourseSelectingList() {

		return courseSelectingList;
	}

	public void setCourseSelectingList(Set<CourseSelecting> courseSelectingList) {

		this.courseSelectingList = courseSelectingList;
	}

}
