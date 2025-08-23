package scalable.com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import scalable.com.example.demo.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
