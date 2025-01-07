package hyfz.hyfz.Services;

import hyfz.hyfz.CustomUser.CustomUserDetails;
import hyfz.hyfz.PlayLoad.Requests.LoginRequest;
import hyfz.hyfz.PlayLoad.Requests.RegisterRequest;
import hyfz.hyfz.PlayLoad.Response.UserInfoResponse;
import hyfz.hyfz.Repo.UserRepo;
import hyfz.hyfz.config.JWTService;
import hyfz.hyfz.exception.DuplicateResourceException;
import hyfz.hyfz.exception.ResourceNotFoundException;
import hyfz.hyfz.user.Role;
import hyfz.hyfz.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepository;

    public UserService(AuthenticationManager authenticationManager, JWTService jwtService, PasswordEncoder passwordEncoder, UserRepo userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public Page<User> getSortedAndPagedData(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User registerNewUser(RegisterRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new DuplicateResourceException("Error: Username is already taken!");
        }
        if (userRepository.existsUserByEmail(signupRequest.getEmail())) {
            throw new DuplicateResourceException("Error: email is already taken!");
        }

        User user = new User(signupRequest.getFirst_name(), signupRequest.getLast_name(), signupRequest.getUsername(), signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()));

        user.setRoles(Role.USER);
        userRepository.save(user);
        return user;
    }

    public UserInfoResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepository.findUserByEmailOrUsername(principal.getUsername(), principal.getUsername());
        String token = jwtService.issueToken(principal.getUsername(), List.of(principal.getAuthorities().toString()));


        return new UserInfoResponse(token, user);
    }


    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("There's no user with id:" + userId)
        );
        userRepository.delete(user);
    }
}
