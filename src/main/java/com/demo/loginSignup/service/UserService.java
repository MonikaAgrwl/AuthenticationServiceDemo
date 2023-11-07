package com.demo.loginSignup.service;

import com.demo.loginSignup.dto.LoginRequest;
import com.demo.loginSignup.dto.SignupRequest;
import com.demo.loginSignup.entity.User;
import com.demo.loginSignup.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<?> registerUser(SignupRequest signupRequest){

        // checking for username exists in a database
        if(userRepository.existsByUserName(signupRequest.getUserName())){
            logger.debug("Username already exists..");
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        logger.debug("User doesn't exist in database. Now, checking for email exists in a database..");
        // checking for email exists in a database
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            logger.debug("Email already exists");
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        // creating user object
        User user = new User();
        user.setName(signupRequest.getName());
        user.setUserName(signupRequest.getUserName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword((signupRequest.getPassword())); //TODO : Enable password hashing through Bcrypt Encryption

        //TODO: ENABLE user roles functionality

        userRepository.save(user);

        logger.info("User is registered successfully!..");
        return new ResponseEntity<>("User is registered successfully!", HttpStatus.CREATED);
    }

    public ResponseEntity<String> loginUser(LoginRequest loginRequest){
        logger.debug("Received new Login request.. ");

        User user = userRepository.findByUserName(loginRequest.getUserName());

        if(user==null){
            logger.error("Incorrect Credentials..");
            return new ResponseEntity<>("Incorrect Credentials", HttpStatus.UNAUTHORIZED);
        } else if (!(user.getPassword().equals(loginRequest.getPassword()))){
            logger.error("Password incorrect..");
            return new ResponseEntity<>("Password incorrect. Please try again.", HttpStatus.UNAUTHORIZED);
        }

        logger.info("User login successfully!..");
        return new ResponseEntity<>("User login successful", HttpStatus.OK);
    }

    public ResponseEntity<?> getUser(String userId){
        Long id ;
        try {
            id = Long.parseLong(userId);
        }catch (NumberFormatException ex){
            logger.error("Invalid user id....");
            return new ResponseEntity<>("Invalid user id...", HttpStatus.BAD_REQUEST);
        }

        logger.debug("Fetching user details.. ");
        User user = userRepository.findById(id);

        if(user==null){
            logger.error("User ID not found");
            return new ResponseEntity<>("User ID not found!", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(user);
    }
}
