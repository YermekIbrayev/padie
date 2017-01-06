package com.iskhak.padie.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iskhak.padie.model.security.User;

/**
 * Created by stephan on 20.03.16.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("FROM User AS u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
}
