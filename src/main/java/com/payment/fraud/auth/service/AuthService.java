package com.payment.fraud.auth.service;

import com.payment.fraud.auth.dto.AuthRequest;
import com.payment.fraud.auth.dto.RegisterRequest;
import com.payment.fraud.auth.entity.Roles;
import com.payment.fraud.auth.entity.User;
import com.payment.fraud.auth.repository.RoleRepository;
import com.payment.fraud.auth.repository.UserRepository;
import com.payment.fraud.auth.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public String register(RegisterRequest req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));

        Roles role = roleRepo.findByName("ROLE_USER").orElseThrow();
        user.getRoles().add(role);

        userRepo.save(user);
        return "User registered";
    }

    public String login(AuthRequest req) {

        System.out.println("password" +req.getPassword());
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        System.out.println("auth " +auth.isAuthenticated());
        return tokenProvider.generateToken(auth.getName());
    }
}

