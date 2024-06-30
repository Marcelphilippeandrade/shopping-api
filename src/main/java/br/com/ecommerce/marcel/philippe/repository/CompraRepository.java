package br.com.ecommerce.marcel.philippe.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.ecommerce.marcel.philippe.modelo.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long>, RelatorioRepository {

	public List<Compra> findAllByUserIdentifier(String userIdentifier);

	public List<Compra> findAllByTotalGreaterThan(Float total);

	public List<Compra> findAllByDateGreaterThan(LocalDateTime data);

}
