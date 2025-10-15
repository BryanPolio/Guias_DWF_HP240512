package sv.edu.udb.config; //BRYAN STEVEN HERNANDEZ POLIO HP240512

import sv.edu.udb.model.User;
import sv.edu.udb.service.UserService;
import sv.edu.udb.service.JwtService;
import sv.edu.udb.dto.AuthResponse;
import sv.edu.udb.dto.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private sv.edu.udb.repository.UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        try {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User oauthUser = oauthToken.getPrincipal();

            String provider = oauthToken.getAuthorizedClientRegistrationId();
            Map<String, Object> attributes = oauthUser.getAttributes();

            String email = getEmail(attributes, provider);
            String name = getName(attributes, provider);
            String oauthId = getOAuthId(attributes, provider);

            // buscar o crear usuario
            User user = userService.findByOAuth(provider, oauthId)
                    .orElseGet(() -> {
                        if (userService.existsByEmail(email)) {
                            User existingUser = userService.findByEmail(email)
                                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                            existingUser.setOauthProvider(provider);
                            existingUser.setOauthId(oauthId);
                            return userRepository.save(existingUser);
                        } else {
                            return userService.createOAuthUser(email, name, provider, oauthId);
                        }
                    });

            // genera token JWT
            String jwtToken = jwtService.generateToken(user);
            UserResponse userResponse = userService.toUserResponse(user);
            AuthResponse authResponse = new AuthResponse(jwtToken, userResponse);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authResponse));
            response.getWriter().flush();

        } catch (Exception e) {
            // devuelve error en el JSON
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "OAuth2 Authentication Failed");
            errorResponse.put("message", e.getMessage());

            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }

    private String getEmail(Map<String, Object> attributes, String provider) {
        if ("github".equals(provider)) {
            String email = (String) attributes.get("email");
            if (email == null) {
                String login = (String) attributes.get("login");
                email = login + "@github.com";
            }
            return email;
        }
        return (String) attributes.get("email");
    }

    private String getName(Map<String, Object> attributes, String provider) {
        if ("github".equals(provider)) {
            String name = (String) attributes.get("name");
            if (name == null) {
                name = (String) attributes.get("login");
            }
            return name;
        }
        return (String) attributes.get("name");
    }

    private String getOAuthId(Map<String, Object> attributes, String provider) {
        if ("github".equals(provider)) {
            return attributes.get("id").toString();
        }
        return attributes.get("sub").toString();
    }
}