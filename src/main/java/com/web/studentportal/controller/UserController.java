package com.web.studentportal.controller;

import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.studentportal.model.Admin;
import com.web.studentportal.model.Course;
import com.web.studentportal.model.User;
import com.web.studentportal.repository.AdminRepository;
import com.web.studentportal.repository.CourseRepository;
import com.web.studentportal.repository.UserRepository;
import com.web.studentportal.service.CourseService;

@Controller
public class UserController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AdminRepository adminRepo;

	@Autowired
	private CourseRepository courseRepo;

	static long userId = 0;

	@GetMapping({ "/", "/home" })
	public String viewHomePage(User user) {
		return "index";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "sign_up";
	}

	@PostMapping("/process_register")
	public String processRegister(User user) {
		User usersDefined = userRepo.findByEmail(user.getEmail());
		if (usersDefined == null) {
			user.setRegDate(Calendar.getInstance().getTime());
			userRepo.save(user);
			return "register_success";
		} else {
			return "already_register";
		}
	}

	@PostMapping("/process_login")
	public String processLogin(User user, @RequestParam("admin") Boolean admin, HttpServletRequest request) {
		if (admin) {
			Admin usersDefined = adminRepo.findAdminByEmail(user.getEmail());
			if (usersDefined == null || !user.getPassword().equals(usersDefined.getPassword())) {
				return "invalid_user";
			} else {
				return "redirect:/courses";
			}

		} else {
			User usersDefined = userRepo.findByEmail(user.getEmail());
			if (usersDefined == null || !user.getPassword().equals(usersDefined.getPassword())) {
				return "invalid_user";
			} else {
				userId = usersDefined.getUserId();
				return "redirect:/coursesEnrolled";

			}
		}
	}

	@GetMapping("/coursesEnrolled")
	public String viewHomePageForStudent(Model model) {

		Set<Course> listCourses = userRepo.getById(userId).getCourses();
		model.addAttribute("listCourses", listCourses);
		return "courses_enrolled";
	}

	@GetMapping("/deleteStudentCourse/{id}")
	public String deleteStudentCourse(@PathVariable(value = "id") long id) {
		Optional<Course> optional = courseRepo.findById(id);
		Course course = null;
		if (optional.isPresent()) {
			course = optional.get();
		} else {
			throw new RuntimeException(" Course not found for id :: " + id);
		}

		User user = userRepo.getById(userId);
		user.getCourses().remove(course);
		userRepo.save(user);

		return "redirect:/coursesEnrolled";
	}

	@PostMapping("/enrollNewCourseStudent")
	public String enrollNewCourseStudent(@RequestParam("courseId") long courseId) {
		Course course = courseService.getCourseById(courseId);
		User user = userRepo.getById(userId);
		user.getCourses().add(course);
		userRepo.save(user);
		return "redirect:/coursesEnrolled";
	}
}