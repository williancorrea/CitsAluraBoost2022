package br.com.alura.comex.service;

import br.com.alura.comex.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expirationInDays}")
    private Long expirationInDays;

    @Value("${forum.jwt.secret}")
    private String secret;


    public String gerarToken(Authentication authentication) {
        Usuario logado = (Usuario) authentication.getPrincipal();
        return Jwts.builder()
                .setIssuer("API do forum da alura")
                .setSubject(logado.getId().toString())
                .setIssuedAt(Date.from(OffsetDateTime.now().toInstant()))
                .setExpiration(Date.from(OffsetDateTime.now().plusDays(this.expirationInDays).toInstant()))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }
    public boolean isTokenValido(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdUsuario(String token){
        var claims = Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public Long getIdUsuario(HttpHeaders headers){
        var token = headers.get("authorization").get(0).replace("Bearer ", "");
        return getIdUsuario(token);
    }
}
