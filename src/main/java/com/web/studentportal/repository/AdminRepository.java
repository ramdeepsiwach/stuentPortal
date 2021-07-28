package com.web.studentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.studentportal.model.Admin;
import com.web.studentportal.model.User;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	@Query("SELECT u FROM Admin u WHERE u.email = ?1")
	public Admin findAdminByEmail(String email);
}
