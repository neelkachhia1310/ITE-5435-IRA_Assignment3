package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/programs")
public class ProgramController {
	private final ProgramService programService;

	public ProgramController(ProgramService programService) {
		this.programService = programService;
	}

	@GetMapping
	public String showPrograms(Model model) {
		model.addAttribute("programs", programService.getAllPrograms());
		return "programs";
	}
}
