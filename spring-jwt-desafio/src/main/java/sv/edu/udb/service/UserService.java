package sv.edu.udb.service; //BRYAN STEVEN HERNANDEZ POLIO HP240512

import sv.edu.udb.model.User;
import sv.edu.udb.model.Role;
import sv.edu.udb.repository.UserRepository;
import sv.edu.udb.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(String email, String password, String name, Role role) {
        User user = new User(email, passwordEncoder.encode(password), name, role);
        return userRepository.save(user);
    }

    public User createOAuthUser(String email, String name, String oauthProvider, String oauthId) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setRole(Role.USER);
        user.setOauthProvider(oauthProvider);
        user.setOauthId(oauthId);
        // Para usuarios OAuth, podemos establecer una contraseña por defecto o null como queramos
        user.setPassword(passwordEncoder.encode("oauth_user_" + System.currentTimeMillis()));
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByOAuth(String provider, String oauthId) {
        return userRepository.findByOauthProviderAndOauthId(provider, oauthId);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole()
        );
    }
}