package com.episen.ms.resource;

import java.util.List;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.episen.ms.model.User;
import com.episen.ms.service.UserService;

@RestController
@RequestMapping(value = "users", produces = {"application/json"})
public class UserResource {

	@Autowired
	private UserService service;
	
	@GetMapping
	public List<User> getAll(){
		return service.getAll();
	}
	
	@GetMapping("{username}")
	public User getOne(@PathVariable("username") String username){
		
		return service.getUser(username);
	}
	
	@PostMapping()
	public ResponseEntity<Object> addUser(@RequestBody User user, @RequestHeader(value = "token", required = false) String token){
		if (token == null){
			JSONObject json = new JSONObject();
			json.put("message", "entrez votre token");
;			return ResponseEntity.status(400).body(json);
		}

		return ResponseEntity.status(200).body(service.addUser(user));
	}
	
	@PutMapping("{username}")
	public void updateUser(@PathVariable("username") String username, @RequestBody User user){
		
		service.updateUser(username, user);
	}
	
	@DeleteMapping("{username}")
	public void deleteUser(@PathVariable("username") String username){
		
		service.deleteUser(username);
	}
	
}
