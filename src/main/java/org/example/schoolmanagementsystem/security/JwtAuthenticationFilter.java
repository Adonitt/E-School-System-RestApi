package org.example.schoolmanagementsystem.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.services.interfaces.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// onceperrequest, ekzekutohet sa here qe te bon request useri - e ne na vyn qe me validu tokenin
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            // marrim tokenin prej header Authentication
            // Bearer {tani tokeni}
            String token = extractTokenFromHeader(request);

            if (token != null) {
                var userDetails = authService.validateToken(token);

                // e inicializon security context holder
                var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities() // rolet dhe permissions te userit
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // kur kemi nevoje me dite kush e ka ba create, update delete ni entitet,
                if (userDetails instanceof AppUserDetails) {
                    request.setAttribute("userId", ((AppUserDetails) userDetails).getId());
                }
            }
        } catch (Exception e) {
//            log.error("Error logging in: {}", e.getMessage());
            System.out.println("error" + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7); // fshije Bearer, shko te pozita 7 deri ne fund
        }
        return null;
    }
}
