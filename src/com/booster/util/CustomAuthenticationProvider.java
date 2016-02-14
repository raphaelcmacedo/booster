package com.booster.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booster.repository.UserRepository;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

	
	UserRepository repository;

	public CustomAuthenticationProvider() {
		super();
	}
	
	@Autowired
	@Qualifier("UserRepository")
	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		String login = authentication.getName();
		String password = authentication.getCredentials().toString();

		com.booster.entity.User user = this.repository.findByLoginAndPassword(login, password);
		
		if (user != null) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

			grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));

			UserDetails userDetails = new User(login, password, grantedAuthorities);
			return new UsernamePasswordAuthenticationToken(userDetails, password, grantedAuthorities);
		} else {
			return null;
		}
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
