package com.example.carros.api.security;

import com.example.carros.dominio.User;
import com.example.carros.dominio.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class UserDatailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userReporitory;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userReporitory.findByLogin(username);
		if(user == null) throw new UsernameNotFoundException("User Not Found");
		return user;
	}
}
