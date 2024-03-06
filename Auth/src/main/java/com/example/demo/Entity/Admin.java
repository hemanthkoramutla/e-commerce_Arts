package com.example.demo.Entity;
import jakarta.persistence.*;
@Entity
@Table(name="Admins")
public class Admin {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	   private String adminname;
	   @Override
	public String toString() {
		return "Admin [aid=" + id + ", adminname=" + adminname + ", adminpassword=" + adminpassword + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAdminname() {
		return adminname;
	}
	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}
	public String getAdminpassword() {
		return adminpassword;
	}
	public void setAdminpassword(String adminpassword) {
		this.adminpassword = adminpassword;
	}
	private String adminpassword;

}
