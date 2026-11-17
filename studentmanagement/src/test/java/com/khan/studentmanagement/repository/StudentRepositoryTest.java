
package com.khan.studentmanagement.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.khan.studentmanagement.entity.Student;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void saveStudentTest() {

        Student student = new Student();
        student.setName("Rahul");
        student.setEmail("rahul@test.com");
        student.setAge(25);
        student.setPhoneNumber("9876543210");

        Student savedStudent = studentRepository.save(student);

        assertNotNull(savedStudent.getId());
        assertEquals("Rahul", savedStudent.getName());
    }

    @Test
    void findStudentByIdTest() {

        Student student = new Student();
        student.setName("Amit");
        student.setEmail("amit@test.com");
        student.setAge(25);
        student.setPhoneNumber("9876543210");

        Student savedStudent = studentRepository.save(student);

        Student foundStudent = studentRepository.findById(savedStudent.getId())
                .orElse(null);

        assertNotNull(foundStudent);
        assertEquals("Amit", foundStudent.getName());
    }

    @Test
    void findAllStudentsTest() {

        Student student1 = new Student();
        student1.setName("Rahul");
        student1.setEmail("rahul1@test.com");
        student1.setAge(25);
        student1.setPhoneNumber("9876543210");

        Student student2 = new Student();
        student2.setName("Amit");
        student2.setEmail("amit1@test.com");
        student2.setAge(26);
        student2.setPhoneNumber("9876543211");

        studentRepository.save(student1);
        studentRepository.save(student2);

        assertTrue(studentRepository.findAll().size() >= 2);
    }

    @Test
    void deleteStudentTest() {

        Student student = new Student();
        student.setName("Vijay");
        student.setEmail("vijay@test.com");
        student.setAge(25);
        student.setPhoneNumber("9876543210");

        Student savedStudent = studentRepository.save(student);

        studentRepository.deleteById(savedStudent.getId());

        assertFalse(studentRepository.existsById(savedStudent.getId()));
    }
}
