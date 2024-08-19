package org.docmanager.service.user;

import lombok.RequiredArgsConstructor;
import org.docmanager.dto.auth.JwtResponse;
import org.docmanager.dto.auth.UserLoginRequest;
import org.docmanager.dto.auth.UserSignUpRequest;
import org.docmanager.dto.auth.UserSignUpResponse;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.model.Role;
import org.docmanager.model.User;
import org.docmanager.model.UserRole;
import org.docmanager.repository.RoleRepository;
import org.docmanager.repository.UserRepository;
import org.docmanager.service.security.JwtUtils;
import org.docmanager.service.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Override
    public UserSignUpResponse registerUser(UserSignUpRequest userSignUpRequest) throws AlreadyExistException {

        if (Boolean.TRUE.equals(userRepository.existsByEmail(userSignUpRequest.getEmail())))
            throw new AlreadyExistException(String.format("User with email %s already exists", userSignUpRequest.getEmail()));

        Role role = this.roleRepository.save(Role.builder().name(UserRole.ROLE_CUSTOMER).build());

        User user = userRepository.save(User.builder().roles(Set.of(role))
                .email(userSignUpRequest.getEmail())
                .registrationDate(LocalDateTime.now())
                .password(encoder.encode(userSignUpRequest.getPassword()))
                .build());

        return UserSignUpResponse.builder()
                .email(user.getEmail())
                .roles(user.getRoles().stream()
                        .map(userRole -> userRole.getName().name()).collect(Collectors.toSet()))
                .success(true).build();
    }

    @Override
    public JwtResponse authenticate(UserLoginRequest userLoginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return JwtResponse.builder()
                .token(jwt).email(userDetails.getEmail())
                .roles(roles).build();
    }

}
