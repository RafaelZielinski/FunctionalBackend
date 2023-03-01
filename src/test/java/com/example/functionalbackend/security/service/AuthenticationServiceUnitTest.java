package com.example.functionalbackend.security.service;

import com.example.functionalbackend.security.auth.AuthenticationResponse;
import com.example.functionalbackend.security.auth.RegisterRequest;
import com.example.functionalbackend.security.exception.UserAlreadyExistsException;
import com.example.functionalbackend.security.model.User;
import com.example.functionalbackend.security.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceUnitTest {

    @Mock
    UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @InjectMocks
    private AuthenticationService authenticationService;


    @Test
    void it_should_throw_User_already_exists_exception_when_you_put_present_email() {
        //Given
        when(userRepository.findByEmail(any()).isPresent()).thenThrow(new UserAlreadyExistsException("User already exists"));
        ;
        //When
        //Then
        assertThatThrownBy(() -> authenticationService.register(expectedRequest()))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("User already exists");
    }

    @Test
    void it_should_register_new_user() {
        //Given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWZla3ppZWxpbnNraUB3" +
                "cC5wbCIsImlhdCI6MTY3NzY5NjIzMCwiZXhwIjoxNjc3Njk3NjcwfQ.3RcoC" +
                "2uGjPlYyc5smPmFEjk_FBQB_oQPN5Nm7PdssTQ";
        RegisterRequest expectedRequest = expectedRequest();

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("$2a$10$fOfZuW/aiVY8BIBC8ltN8eLKHX/iNvJnJOJ3k7OA6dSwyXg9RGqWK");
        when(jwtService.generateToken(any())).thenReturn(token);

        //When
        AuthenticationResponse actualResponse = authenticationService.register(expectedRequest);
        //Then
        then(userRepository).should().save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();
        assertThat(actualResponse.getToken()).isEqualTo(token);
        assertThat(user.getPassword()).isEqualTo(passwordEncoder.encode(expectedRequest.getPassword()));
        assertThat(user.getUsername()).isEqualTo(expectedRequest.getEmail());


    }

    @Test
    void itShouldAuthenticate() {
        //Given
        //When
        //Then
    }

    private RegisterRequest expectedRequest() {
        return RegisterRequest.builder()
                .firstname("Rafał")
                .lastname("Zieliński")
                .email("rafekzielinski@wp.pl")
                .password("password")
                .build();
    }
}
