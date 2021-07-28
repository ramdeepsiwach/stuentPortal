package com.web.studentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.studentportal.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
