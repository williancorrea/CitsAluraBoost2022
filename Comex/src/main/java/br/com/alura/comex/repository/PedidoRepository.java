package br.com.alura.comex.repository;

import br.com.alura.comex.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {


    @Query("select count(p) from Pedido p where p.cliente.id = :clienteId")
    int contarPedidosPorCliente(Long clienteId);

}
