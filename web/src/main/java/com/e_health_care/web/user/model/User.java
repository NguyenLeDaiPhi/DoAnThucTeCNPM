package com.e_health_care.web.user.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
   @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String address;
	private int phone;
}
