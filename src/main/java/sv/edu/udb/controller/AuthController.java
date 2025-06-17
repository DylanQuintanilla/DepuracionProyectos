package sv.edu.udb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.service.JwtService;
import sv.edu.udb.controller.request.AuthRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    // Endpoint para realizar login
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        try {
            // Imprimir los detalles del usuario que llega al endpoint
            System.out.println("AuthController- Username: " + authRequest.getUsername());
            System.out.println("AuthController-Password: " + authRequest.getPassword());

            // Autenticación de las credenciales (usuario y contraseña)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                            authRequest.getPassword())

            );
            // Si las credenciales son correctas, generamos el JWT
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Imprimir detalles del usuario autenticado
            System.out.println("Authenticated User: " + userDetails.getUsername());
            return jwtService.generateToken(userDetails);
        } catch (BadCredentialsException e) {
            return "Error de autenticación: " + e.getMessage();
        }
    }
}
