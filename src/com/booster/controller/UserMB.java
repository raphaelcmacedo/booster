package com.booster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.booster.entity.User;
import com.booster.service.UserService;

@Controller("userMB")
//@Scope(value="session")
@Scope("view")
public class UserMB extends GenericCrudMB<User, UserService> {

	private UserService service;

	@Autowired
	public UserMB(UserService service) {
		super(service);
		this.service = service;
	}

}
