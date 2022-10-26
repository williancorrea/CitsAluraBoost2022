package br.com.alura.comex.service;

import br.com.alura.comex.config.exception.NotFoundException;
import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.ClienteDto;
import br.com.alura.comex.repository.ClienteRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(@Lazy ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public List<Cliente> findByNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Cliente findByID(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException();
                });
    }

    @Transactional
    public Cliente cadastrar(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDto.getNome());
        return cadastrar(cliente);
    }

    @Transactional
    public Cliente atualizar(ClienteDto clienteDto) {
        //TODO: FAzer o tratamento de nulo
        Cliente cliente = this.findByID(clienteDto.getId());
        cliente.setNome(clienteDto.getNome());
        return cadastrar(cliente);
    }

    private Cliente cadastrar(Cliente cliente) {
        return clienteRepository.saveAndFlush(cliente);
    }

    @Transactional
    public void deletar(Long id) {
        clienteRepository.delete(this.findByID(id));
    }
}
