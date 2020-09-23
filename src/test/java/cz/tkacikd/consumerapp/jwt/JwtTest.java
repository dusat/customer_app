package cz.tkacikd.consumerapp.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtTest {

    @Test
    void validateJwtTokenWith3PartsNoException() {
        String token =  "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MDA3MDA4NjksImV4cCI6MTYwMDcwMTA0OSwidXNlcklkIjozMjIsImVtYWlsIjoibWVuc2lrQHNlem5hbS5jeiJ9.z25iM09PsyKeczjfzpAtqiHPC6EsgGe3DazYOm2XpX4";
        String[] parts = token.split("\\.");
        Assertions.assertDoesNotThrow(() -> {
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid Token format");
            }
        });
    }

    @Test
    void validateJwtTokenWithout3PartsWithException() {
        String token =  "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MDA3MDA4NjksImV4cCI6MTYwMDcwMTA0OSwidXNlcklkIjozMjIsImVtYWlsIjoibWVuc2lrQHNlem5hbS5jeiJ";
        String[] parts = token.split("\\.");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid Token format");
            }
        });
    }
}
