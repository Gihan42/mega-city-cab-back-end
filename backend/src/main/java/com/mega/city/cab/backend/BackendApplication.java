package com.mega.city.cab.backend;

import com.mega.city.cab.backend.entity.UserRoles;
import com.mega.city.cab.backend.repo.UserRolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
	@Autowired
	UserRolesRepo userRolesRepo;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("hello spring");
	}

	@Override
	public void run(String... args) throws Exception {
		List<UserRoles> all = userRolesRepo.findAll();
		if (all.isEmpty()){
			UserRoles userRolesOne = new UserRoles();
			userRolesOne.setRole("Admin");
			userRolesOne.setStatus("1");
			userRolesRepo.save(userRolesOne);
			UserRoles userRolesTwo = new UserRoles();
			userRolesTwo.setRole("User");
			userRolesTwo.setStatus("1");
			userRolesRepo.save(userRolesTwo);
		}
	}

}
