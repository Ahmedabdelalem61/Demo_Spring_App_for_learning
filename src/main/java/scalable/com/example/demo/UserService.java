package scalable.com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo) { this.repo = repo; }

    public List<User> findAll() { return repo.findAll(); }
    public Optional<User> findById(Long id) { return repo.findById(id); }

    public User create(User u) {
        repo.findByEmail(u.getEmail()).ifPresent(existing -> {
            throw new IllegalArgumentException("Email already used");
        });
        return repo.save(u);
    }

    public User update(Long id, User u) {
        User existing = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
        existing.setName(u.getName());
        existing.setEmail(u.getEmail());
        return repo.save(existing);
    }

    public void delete(Long id) { repo.deleteById(id); }
}
