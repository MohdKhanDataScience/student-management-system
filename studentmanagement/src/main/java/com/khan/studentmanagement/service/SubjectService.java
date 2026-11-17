package com.khan.studentmanagement.service;

import org.springframework.stereotype.Service;

import com.khan.studentmanagement.entity.Subject;
import com.khan.studentmanagement.exception.SubjectNotFoundException;
import com.khan.studentmanagement.repository.SubjectRepository;

@Service
public class SubjectService {
	
	private SubjectRepository subjectRepository;

	public SubjectService(SubjectRepository subjectRepository) {
		super();
		this.subjectRepository = subjectRepository;
	}
	
	public Subject addSubject(Subject subject) {
		return subjectRepository.save(subject);
	}

	public Subject getSubjectById(Long id) {
		return subjectRepository.findById(id)
				.orElseThrow(()-> new SubjectNotFoundException(
						"Subject Not Found with ID :" + id));
	}
}
