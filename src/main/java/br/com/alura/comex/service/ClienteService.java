package br.com.alura.comex.service;

import br.com.alura.comex.config.exception.NotFoundException;
import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.dto.ClienteDto;
import br.com.alura.comex.repository.ClienteRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final MessageSource messageSource;
    private final ClienteRepository clienteRepository;

    public ClienteService(@Lazy MessageSource messageSource, @Lazy ClienteRepository clienteRepository) {
        this.messageSource = messageSource;
        this.clienteRepository = clienteRepository;
    }

    public Page<Cliente> listarTodos(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(messageSource.getMessage("client.not-found", new Object[]{id}, LocaleContextHolder.getLocale()));
        });
    }

    @Transactional
    public Cliente inserir(ClienteDto clienteDto) {
        return inserir(clienteDto.convert());
    }

    @Transactional
    public Cliente inserir(Cliente cliente) {
        cliente.setId(null);
        return persist(cliente);
    }

    @Transactional
    public Cliente atualizar(ClienteDto clienteDto) {
        this.buscarPorId(clienteDto.getId());
        return atualizar(clienteDto.convert());
    }

    @Transactional
    public Cliente atualizar(Cliente cliente) {
        this.buscarPorId(cliente.getId());
        return persist(cliente);
    }

    @Transactional
    public void remover(Long id) {
        clienteRepository.delete(this.buscarPorId(id));
    }

    protected Cliente persist(Cliente cliente) {
        return clienteRepository.saveAndFlush(cliente);
    }
}
