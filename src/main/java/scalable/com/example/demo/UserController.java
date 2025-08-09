package scalable.com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService svc;
    public UserController(UserService svc) { this.svc = svc; }

    @GetMapping
    public List<User> list() { return svc.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return svc.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = svc.create(user);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        try {
            return ResponseEntity.ok(svc.update(id, user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
