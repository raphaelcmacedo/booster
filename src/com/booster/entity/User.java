package com.booster.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	@Column(name="userId")
	private long id;
	
	private String email;
	private String password;
	
	
}
