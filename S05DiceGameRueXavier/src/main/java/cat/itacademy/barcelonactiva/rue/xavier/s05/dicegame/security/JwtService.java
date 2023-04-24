package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "6E3272357538782F413F4428472B4B6250655367566B59703373367639792442";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);  // We extract all the claims
        return claimsResolver.apply(claims);            // With function passed as parameter, we can extract any claim
    }

    /**
     * This method extract all claims contained by a token.
     * @param token
     * @return All claims within a token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())          // Key needed to decode our token
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * To generate a token only with our userDetails
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generate a token out of our extraclaims and userDetails
     * @param extraClaims
     * @param userDetails
     * @return
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // extraClaims map is what we need to pass any information to store in a token f.e: authorities
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    /**
     * We want to validate if token passed match with the one contained in user
     * @param token
     * @param userDetails
     * @return
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    /**
     * Check if token is expired
     * @param token
     * @return true if it's not expired. false any other case.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract expiration date of our token
     * @param token
     * @return
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}


