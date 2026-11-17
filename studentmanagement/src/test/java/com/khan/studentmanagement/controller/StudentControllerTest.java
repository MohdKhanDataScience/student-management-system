package com.khan.studentmanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.khan.studentmanagement.entity.Student;
import com.khan.studentmanagement.repository.AppUserRepository;
import com.khan.studentmanagement.service.AppUserService;
import com.khan.studentmanagement.service.CustomUserDetailsService;
import com.khan.studentmanagement.service.JwtService;
import com.khan.studentmanagement.service.RevokedTokenService;
import com.khan.studentmanagement.service.StudentService;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class StudentControllerTest {

	@Autowired
	private MockMvc mockMVC;
	
	@MockitoBean
	private AppUserRepository appUserRepository;
	
	@MockitoBean
	private StudentService studentService;
	
	@MockitoBean
	private AppUserService appUserService;
	
	@MockitoBean
	private AuthenticationManager authenticationManager;
	
	@MockitoBean
	private JwtService jwtService;
	
	@MockitoBean
	private CustomUserDetailsService customUserDetailsService;
	
	@MockitoBean
	private RevokedTokenService revokedTokenService;
	
	@Test
	void getAllStudentTest() throws Exception{
		List<Student> students=new ArrayList<>();
		
		Student student=new Student();
		
		student.setId(1L);
		student.setName("Rahul");
		student.setEmail("rahul@gmail.com");
		
		students.add(student);
		
		when(studentService.getAllStudents()).thenReturn(students);
		
		mockMVC.perform(get("/students"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].name").value("Rahul"))
		.andExpect(jsonPath("$[0].email").value("rahul@gmail.com"));
	}
	
	@Test
	void addStudentTest() throws Exception {

	    Student student = new Student();
	    student.setId(1L);
	    student.setName("Rahul");
	    student.setEmail("rahul@gmail.com");

	    when(studentService.addStudent(any(Student.class))).thenReturn(student);

	    mockMVC.perform(post("/students")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("""
	                    {
	                        "name": "Rahul",
	                        "email": "rahul@gmail.com"
	                    }
	                    """))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.name").value("Rahul"))
	            .andExpect(jsonPath("$.email").value("rahul@gmail.com"));
	}
	
	@Test
	void getStudentByIdTest() throws Exception {

	    Student student = new Student();
	    student.setId(1L);
	    student.setName("Rahul");
	    student.setEmail("rahul@gmail.com");

	    when(studentService.getStudentById(1L)).thenReturn(student);

	    mockMVC.perform(get("/students/1"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id").value(1))
	            .andExpect(jsonPath("$.name").value("Rahul"));
	}
	
	@Test
	void deleteStudentTest() throws Exception {

	    mockMVC.perform(delete("/students/1"))
	            .andExpect(status().isOk());

	    verify(studentService, times(1)).deleteStudentById(1L);
	}
	
}
