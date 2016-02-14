package com.booster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.booster.repository.UserRepository;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("UserService")
public class UserService extends GenericService {
		
	UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		super(userRepository);
		this.userRepository = userRepository;
	}
	
	
}
