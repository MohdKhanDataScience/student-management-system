package com.khan.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khan.studentmanagement.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	Student findByEmail(String email);
	
}
