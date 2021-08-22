package com.packt.cardatabase.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;


// request : sign in user by using body POST
// response : giving the to token

// user access different url by using Authorization header request
public class AuthenticationService {

    //The JSON web token contains three different parts, separated by dots:

    //The first part is the header that defines the type of the token and the
    //hashing algorithm.

    //The second part is the payload that, typically, in the case of
    //authentication, contains information pertaining to the user.

    //The third part is the signature that is used to verify that the token hasn't
    //been changed along the way.

    // The following is an example of a JWT //token:
    //eyJhbGciOiJIUzI1NiJ9.
    //eyJzdWIiOiJKb2UifD.
    //ipevRNuRP6HflG8cFKnmUPtypruRC4fc1DWtoLL62SY

    static final long EXPIRATION_TIME = 864_000_00;  // 1 day in milliseconds
    static  final String SIGN_IN_KEY =  "SecretKey";
    static final String PREFIX = "Bearer";

    // add token to Authorization header
    static public void addToken(HttpServletResponse response, String username) {
        String JwtToken = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SIGN_IN_KEY)
                .compact();

        response.addHeader("Authorization", PREFIX + " " + JwtToken);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");

    }


    // get token from Authorization header
    static public Authentication getAuthentication (HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SIGN_IN_KEY)
                    .parseClaimsJws(token.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }
        }
        return null;
    }
}
