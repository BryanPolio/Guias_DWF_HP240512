package sv.edu.udb.controller; //BRYAN STEVEN HERNANDEZ POLIO HP240512

import sv.edu.udb.dto.UserResponse;
import sv.edu.udb.model.User;
import sv.edu.udb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user = null;

        if (principal instanceof User) {
            user = (User) principal;
        } else if (principal instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User securityUser =
                    (org.springframework.security.core.userdetails.User) principal;
            String email = securityUser.getUsername();
            user = userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        } else {
            throw new RuntimeException("Tipo de autenticación no soportado");
        }

        UserResponse userResponse = userService.toUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("Este es un endpoint solo para ADMIN");
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> userProfile() {
        return ResponseEntity.ok("Perfil de usuario");
    }
}