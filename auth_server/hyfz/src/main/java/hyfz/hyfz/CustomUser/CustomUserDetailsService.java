package hyfz.hyfz.CustomUser;

import hyfz.hyfz.Repo.UserRepo;
import hyfz.hyfz.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepository;


    public CustomUserDetailsService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmailOrUsername(username,username);
        if (user == null){
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        return new CustomUserDetails(user);
    }
}
