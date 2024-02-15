package com.springsecurity.service;

import com.springsecurity.dto.CreateUserRequest;
import com.springsecurity.model.User;
import com.springsecurity.repository.UserRepository;
import com.springsecurity.security.JwtProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = getByUsername(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Creates a new user based on the provided request.
     *
     * @param request the CreateUserRequest object containing the user details
     * @return the created User object
     */
    public User createUser(CreateUserRequest request) {
        User user = User.builder()
                .name(request.name())
                .username(request.username())
                .password(bCryptPasswordEncoder.encode(request.password()))
                .authorities(request.authorities())
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        return userRepository.save(user);
    }

    public String login(String username) {
        return jwtProvider.generateToken(username);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
