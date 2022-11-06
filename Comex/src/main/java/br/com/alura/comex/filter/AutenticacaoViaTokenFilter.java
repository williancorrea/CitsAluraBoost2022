package br.com.alura.comex.filter;

import br.com.alura.comex.service.AutenticacaoService;
import br.com.alura.comex.service.TokenService;
import br.com.alura.comex.model.Usuario;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private AutenticacaoService autenticacaoService;

    public AutenticacaoViaTokenFilter(
            @Lazy TokenService tokenService,
            @Lazy AutenticacaoService autenticacaoService) {
        this.tokenService = tokenService;
        this.autenticacaoService = autenticacaoService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);
        if (tokenService.isTokenValido(token)) {
            autenticarCliente(token);
        }
        filterChain.doFilter(request, response);
    }

    private void autenticarCliente(String token) {
        Long iDUsuario = tokenService.getIdUsuario(token);
        Usuario usuario = autenticacaoService.findUsuarioPorId(iDUsuario);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()));
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.replace("Bearer ", "");
    }
}
