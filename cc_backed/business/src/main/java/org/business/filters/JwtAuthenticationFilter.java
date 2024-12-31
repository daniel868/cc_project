package org.business.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.business.utils.AppConstants;
import org.business.utils.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null && isWhiteListRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid JWT Token");
        }
        String token = authHeader.substring(7);
        logger.debug("Requests with token: {}", token);
        if (isTokenExpired(token)) {
            logger.debug("Token expired: {}", token);
            throw new RuntimeException("Invalid JWT Token");
        }
        filterChain.doFilter(request, response);
    }

    private boolean isWhiteListRequest(HttpServletRequest request) {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(method);

        return AppConstants.WHITE_LIST_APIS.stream()
                .anyMatch(whiteListApi -> {
                    Pattern pattern = Pattern.compile(whiteListApi.getUrlPattern());
                    Matcher matcher = pattern.matcher(requestURI);
                    return whiteListApi.getMethod().equals(httpMethod) && matcher.matches();
                });
    }

    private void writeResponseError(HttpServletResponse response) throws IOException {

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", "Invalid token");

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), errorDetails);
    }

    private boolean isTokenExpired(String token) {
        Claims claims = Jwts.
                parserBuilder()
                .setSigningKey(generateSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date());
    }

    private Key generateSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}