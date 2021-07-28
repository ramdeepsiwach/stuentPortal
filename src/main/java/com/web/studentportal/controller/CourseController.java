package com.web.studentportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.studentportal.model.Course;
import com.web.studentportal.repository.UserRepository;
import com.web.studentportal.service.CourseService;

@Controller
public class CourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private UserRepository userRepo;

	@GetMapping("/courses")
	public String viewHomePage(Model model) {
		List<Course> listCourses = courseService.getAllCourses();
		model.addAttribute("listCourses", listCourses);
		return "courses_available";

	}

	@GetMapping("/showNewCoursesForm")
	public String showNewCoursesForm(Model model) {
		Course course = new Course();
		model.addAttribute("course", course);
		return "new_course";
	}

	@GetMapping("/showNewCoursesFormStudent")
	public String showNewCoursesFormStudent(Model model) {
		List<Course> listCourses = courseService.getAllCourses();
		model.addAttribute("listCourses", listCourses);
		Course course = new Course();
		model.addAttribute("course", course);
		return "enroll_new_course";
	}

	@PostMapping("/saveCourse")
	public String saveCourse(@ModelAttribute("course") Course course) {
		courseService.saveCourse(course);
		return "redirect:/courses";
	}

	

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

		Course course = courseService.getCourseById(id);

		model.addAttribute("course", course);
		return "update_course";
	}

	@GetMapping("/deleteCourse/{id}")
	public String deleteCourse(@PathVariable(value = "id") long id) {

		this.courseService.deleteCourseById(id);
		return "redirect:/courses";
	}
}
