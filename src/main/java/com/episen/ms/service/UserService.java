package com.episen.ms.service;

import java.util.List;
import java.util.Optional;

import com.episen.ms.model.AuthenticateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.episen.ms.model.User;
import com.episen.ms.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public User addUser(User user){
		if("".equals(user.getUsername()) || "".equals(user.getEmail())){
			throw new RuntimeException("user exception");
		}
		repository.addUser(user);
		return user;
	}
	
	public User getUser(String username){
		
		User user = repository.getUserByUsername(username);
		
		if(user == null){
			throw new RuntimeException("User not found");
		}
		
		return user;
	}
	
	public List<User> getAll(){
		return repository.getAll();
	}
	
	public User updateUser(String username, User user){
		
		if(repository.getUserByUsername(username) == null){
			throw new RuntimeException("User not found");
		}
		
		repository.updateUser(user);
		return user;
	}
	
	public void deleteUser(String username){
		
		if(repository.getUserByUsername(username) == null){
			throw new RuntimeException("User not found");
		}
		
		repository.deleteUserByUsername(username);
	}

	public UserRepository getRepository() {
		return repository;
	}

	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}

	public Optional<User> checkLogin(AuthenticateModel authenticateModel){
		return repository.checkUser(authenticateModel);
	}
	
	
}
