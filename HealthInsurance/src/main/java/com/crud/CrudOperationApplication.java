package com.crud;


import com.crud.entity.Admin;
import com.crud.entity.User;
import com.crud.enums.Role;
import com.crud.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@Configuration
public class CrudOperationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudOperationApplication.class, args);

	}
		@Autowired
		private PasswordEncoder passwordEncoder;

		@Bean
		CommandLineRunner createSuperAdmin(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
			return args -> {
				if (!adminRepository.existsByRole(Role.SUPER_ADMIN)) {
					Admin superAdmin = new Admin();
					superAdmin.setEmail("superadmin@gmail.com");
					superAdmin.setPassword(passwordEncoder.encode("superadmin@123"));
					superAdmin.setRole(Role.SUPER_ADMIN);
					superAdmin.setStatus(com.crud.enums.AdminStatus.APPROVED);

					adminRepository.save(superAdmin);
					System.out.println("SUPER_ADMIN created successfully!");
				}
			};
		}
	}








