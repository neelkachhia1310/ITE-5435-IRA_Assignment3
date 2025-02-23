package com.example.demo;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/students")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("student", new Student());
		return "register";
	}

	@PostMapping("/register")
	public String registerStudent(@ModelAttribute Student student, HttpSession session) {
		studentService.registerStudent(student);
		session.setAttribute("loggedInUser", student);

		return "redirect:/enrollment/register";
	}

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		model.addAttribute("message", "");
		return "login";
	}

	@PostMapping("/login")
	public String handleLogin(@ModelAttribute Student student, Model model, HttpSession session) {
		Optional<Student> existingStudent = studentService.findByUserName(student.getUserName());

		if (existingStudent.isPresent() && existingStudent.get().getPassword().equals(student.getPassword())) {
			session.setAttribute("loggedInUser", existingStudent.get());
			return "redirect:/students/welcome";
		} else {
			model.addAttribute("message", "Invalid username or password");
			return "login";
		}
	}

	@GetMapping("/welcome")
	public String showWelcomePage(Model model, HttpSession session) {
		Student loggedInUser = (Student) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/students/login";
		}

		model.addAttribute("student", loggedInUser);
		return "welcome";
	}

	@GetMapping("/update")
	public String showUpdateForm(Model model, HttpSession session) {
		Student loggedInUser = (Student) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/students/login";
		}

		model.addAttribute("student", loggedInUser);
		return "updateProfile";
	}

	@PostMapping("/update")
	public String updateStudentProfile(@ModelAttribute Student student, HttpSession session) {
		Student updatedStudent = studentService.updateStudent(student);
		session.setAttribute("loggedInUser", updatedStudent);
		return "redirect:/students/welcome";
	}
}
