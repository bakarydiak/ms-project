package com.episen.ms.repository;

import java.util.*;

import com.episen.ms.model.AuthenticateModel;
import org.springframework.stereotype.Component;

import com.episen.ms.model.User;

@Component
public class UserRepository {

	private Map<String, User> userInMemory = new HashMap<>();


	public void addUser(User user){
		System.out.println("AddUser : username " + user.getUsername());
		
		userInMemory.put(user.getUsername(), user);
	}
	
	public User getUserByUsername(String username){
		System.out.println("getUserByUsername , username : " + username);
		return userInMemory.get(username);
	}
	
	public List<User> getAll(){
		return new ArrayList<User>(userInMemory.values());
	}
	
	public void updateUser(User user){
		System.out.println("updateUser : userbame " + user.getUsername());
		userInMemory.put(user.getUsername(), user);
	}
	
	public void deleteUserByUsername(String username){
		System.out.println("deleteUserByUsername , username : " + username);
		userInMemory.remove(username);
	}

	public Optional<User> checkUser (AuthenticateModel auth){
		for (User user : userInMemory.values()) {
			if (user.getAuthenticateModel().getLogin().equals(auth.getLogin()) && user.getAuthenticateModel().getPassword().equals(auth.getPassword())){
				return Optional.of(user);
			}
		}

		return null;
	}
}
