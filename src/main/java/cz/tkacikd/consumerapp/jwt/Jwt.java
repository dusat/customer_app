package cz.tkacikd.consumerapp.jwt;

import cz.tkacikd.consumerapp.constants.Constants;
import cz.tkacikd.consumerapp.domain.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Jwt {
    public static Map<String, String> generateJWTToken(Customer cst) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder()
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_EXPIRATION))
                .claim("userId", cst.getId())
                .claim("email", cst.getCustomerRegData().getEmail())
                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode(Constants.SECRET_KEY))
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    public static ResponseEntity validateJwtToken(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid Token format");
        }
        try {
            Jwts.parser().setSigningKey(TextCodec.BASE64.decode(Constants.SECRET_KEY)).parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
