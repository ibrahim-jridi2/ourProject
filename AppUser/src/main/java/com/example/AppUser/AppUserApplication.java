package com.example.AppUser;

import com.example.AppUser.models.ApplicationUser;
import com.example.AppUser.models.Role;
import com.example.AppUser.repository.RoleRepository;
import com.example.AppUser.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class AppUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppUserApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent())
				return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			Role userRole = roleRepository.save(new Role("USER"));
			Role superAdminRole = roleRepository.save(new Role("SUPER-ADMIN"));
			Role guestRole = roleRepository.save(new Role("GUEST"));
			Role organizer =roleRepository.save(new Role("ORGANIZER"));

			Set<Role> superAdminRoles = new HashSet<>();
			superAdminRoles.add(superAdminRole);
			ApplicationUser superAdmin = new ApplicationUser(1, "super", passwordEncode.encode("super"), superAdminRoles);
			userRepository.save(superAdmin);

			Set<Role> adminRoles = new HashSet<>();
			adminRoles.add(adminRole);
			ApplicationUser admin = new ApplicationUser(2, "admin", passwordEncode.encode("admin"), adminRoles);
			userRepository.save(admin);


			Set<Role> userRoles = new HashSet<>();
			userRoles.add(userRole);
			ApplicationUser user = new ApplicationUser(3, "user", passwordEncode.encode("user"), userRoles);
			userRepository.save(user);

			Set<Role> guestRoles = new HashSet<>();
			guestRoles.add(guestRole);
			ApplicationUser guest = new ApplicationUser(4, "guest", passwordEncode.encode("guest"), guestRoles);
			userRepository.save(guest);

			Set<Role> organizerRole = new HashSet<>();
			organizerRole.add(organizer);
			ApplicationUser organizerUser = new ApplicationUser(5, "organzier", passwordEncode.encode("organzier"), organizerRole);
			userRepository.save(organizerUser);

			System.out.println("Starting...");
		};
	}
}

