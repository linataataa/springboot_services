package hyfz.hyfz.Repo;

import hyfz.hyfz.user.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User , String> {
    Optional<User> findByEmail(String email);
    User findUserByEmailOrUsername(String email, String username);
    boolean existsUserByEmail(@Email(message = "email incorrect") String email);

    boolean existsByUsername(String username);
}
