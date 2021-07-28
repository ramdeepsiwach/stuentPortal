package com.web.studentportal.service;

import java.util.List;

import com.web.studentportal.model.Course;

public interface CourseService {
	List<Course> getAllCourses();
	
	void saveCourse(Course course);

	Course getCourseById(long id);

	void deleteCourseById(long id);
}
