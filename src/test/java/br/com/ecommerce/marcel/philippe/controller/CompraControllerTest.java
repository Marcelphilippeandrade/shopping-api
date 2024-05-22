package br.com.ecommerce.marcel.philippe.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.ecommerce.marcel.philippe.dto.CompraDTO;
import br.com.ecommerce.marcel.philippe.dto.ItemDTO;
import br.com.ecommerce.marcel.philippe.service.CompraService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
class CompraControllerTest {
	

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CompraService compraService;
	
	private CompraDTO compraDto;
	private List<CompraDTO> compras;
	
	private static final float COMPRA_TOTAL = 1000F;
	private static final float PRODUTO_PRECO = 1000F;
	private static final String PRODUTO_IDENTIFICADOR = "tv";
	private static final String URL_BASE = "/shopping";
	private static final String USUARIO_IDENTIFICADOR = "marcel";
	private static final LocalDateTime COMPRA_DATA = LocalDateTime.now();
	
	@BeforeEach
	public void setUp() {
		compras = new ArrayList<>();
		compraDto = obterDadosCompra();
		compras.add(compraDto);
		BDDMockito.given(this.compraService.save(Mockito.any(CompraDTO.class))).willReturn(compraDto);
	}

	@Test
	public void deveRetornarTodasAsCompras() throws Exception {
		BDDMockito.given(this.compraService.getAll()).willReturn(compras);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(compras.getFirst().getUserIdentifier())))
				.andExpect(content().string(containsString(Float.toString(compras.getFirst().getTotal()))))
				.andExpect(content().string(containsString(compras.getFirst().getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))));
	}
	
	@Test
	public void deveRetornarTodasAsComprasDeUmUsuario() throws Exception {
		BDDMockito.given(this.compraService.getByUser(USUARIO_IDENTIFICADOR)).willReturn(compras);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/shopByUser/" + USUARIO_IDENTIFICADOR)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(compras.getFirst().getUserIdentifier())))
				.andExpect(content().string(containsString(Float.toString(compras.getFirst().getTotal()))))
				.andExpect(content().string(containsString(compras.getFirst().getItems().getFirst().getProductIdentifier())))
				.andExpect(content().string(containsString(Float.toString(compras.getFirst().getItems().getFirst().getPrice()))));
	}
	
	@Test
	public void deveRetornarTodasAsComprasDeUmaDeterminadaData() throws Exception {
		BDDMockito.given(this.compraService.getByDate(COMPRA_DATA.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))).willReturn(compras);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/shopByDate" + "?date=" + COMPRA_DATA.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(compras.getFirst().getUserIdentifier())))
				.andExpect(content().string(containsString(Float.toString(compras.getFirst().getTotal()))))
				.andExpect(content().string(containsString(compras.getFirst().getItems().getFirst().getProductIdentifier())))
				.andExpect(content().string(containsString(Float.toString(compras.getFirst().getItems().getFirst().getPrice()))));
	}
	
	@Test
	public void deveSalvarUmaNovaCompra() throws Exception {
		BDDMockito.given(this.compraService.save(compraDto)).willReturn(compraDto);
		
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(Float.toString(COMPRA_TOTAL))))
				.andExpect(content().string(containsString(USUARIO_IDENTIFICADOR)))
				.andExpect(content().string(containsString(COMPRA_DATA.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))))
				.andExpect(content().string(containsString(compras.getFirst().getItems().getFirst().getProductIdentifier())))
				.andExpect(content().string(containsString(Float.toString(compras.getFirst().getItems().getFirst().getPrice()))));
	}
	
	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		CompraDTO compraDto = obterDadosCompra();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper.writeValueAsString(compraDto);
	}

	private CompraDTO obterDadosCompra() {
		CompraDTO compraDto = new CompraDTO();
		compraDto.setTotal(COMPRA_TOTAL);
		compraDto.setUserIdentifier(USUARIO_IDENTIFICADOR);
		compraDto.setDate(COMPRA_DATA);
		
		List<ItemDTO> itensDto = new ArrayList<ItemDTO>();
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setProductIdentifier(PRODUTO_IDENTIFICADOR);
		itemDto.setPrice(PRODUTO_PRECO);
		itensDto.add(itemDto);
		
		compraDto.setItems(itensDto);
		return compraDto;
	}
}
