package com.example.demo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/enrollment")
public class EnrollmentController {

	private final EnrollmentService enrollmentService;
	private final ProgramRepository programRepository;

	public EnrollmentController(EnrollmentService enrollmentService, ProgramRepository programRepository) {
		this.enrollmentService = enrollmentService;
		this.programRepository = programRepository;
	}

	@GetMapping("/register")
	public String enrollStudent(Model model) {
		List<Program> programs = programRepository.findAll();
		model.addAttribute("student", new Student());
		model.addAttribute("programs", programs);
		model.addAttribute("enrollment", new Enrollment());
		return "payment";
	}

	@PostMapping("/register")
	public String processEnrollment(@RequestParam("programCode") String programCode,
			@RequestParam("cardName") String cardName, @RequestParam("cardNumber") String cardNumber,
			@RequestParam("expiryDate") String expiryDate, @RequestParam("cvv") String cvv, Model model,
			HttpSession session) {

		Student loggedInUser = (Student) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/students/login"; // Ensure user is logged in
		}

		Optional<Program> selectedProgram = programRepository.findById(programCode);
		if (selectedProgram.isEmpty()) {
			model.addAttribute("errorMessage", "Invalid program selected. Please try again.");
			return "payment";
		}

		Program program = selectedProgram.get();

		// System.out.println("Processing Payment for: " + cardName + " | Card: " +
		// cardNumber);

		Enrollment enrollment = new Enrollment();
		enrollment.setStudent(loggedInUser);
		enrollment.setProgram(program);
		enrollment.setAmountPaid(program.getFee());
		enrollment.setStartDate(LocalDate.now());
		enrollment.setStatus(Enrollment.Status.PENDING);

		enrollmentService.enrollStudent(enrollment);

		model.addAttribute("confirmationMessage", "Payment Successful! Your application is being processed.");
		return "confirmation";
	}

}
