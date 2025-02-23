package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProgramService {
	private final ProgramRepository programRepository;

	public ProgramService(ProgramRepository programRepository) {
		this.programRepository = programRepository;
	}

	public List<Program> getAllPrograms() {
		return programRepository.findAll();
	}
}
