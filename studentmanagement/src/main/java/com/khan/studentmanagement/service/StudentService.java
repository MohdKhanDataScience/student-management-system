package com.khan.studentmanagement.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.khan.studentmanagement.entity.Student;
import com.khan.studentmanagement.entity.Subject;
import com.khan.studentmanagement.exception.DuplicateEmailException;
import com.khan.studentmanagement.exception.StudentNotFoundException;
import com.khan.studentmanagement.repository.StudentRepository;
import com.khan.studentmanagement.repository.SubjectRepository;

@Service
public class StudentService {
	
	
	private StudentRepository stdRepository;

	private SubjectRepository subjectRepository;
	
	public StudentService(StudentRepository stdRepository, SubjectRepository subjectRepository) {
		super();
		this.stdRepository = stdRepository;
		this.subjectRepository = subjectRepository;
	}

	public Student assignSubject(Long studentId, Set<Long>subjectIds) {
		Student student=stdRepository.findById(studentId)
				.orElseThrow(()-> new RuntimeException("Student Not found"));
		
		Set<Subject>subjects=new HashSet<>();
		for(Long subjectId:subjectIds) {
			Subject subject=subjectRepository.findById(subjectId)
					.orElseThrow(()-> new RuntimeException("Subject Not found"));
			
			subjects.add(subject);
		}
		student.setSubjects(subjects);
		
		return stdRepository.save(student);
	}
	
	 // Pagination
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Page<Student> getStudentsWithPagination(Pageable pageable) {
        return stdRepository.findAll(pageable);
    }

    // Sorting
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Student> getStudentsWithSorting(String field) {
        return stdRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }


	@PreAuthorize("hasRole('ADMIN')")
	public Student addStudent(Student student) {
		System.out.println("Courses recieved :"+student.getCourses().size());
		if(stdRepository.findByEmail(student.getEmail())!=null) {
			throw new DuplicateEmailException("Email already exist");
		}
		return stdRepository.save(student);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public List<Student> getAllStudents(){
		return stdRepository.findAll();
	}
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public Student getStudentById(long id) {
		return stdRepository.findById(id)
				.orElseThrow(()->new StudentNotFoundException(
				"Student Not Found with ID :"+ id));
		
	}
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteStudentById(Long id) {
		if(stdRepository.existsById(id)) {
			stdRepository.deleteById(id);
			return true;
		}
		return false;
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public Student updateStudentById(Long id, Student updatedStudent) {
		return stdRepository.findById(id).map(student->{
			student.setName(updatedStudent.getName());
			student.setEmail(updatedStudent.getEmail());
			return stdRepository.save(student);
		}).orElse(null);	
	}
	//custom method
	public Student getStudentByEmail(String email) {
		return stdRepository.findByEmail(email);
	}

}
