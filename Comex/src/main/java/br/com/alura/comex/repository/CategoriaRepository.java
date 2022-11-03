package br.com.alura.comex.repository;

import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.model.projections.CategoriaPedidosProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query(
            nativeQuery = true,
            value = """
                        select c.nome,
                           (count(p.id)) as quantidade,
                           (sum(p.preco_unitario)) as montante
                        from categorias c
                            join produtos p on c.id = p.categoria_id
                        GROUP BY c.id LIMIT 1;    
                    """)
    List<CategoriaPedidosProjection> relatorioPedidos();

}
