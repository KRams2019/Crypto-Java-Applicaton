package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "SELECT * FROM user t WHERE "
			+ "LOWER(t.user_name) LIKE LOWER(CONCAT(:searchTerm, '%'))", nativeQuery = true)
	List<User> findBySearchTermNative(@Param("searchTerm") String searchTerm);
}
