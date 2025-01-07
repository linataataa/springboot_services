//h
package hyfz.hyfz.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JWTService {
    private static final String SECRET_KEY = "69344B734C31754949694A6C74717034505A583778574673714533765275786E";


    public String issueToken(String subject) {
        return issueToken(subject, Map.of());
    }

    public String issueToken(String subject, String... scopes) {
        return issueToken(subject, Map.of("scopes", scopes));
    }

    public String issueToken(String subject, List<String> scopes) {
        return issueToken(subject, Map.of("scopes", scopes));
    }


    public String issueToken(
            String subject,
            Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("hyfz")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(
                        Date.from(
                                Instant.now().plus(15, ChronoUnit.DAYS)
                        )
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean isTokenValid(String jwt, String username) {
        String subject = getSubject(jwt);
        return subject.equals(username) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        Date today = Date.from(Instant.now());
        return getClaims(jwt).getExpiration().before(today);
    }


//    String  extractuseremail(String token){
//
//      return extractClaims(token , Claims::getSubject);
//  }
//
//
//  public String generatetoken(UserDetails userDetails){
//
//      return generatetoken(new HashMap<>() , userDetails);
//  }
//public String generatetoken(
//        Map<String , Objects> extraClaims,
//        UserDetails userDetails) {
//      return Jwts.builder().setClaims(extraClaims)
//              .setSubject(userDetails.getUsername())
//              .setIssuedAt(new Date())
//              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60*24 ))
//              .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//              .compact();
//}
//    public <T> T extractClaims(String token, Function<Claims , T> claimsResolver ){
//      final Claims claims = extracAllClaims(token);
//      return claimsResolver.apply(claims);
//    }
//
//  private Claims extracAllClaims(String token){
//      return Jwts
//              .parserBuilder()
//              .setSigningKey(getSignInKey())
//              .build()
//              .parseClaimsJwt(token)
//              .getBody();
//  }
//
//    private Key getSignInKey() {
//      byte[] keyBites = Decoders.BASE64.decode(SECRET_KEY);
//      return Keys.hmacShaKeyFor(keyBites);
//    }
//
//    private Date extractExperation(String token){
//      return extractClaims(token , Claims::getExpiration);
//    }
//
//    public boolean isTokenExpired1(String token){
//      return extractExperation(token).before(new Date());
//    }
//
//    public boolean isTokenValid(String token , UserDetails userDetails){
//      //validate if a token is for a user
//        final String username = extractuseremail(token);
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
}
