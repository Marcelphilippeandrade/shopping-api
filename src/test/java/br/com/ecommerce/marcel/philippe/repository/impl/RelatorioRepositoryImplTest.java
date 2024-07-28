package br.com.ecommerce.marcel.philippe.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.ecommerce.marcel.philippe.dto.RelatorioDTO;
import br.com.ecommerce.marcel.philippe.modelo.Compra;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

class RelatorioRepositoryImplTest {

	@InjectMocks
	private RelatorioRepositoryImpl relatorioRepository;

	@Mock
	private EntityManager entityManager;

	@Mock
	private Query query;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveRetornarAsComprasApartirDosFiltros() {
		List<Compra> expectedCompras = Arrays.asList(new Compra(), new Compra());

		when(entityManager.createQuery(anyString())).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);
		when(query.getResultList()).thenReturn(expectedCompras);

		Date dataInicio = new Date();
		Date dataFim = new Date();
		Float valorMinimo = 100.0f;

		List<Compra> compras = relatorioRepository.getComprasByFilters(dataInicio, dataFim, valorMinimo);

		assertEquals(expectedCompras, compras);
	}

	@Test
	void deveRetornarRelatorioDasComprasApartirDasDatas() {
		Object[] result = new Object[] { 1, 200.0, 100.0 };

		when(entityManager.createNativeQuery(anyString())).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);
		when(query.getSingleResult()).thenReturn(result);

		Date dataInicio = new Date();
		Date dataFim = new Date();

		RelatorioDTO relatorio = relatorioRepository.getRelatorioByDate(dataInicio, dataFim);

		assertEquals(Integer.valueOf(1), relatorio.getQuantidade());
		assertEquals(Double.valueOf(200.0), relatorio.getTotal());
		assertEquals(Double.valueOf(100.0), relatorio.getMedia());
	}

}
