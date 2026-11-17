package com.khan.studentmanagement.entity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Student {
	
	@ManyToMany
	@JoinTable(
			name="student_subject",
			joinColumns=@JoinColumn(name="student_id"),
			inverseJoinColumns=@JoinColumn(name="subject_id"))
	private Set<Subject> subjects=new HashSet<>();
	
	@OneToMany(mappedBy="student",cascade = CascadeType.ALL)
	private List<Course> courses=new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="student_card_id")
	private StudentCard studentCard;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="Student name is required")
	@Size(min=3, max=50, message="Name must be between 3 and 50 characters")
	@Column(name="name",nullable=false,length=100)
	private String name;
	
	@NotBlank(message="Email is required")
	@Email(message="Please provide a valid email")
	@Column(name="email",unique=true)
	private String email;
	
	@NotNull(message="Age is required")
	private Integer age;
	
	@NotEmpty(message="Phone number is required")
	private String phoneNumber;
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(Long id, String name, String email,Integer age,String phoneNumber) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.age=age;
		this.phoneNumber=phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", email=" + email + "]";
	}

	public StudentCard getStudentCard() {
		return studentCard;
	}

	public void setStudentCard(StudentCard studentCard) {
		this.studentCard = studentCard;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
		
		if(courses!=null) {
			for(Course course:courses) {
				course.setStudent(this);
			}
		}
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
