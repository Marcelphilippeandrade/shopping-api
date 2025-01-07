package br.com.ecommerce.marcel.philippe.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.ecommerce.marcel.philippe.modelo.Compra;

@ExtendWith(SpringExtension.class)
class CompraRepositoryTest {

	@Autowired
	private CompraRepository compraRepository;

	@MockBean
	private CompraRepository mockCompraRepository;

	private static final String USUARIO_IDENTIFICADOR = "06618938635";
	private static final LocalDate COMPRA_DATA = LocalDate.now();
	private static final float COMPRA_TOTAL = 1000F;
	private static final long COMPRA_ID = 1L;

	private ArrayList<Compra> compras;

	@BeforeEach
	public void setUp() {
		compras = new ArrayList<Compra>();

		Compra compra = new Compra();
		compra.setUserIdentifier(USUARIO_IDENTIFICADOR);
		compra.setId(COMPRA_ID);
		compra.setTotal(COMPRA_TOTAL);
		compra.setDate(COMPRA_DATA);
		compras.add(compra);
	}

	@Test
	public void deveRetornarTodasAsComprasDeUmUsuario() {
		when(mockCompraRepository.findAllByUserIdentifier(USUARIO_IDENTIFICADOR)).thenReturn(compras);
		List<Compra> comprasRetorno = compraRepository.findAllByUserIdentifier(USUARIO_IDENTIFICADOR);

		assertNotNull(comprasRetorno);
		assertEquals(compras.get(0).getUserIdentifier(), comprasRetorno.get(0).getUserIdentifier());
	}

	@Test
	public void deveRetornarTodasAsComprasDeUmValorTotalExpecifico() {
		when(mockCompraRepository.findAllByTotalGreaterThan(COMPRA_TOTAL)).thenReturn(compras);
		List<Compra> comprasRetorno = compraRepository.findAllByTotalGreaterThan(COMPRA_TOTAL);

		assertNotNull(comprasRetorno);
		assertEquals(compras.get(0).getUserIdentifier(), comprasRetorno.get(0).getUserIdentifier());
		assertEquals(compras.get(0).getTotal(), comprasRetorno.get(0).getTotal());
	}

	@Test
	public void deveRetornarTodasAsComprasDeUmaDataEspecifica() {
		when(mockCompraRepository.findAllByDateGreaterThan(COMPRA_DATA)).thenReturn(compras);
		List<Compra> comprasRetorno = compraRepository.findAllByDateGreaterThan(COMPRA_DATA);

		assertNotNull(comprasRetorno);
		assertEquals(compras.get(0).getUserIdentifier(), comprasRetorno.get(0).getUserIdentifier());
		assertEquals(compras.get(0).getTotal(), comprasRetorno.get(0).getTotal());
		assertEquals(compras.get(0).getDate(), comprasRetorno.get(0).getDate());
	}
}
