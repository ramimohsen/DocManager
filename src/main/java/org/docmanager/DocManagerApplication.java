package org.docmanager;

import lombok.RequiredArgsConstructor;
import org.docmanager.model.Role;
import org.docmanager.model.User;
import org.docmanager.model.UserRole;
import org.docmanager.repository.RoleRepository;
import org.docmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class DocManagerApplication implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String email;

    @Value("${admin.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(DocManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {

        List<Role> roles = this.roleRepository.saveAll(Set.of(
                Role.builder().name(UserRole.ROLE_CUSTOMER).build(),
                Role.builder().name(UserRole.ROLE_ADMIN).build()
        ));

        this.userRepository.save(User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(new HashSet<>(roles)).build());


    }
}
