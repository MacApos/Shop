package pl.coderslab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.User;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class LoginController {
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authManager;

    public String createToken( Authentication authentication){
        Instant now = Instant.now();
        long expiry = 36000L;

        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authManager.authenticate(authReq);
        String token = createToken(authentication);
        return token;
    }
}
