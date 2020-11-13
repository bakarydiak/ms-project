package com.episen.ms;

import java.util.Arrays;
import java.util.Map;

import com.episen.ms.model.AuthenticateModel;
import com.episen.ms.model.User;
import com.episen.ms.repository.UserRepository;
import com.episen.ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.episen.ms.security.JwTokenGenerator;

@SpringBootApplication
public class MsCours1Application implements CommandLineRunner{
	
	@Autowired
	private JwTokenGenerator generator;
	@Autowired
	private UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(MsCours1Application.class, args);

		System.out.println("hello ESIPEN");
	}

	@Override
	public void run(String... args) throws Exception {
		this.userRepository.addUser(
				new User("admin", "admin@admin.fr", 29,
						new AuthenticateModel("admin", "admin"), "admin"));

	}

}
