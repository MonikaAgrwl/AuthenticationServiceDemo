package com.demo.loginSignup.service;

import com.demo.loginSignup.dto.LoginRequest;
import com.demo.loginSignup.dto.SignupRequest;
import com.demo.loginSignup.entity.User;
import com.demo.loginSignup.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_success() {
        SignupRequest signupRequest = new SignupRequest("test","test","test@gmail.com","password");
        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
       // when(userRepository.save(any(User.class))).thenReturn(user);
        ResponseEntity<?> responseEntity = userService.registerUser(signupRequest);
        Assertions.assertEquals(responseEntity.getStatusCode().value(),201);
    }

    @Test
    void registerUser_shouldThrowErrorWhenExistingEmail() {
        SignupRequest signupRequest = new SignupRequest("test","test","test@gmail.com","password");
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        ResponseEntity<?> responseEntity = userService.registerUser(signupRequest);
        Assertions.assertEquals(400, responseEntity.getStatusCode().value());
    }

    @Test
    void registerUser_shouldThrowErrorWhenExistingUserName() {
        SignupRequest signupRequest = new SignupRequest("test","test","test@gmail.com","password");
        when(userRepository.existsByUserName(anyString())).thenReturn(true);
        ResponseEntity<?> responseEntity = userService.registerUser(signupRequest);
        Assertions.assertEquals(400, responseEntity.getStatusCode().value());
    }

    @Test
    void authenticateUser_success() {
        LoginRequest loginRequest = new LoginRequest("test","password");
        User user = new User("test","test", "test","password");
        when(userRepository.findByUserName(anyString())).thenReturn(user);
        ResponseEntity<?> responseEntity = userService.loginUser(loginRequest);
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    void authenticateUser_passwordIncorrect() {
        LoginRequest loginRequest = new LoginRequest("test","password");
        User user = new User("test","test", "test","password1");
        when(userRepository.findByUserName(anyString())).thenReturn(user);
        ResponseEntity<?> responseEntity = userService.loginUser(loginRequest);
        Assertions.assertEquals(401, responseEntity.getStatusCode().value());
    }

    @Test
    void getUser_success() {
        User user = new User("test","test", "test","password1");
        when(userRepository.findById(anyLong())).thenReturn(user);
        ResponseEntity<?> responseEntity = userService.getUser("1");
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    void getUser_InvalidId() {
        ResponseEntity<?> responseEntity = userService.getUser("b");
        Assertions.assertEquals(400, responseEntity.getStatusCode().value());
    }

    @Test
    void getUser_UserIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        ResponseEntity<?> responseEntity = userService.getUser("1");
        Assertions.assertEquals(404, responseEntity.getStatusCode().value());
    }
}