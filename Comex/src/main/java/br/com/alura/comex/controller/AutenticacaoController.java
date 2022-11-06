package br.com.alura.comex.controller;

import br.com.alura.comex.model.dto.LoginForm;
import br.com.alura.comex.model.dto.TokenDto;
import br.com.alura.comex.service.AutenticacaoService;
import br.com.alura.comex.service.TokenService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final AutenticacaoService autenticacaoService;
    private final TokenService tokenService;

    public AutenticacaoController(@Lazy AuthenticationManager authenticationManager,
                                  @Lazy AutenticacaoService autenticacaoService,
                                  @Lazy TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.autenticacaoService = autenticacaoService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
        var userDetails = autenticacaoService.loadUserByUsername(form.getEmail());
        var token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), form.getSenha());
        try {
            var authentication = authenticationManager.authenticate(token);
            var response = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenDto(response, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
