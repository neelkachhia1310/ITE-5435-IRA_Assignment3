package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
	private final EnrollmentRepository enrollmentRepository;

	public EnrollmentService(EnrollmentRepository enrollmentRepository) {
		this.enrollmentRepository = enrollmentRepository;
	}

	public Enrollment enrollStudent(Enrollment enrollment) {
		enrollment.setStatus(Enrollment.Status.PENDING);
		return enrollmentRepository.save(enrollment);
	}
}
