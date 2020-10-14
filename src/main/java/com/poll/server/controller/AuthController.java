package com.poll.server.controller;

import com.poll.server.config.DatabaseConfig;
import com.poll.server.exception.AppException;
import com.poll.server.model.Role;
import com.poll.server.model.RoleName;
import com.poll.server.model.User;
import com.poll.server.payload.ApiResponse;
import com.poll.server.payload.JwtAuthenticationResponse;
import com.poll.server.payload.LoginRequest;
import com.poll.server.payload.SignUpRequest;
import com.poll.server.repository.RoleRepository;
import com.poll.server.repository.UserRepository;
import com.poll.server.repository.UserRoleRepository;
import com.poll.server.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
/**
 *  
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @Autowired
    DatabaseConfig dataSource;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws SQLException {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));



        User result = userRepository.save(user);
        
      Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
      .orElseThrow(() -> new AppException("User Role not set."));
      user.setRoles(Collections.singleton(userRole));
      userRole.setId(user.getId());
     
      System.out.println("Role ID "+ user.getRoles() + " Role Name " +user.getRoles().stream());
      
 
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();
        
        System.out.println("DataSource "+ dataSource.dataSource());
        insertRecord(user.getId());

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
    
    public void insertRecord(Long user_id) throws SQLException {
    	
        final String INSERT_USERS_SQL = "INSERT INTO user_roles" +
    	        "  (user_id, role_id ) VALUES " +
    	        " (?, ?);";
        System.out.println(INSERT_USERS_SQL);
        // Step 1: Establishing a Connection
        try (Connection connection =  dataSource.dataSource().getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setFloat(1, user_id);
            preparedStatement.setFloat(2, 2);
       

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            // print SQL exception information
            
        }

    }
}
