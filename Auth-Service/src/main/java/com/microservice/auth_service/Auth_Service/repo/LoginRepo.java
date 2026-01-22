package com.microservice.auth_service.Auth_Service.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.auth_service.Auth_Service.entity.LoginEntity;




public interface LoginRepo extends JpaRepository<LoginEntity, Long>{
	boolean existsByEmail(String email);
	Optional<LoginEntity> findByEmail(String email);
	List<LoginEntity> findByOrderByIdDesc();
}
