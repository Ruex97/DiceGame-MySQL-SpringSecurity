package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component  // This annotation tells spring that we need this class to become a spring bean
@RequiredArgsConstructor    // Will create a constructor with any final field declared
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    /**
     * This method can intercept the request, extract data from it and provide new data
     * to the response. The FilterChain follows the chain of responsibility pattern, so it will
     * contain the list of the other filters we need to execute.
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {        // Bearer token should always start like that. If one of these two condiotions
            filterChain.doFilter(request, response);                        // are not fulfilled, we pass the request and response to the next filter
            return;
        }
        jwt = authHeader.substring(7);      // 7 is because Bearer + space =7
        username = jwtService.extractUsername(jwt);   // Extract from JWT. We need a class to manipulate JWT
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {          // Null authentication means that user is not yet authenticated
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(    // Object needed to update our security context
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));           // Enforce the token with the details of our request
                SecurityContextHolder.getContext().setAuthentication(authToken);                            // Update security context holder
            }
        }
        filterChain.doFilter(request, response);
    }
}

