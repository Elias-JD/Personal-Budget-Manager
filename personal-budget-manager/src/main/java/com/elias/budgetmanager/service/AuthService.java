package com.elias.budgetmanager.service;

import com.elias.budgetmanager.dto.AuthResponse;
import com.elias.budgetmanager.dto.LoginRequest;
import com.elias.budgetmanager.dto.RegisterRequest;
import com.elias.budgetmanager.exception.ResourceAlreadyExistsException;
import com.elias.budgetmanager.exception.ResourceNotFoundException;
import com.elias.budgetmanager.jwt.JwtUtils;
import com.elias.budgetmanager.model.CustomUserDetails;
import com.elias.budgetmanager.model.Role;
import com.elias.budgetmanager.model.User;
import com.elias.budgetmanager.model.enums.RoleName;
import com.elias.budgetmanager.repository.RoleRepository;
import com.elias.budgetmanager.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())){
            throw new ResourceAlreadyExistsException("Email already in use");
        }

        if (userRepository.existsByUsername(request.username())){
            throw new ResourceAlreadyExistsException("Username already taken");
        }

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));

        User user = new User();

        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setEnabled(true);
        user.getRoles().add(role);

        userRepository.save(user);

        String token = jwtUtils.generateJwtToken(new CustomUserDetails(user));


        return new AuthResponse(token,"Bearer");
    }


    public AuthResponse login(LoginRequest request) {

        Authentication auth =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.email(),
                    request.password()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        if (userDetails == null) {
            throw new AccessDeniedException("Internal error: User data could not be obtained");
        }

        String token = jwtUtils.generateJwtToken(userDetails);

        return new AuthResponse(token,"Bearer");
    }

}
