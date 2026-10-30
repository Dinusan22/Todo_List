package DS.TECH.HelloWorld.Util;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
   private final String SECRET="hello i am dinusan i from jaffna i am single";
    private final long EXPIRATION=1000*60*5;
   private final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
//   private final Key secretKey;
//    public JwtUtil(@Value("${JWT_SECRET}") String secret) {
//       this.secretKey = Keys.hmacShaKeyFor(
//               secret.getBytes(StandardCharsets.UTF_8)
//       );
//   }

    public String generateToken(String email){
        return Jwts.builder()//creates a new JWT builder object
                .setSubject(email)//This adds the email to the JWT's sub (subject) claim  "sub":"student@gmail.com"
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(secretKey,SignatureAlgorithm.HS256)
                .compact();//creates the completed JWT as a String

    }
    public String ExtractEmail(String token){
        return Jwts.parser()//A parser reads data in a particular format, understands its structure, and converts it into something your Java program can work with.
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public boolean validateJwtToken(String token){
        try{
            ExtractEmail(token);

            return true;
        }catch (JwtException exception){
            return false;
        }
    }
}
