package com.booster.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.booster.entity.User;
import com.booster.repository.UserRepository;

@Repository(value = "UserRepository")
public class UserDAO extends GenericDAO<User> implements UserRepository {
	
	@Autowired
	public UserDAO(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}
	
	@Override
	public User findByLoginAndPassword(String login, String password) {
		Query query = super.createQuery("From User where login = :login and password = :password");
		query.setParameter("login", login);
		query.setParameter("password", password);
		return (User) query.uniqueResult(); 
	}

}
