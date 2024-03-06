package com.example.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entity.Admin;
public interface AdminRepository extends JpaRepository<Admin, Long> {
	    Admin findByAdminname(String adminname);
}

