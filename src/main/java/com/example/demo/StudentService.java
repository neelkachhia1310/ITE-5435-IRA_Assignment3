package com.example.demo;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public Student registerStudent(Student student) {
		student.setPassword(student.getPassword());
		return studentRepository.save(student);
	}

	public Student updateStudent(Student student) {
		Optional<Student> existingStudent = studentRepository.findById(student.getStudentId());

		if (existingStudent.isPresent()) {
			Student updatedStudent = existingStudent.get();
			updatedStudent.setFirstName(student.getFirstName());
			updatedStudent.setLastName(student.getLastName());
			updatedStudent.setAddress(student.getAddress());
			updatedStudent.setCity(student.getCity());
			updatedStudent.setPostalCode(student.getPostalCode());

			return studentRepository.save(updatedStudent);
		}

		return null;
	}

	public Optional<Student> findByUserName(String userName) {
		return studentRepository.findByUserName(userName);
	}
}
