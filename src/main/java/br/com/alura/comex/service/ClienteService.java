package br.com.alura.comex.service;

import br.com.alura.comex.config.exception.NotFoundException;
import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.dto.ClienteDto;
import br.com.alura.comex.repository.ClienteRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(@Lazy ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException();
                });
    }

    @Transactional
    public Cliente inserir(ClienteDto clienteDto) {
        clienteDto.setId(null);
        return persist(clienteDto.convert());
    }

    @Transactional
    public Cliente inserir(Cliente cliente) {
        cliente.setId(null);
        return persist(cliente);
    }

    @Transactional
    public Cliente atualizar(ClienteDto clienteDto) {
        this.buscarPorId(clienteDto.getId());
        return persist(clienteDto.convert());
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

    /**
     * Metodo utilizado para efetuar validações e persistencia na base de dados
     *
     * @param cliente
     * @return
     */
    protected Cliente persist(Cliente cliente) {
        return clienteRepository.saveAndFlush(cliente);
    }
}
