package br.com.alura.comex.config.security;

import br.com.alura.comex.model.Usuario;
import br.com.alura.comex.repository.UsuarioRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final MessageSource messageSource;

    public AutenticacaoService(@Lazy UsuarioRepository usuarioRepository,
                               @Lazy MessageSource messageSource) {
        this.usuarioRepository = usuarioRepository;
        this.messageSource = messageSource;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailEqualsIgnoreCase(email)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(messageSource.getMessage("unauthenticated_user", null, LocaleContextHolder.getLocale()));
                });
    }

    public Usuario findUsuarioPorId(Long id){
        return usuarioRepository.findById(id).get();
    }
}
