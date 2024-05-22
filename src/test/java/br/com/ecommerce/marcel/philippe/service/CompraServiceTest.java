package br.com.ecommerce.marcel.philippe.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ecommerce.marcel.philippe.dto.CompraDTO;
import br.com.ecommerce.marcel.philippe.dto.ItemDTO;
import br.com.ecommerce.marcel.philippe.modelo.Compra;
import br.com.ecommerce.marcel.philippe.modelo.Item;
import br.com.ecommerce.marcel.philippe.repository.CompraRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class CompraServiceTest {
	
	@MockBean
	private CompraRepository compraRepository;
	
	@Autowired
	private CompraService compraService;
	
	private static final long COMPRA_TOTAL = 100L;
	private static final long COMPRA_ID = 1L;
	private static final String USUARIO_IDENTIFICADOR = "marcel";
	private static final float ITEM_PRECO = 1000F;
	private static final String PRODUTO_IDENTIFICADOR = "tv";
	private static final long PRODUTO_ID = 1L;
	private static final float TOTAL_COMPRA = 1000F;
	private static final LocalDateTime DATA_COMPRA = LocalDateTime.now();
	
	@BeforeEach
	public void setUp() {
		ArrayList<Compra> compras = new ArrayList<Compra>();
		Compra compra = new Compra();
		compra.setUserIdentifier(USUARIO_IDENTIFICADOR);
		compra.setId(COMPRA_ID);
		compra.setDate(DATA_COMPRA);
		compra.setTotal(COMPRA_TOTAL);
		
		ArrayList<Item> items = new ArrayList<Item>();
		Item item = new Item();
		item.setProductIdentifier(PRODUTO_IDENTIFICADOR);
		item.setPrice(ITEM_PRECO);
		items.add(item);
		
		compra.setItems(items);
		compras.add(compra);
		
		Compra compra1 = new Compra();
		compra1.setUserIdentifier(USUARIO_IDENTIFICADOR);
		compra1.setId(COMPRA_ID);
		compra1.setDate(DATA_COMPRA);
		compra1.setTotal(COMPRA_TOTAL);
		compra1.setItems(items);
		
		compras.add(compra1);
		
		BDDMockito.given(compraRepository.save(any())).willReturn(compra);
		BDDMockito.given(compraRepository.saveAll(Mockito.anyList())).willReturn(compras);
		BDDMockito.given(compraRepository.findAll()).willReturn(compras);
		BDDMockito.given(compraRepository.findAllByUserIdentifier(USUARIO_IDENTIFICADOR)).willReturn(compras);
		BDDMockito.given(compraRepository.findAllByDateGreaterThan(Mockito.any())).willReturn(compras);
		BDDMockito.given(compraRepository.findById(PRODUTO_ID)).willReturn(Optional.of(compra));
	}
	
	@Test
	public void deveRetornarTodasAsCompras() {
		List<CompraDTO> compras = this.compraService.getAll();
		assertNotNull(compras);
		assertTrue("A lista de compras deve ter mais de 1 compra!", compras.size() > 1);
	}
	
	@Test
	public void deveRetornarTodasAsComprasDeUmUsuario() {
		List<CompraDTO> compras = this.compraService.getByUser(USUARIO_IDENTIFICADOR);
		assertNotNull(compras);
		assertTrue("A lista de compras deve ter mais de 1 compra!", compras.size() > 1);
		assertEquals(USUARIO_IDENTIFICADOR, compras.get(0).getUserIdentifier());
	}
	
	@Test
	public void deveRetornarTodasAsComprasApartirDeUmaDeterminadaData() {
		List<CompraDTO> compras = this.compraService.getByDate(DATA_COMPRA.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		assertNotNull(compras);
		assertEquals(DATA_COMPRA, compras.get(0).getDate());
	}

	@Test
	public void deveRetornarUmaCompraApartirDoId() {
		CompraDTO compraDto = this.compraService.findById(PRODUTO_ID);
		assertNotNull(compraDto);
		assertEquals(USUARIO_IDENTIFICADOR, compraDto.getUserIdentifier());
	}
	
	@Test
	public void naoDeveRetornarUmaCompraApartirDoId() {
		CompraDTO compraDto = this.compraService.findById(2L);
		assertNull(compraDto);
	}
	
	@Test
	public void deveSalvarUmaCompra() {
		CompraDTO compraDto = this.compraService.save(obterDadosCompraDTO());
		assertNotNull(compraDto);
		assertEquals(USUARIO_IDENTIFICADOR, compraDto.getUserIdentifier());
	}
	
	private CompraDTO obterDadosCompraDTO() {
		CompraDTO compraDto = new CompraDTO();
		compraDto.setUserIdentifier(USUARIO_IDENTIFICADOR);
		compraDto.setTotal(TOTAL_COMPRA);
		compraDto.setDate(DATA_COMPRA);
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setProductIdentifier(PRODUTO_IDENTIFICADOR);
		itemDto.setPrice(ITEM_PRECO);
		
		ArrayList<ItemDTO> items = new ArrayList<ItemDTO>();
		items.add(itemDto);
		
		compraDto.setItems(items);
		return compraDto;
	}
}
