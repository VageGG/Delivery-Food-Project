package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.UserDto;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.UserService;
import com.fooddeliveryfinalproject.service.ValidUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
@AllArgsConstructor
public abstract class LoginImplController<S extends ValidUser, T extends UserDto> {

    private AuthenticationManager authenticationManager;

    private JWTUtilService jwtUtilService;

    private UserService userService;

    public S service;
    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody T dto) {
        try {
            service.addUser(dto);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody T dto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(dto.getEmail());
        final String jwt = jwtUtilService.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }

//    @GetMapping("/login/github")
//    public RedirectView loginWithGithub() {
//        return new RedirectView("http://localhost:8080/oauth2/authorization/github");
//    }


//    @GetMapping("/loginSuccess")
//    public ResponseEntity<?> loginSuccess() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//        String username = oauth2User.getAttribute("login");
//
//        // Генерируем JWT токен для пользователя
//        String jwtToken = jwtUtilService.generateToken(userService.loadUserByUsername(username));
//
//        return ResponseEntity.ok(jwtToken);
//    }
//
//    @GetMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            // Деактивируйте текущую сессию
//            SecurityContextHolder.getContext().setAuthentication(null);
//        }
//
//        // Удалите данные сессии и перенаправьте на страницу входа с параметром logout
//        response.sendRedirect("/login?logout");
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
