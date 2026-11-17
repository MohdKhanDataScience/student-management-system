package com.khan.studentmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.khan.studentmanagement.entity.Student;
import com.khan.studentmanagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
	
	@Mock
	private StudentRepository studentRepository;
	
	@InjectMocks
	private StudentService studentService;
	
	@Test
	void getAllStudentTest() {
		
		List<Student> students=new ArrayList<>();
		
		students.add(new Student());
		students.add(new Student());
		
		when(studentRepository.findAll()).thenReturn(students);
		
		List<Student>result=studentService.getAllStudents();
		
		assertEquals(2, result.size());
		
		verify(studentRepository,times(1)).findAll();
	}

	@Test
	void getStudentById() {
		
		Student student=new Student();
		student.setId(1L);
		when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
		
		Student result=studentService.getStudentById(1L);
		
		assertEquals(1L, result.getId());
		
		verify(studentRepository,times(1)).findById(1L);
	}
	
	@Test
	void deleteStudentTest() {
		
		when(studentRepository.existsById(1L)).thenReturn(true);
		
		studentService.deleteStudentById(1L);
		
		verify(studentRepository,times(1)).deleteById(1L);
	}
	
	@Test
	void addStudentTest() {
		
		Student student=new Student();
		
		student.setName("Rahul");
		student.setEmail("rahul@gmail.com");
		
		when(studentRepository.save(student)).thenReturn(student);
		
		Student result=studentService.addStudent(student);
		
		assertEquals("Rahul", result.getName());
		assertEquals("rahul@gmail.com", result.getEmail());
		
		verify(studentRepository,times(1)).save(student);
		
		
		
		
	}
}
