package com.khan.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khan.studentmanagement.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{

}
