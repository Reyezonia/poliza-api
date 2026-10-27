package com.example.polizas_api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String HEADER = "x-api-key";
    private static final String EXCLUDED = "/h2-console";

    @Value("${app.api-key}")
    private String apiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
                
        String path = request.getRequestURI();
        if (path.startsWith(EXCLUDED)) {
            filterChain.doFilter(request, response);
            return;
        }

        String provided = request.getHeader(HEADER);
        if (provided == null || !provided.equals(apiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"status\":401,\"error\":\"Unauthorized\",\"mensaje\":\"Header x-api-key inválido o ausente\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
