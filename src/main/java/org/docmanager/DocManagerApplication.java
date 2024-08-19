package org.docmanager;

import lombok.RequiredArgsConstructor;
import org.docmanager.model.Role;
import org.docmanager.model.UserRole;
import org.docmanager.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DocManagerApplication implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(DocManagerApplication.class, args);
    }

    @Override
    public void run(String... args)  {
        this.roleRepository.save(Role.builder().name(UserRole.ROLE_CUSTOMER).build());
        this.roleRepository.save(Role.builder().name(UserRole.ROLE_ADMIN).build());
    }
}
