package com.booster.repository;

import com.booster.entity.User;

public interface UserRepository extends GenericRepository {
	
	public User findByLoginAndPassword(String login, String password);
}
