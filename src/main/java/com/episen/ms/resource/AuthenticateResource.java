package com.episen.ms.resource;

import java.util.Arrays;
import java.util.Optional;

import com.episen.ms.model.User;
import com.episen.ms.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.episen.ms.model.AuthenticateModel;
import com.episen.ms.model.SingleValue;
import com.episen.ms.model.UserContext;
import com.episen.ms.security.JwTokenGenerator;
import com.episen.ms.security.JwTokenValidator;

@RestController
@RequestMapping(value = "authenticate", produces = {"application/json"})
public class AuthenticateResource {

	@Autowired
	private JwTokenGenerator jwtGenerator;

	@Autowired
	private JwTokenValidator jwtValidator;

	@Autowired
	private UserService userService;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public ResponseEntity<Object> authenticate(@RequestBody AuthenticateModel authModel){
		Optional<User> user = userService.checkLogin(authModel);
		// TODO check database

		if(user != null){
			User authenticatedUser = user.get();
			JSONObject json = new JSONObject();
			json.put("User", user.get());
			json.put("Token", jwtGenerator.generateToken(authenticatedUser.getAuthenticateModel().getLogin(), authenticatedUser.getRole()));
			return ResponseEntity.status(200).body(json);
		}
		JSONObject json = new JSONObject();
		json.put("message", "utilisateur introuvable");
		return ResponseEntity.status(400).body(json);
	}
	
	@GetMapping("validate")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public ResponseEntity<Object> parseToken(@RequestHeader(value = "token", required = false) String token){
		if(token != null){
			JSONObject json = new JSONObject();
			json.put("role", jwtValidator.transforme(token));
			return ResponseEntity.status(200).body(json);
		}
		JSONObject json = new JSONObject();
		json.put("role", "non authentifier");
		return ResponseEntity.status(400).body(json);
	}
}
