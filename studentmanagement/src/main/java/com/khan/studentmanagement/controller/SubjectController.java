package com.khan.studentmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khan.studentmanagement.entity.Subject;
import com.khan.studentmanagement.repository.SubjectRepository;
import com.khan.studentmanagement.service.SubjectService;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
	
	private SubjectService subjectService;

	public SubjectController(SubjectService subjectService) {
		super();
		this.subjectService = subjectService;
	}
	
	@PostMapping
	public Subject addSubject(@RequestBody Subject subject) {
		return subjectService.addSubject(subject);
	}
	
	@GetMapping("/getById/{id}")
	public Subject getById(@PathVariable Long id) {
		return subjectService.getSubjectById(id);
	}
}
