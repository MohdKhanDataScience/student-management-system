package com.khan.studentmanagement.controller;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khan.studentmanagement.entity.Student;
import com.khan.studentmanagement.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	private StudentService stdService;
	
	
	public StudentController(StudentService stdService) {
		super();
		this.stdService = stdService;
	}
	
	@PostMapping("/addData")
	public Student addStudent(@Valid @RequestBody Student student) {
		return stdService.addStudent(student);
	}
	
	@PutMapping("/{studentId}/subjects")
	public Student assignSubjects(@PathVariable Long studentId,@RequestBody Set<Long>subjectIds) {
		return stdService.assignSubject(studentId, subjectIds);
	}
	
	@GetMapping("/getData")
	public List<Student> getAllStudents() {
		return stdService.getAllStudents();
	}
	
	@GetMapping("/getById/{id}")
	public Student getByID(@PathVariable Long id) {
		return stdService.getStudentById(id);
	}
	
	 // Pagination
    @GetMapping("/page")
    public Page<Student> getStudentsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return stdService.getStudentsWithPagination(pageable);
    }

    // Sorting
    @GetMapping("/sort")
    public List<Student> getStudentsWithSorting(
            @RequestParam(defaultValue = "name") String field) {

        return stdService.getStudentsWithSorting(field);
    }
    
	//Custom method Controller
	@GetMapping("/getByEmail/{email}")
	public Student getByEmail(@PathVariable String email) {
		return stdService.getStudentByEmail(email);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteById(@PathVariable Long id) {
		boolean deleted=stdService.deleteStudentById(id);
		if(deleted) {
			return "Successfully deleted";
		}
		else {
			return "Not found";
		}
	}
	@PutMapping("/update/{id}")
	public Student updateById(@PathVariable Long id, @RequestBody Student student) {
		return stdService.updateStudentById(id, student);
	}
	
	//Testing generic exception
	@GetMapping("/test-error")
	public String testError() {
		throw new RuntimeException("Testing generic Exception");
	}
}
