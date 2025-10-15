package sv.edu.udb.controller; //BRYAN STEVEN HERNANDEZ POLIO HP240512

import sv.edu.udb.dto.*;
import sv.edu.udb.model.User;
import sv.edu.udb.model.Role;
import sv.edu.udb.service.UserService;
import sv.edu.udb.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request,
            @RequestParam(defaultValue = "USER") Role role) {

        if (userService.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El usuario ya existe");
        }

        User user = userService.createUser(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                role
        );

        UserResponse userResponse = userService.toUserResponse(user);
        String jwt = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(jwt, userResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        UserResponse userResponse = userService.toUserResponse(user);
        String jwt = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(jwt, userResponse));
    }
}