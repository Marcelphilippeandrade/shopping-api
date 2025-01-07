package br.com.ecommerce.marcel.philippe.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import br.com.ecommerce.marcel.philippe.dto.RelatorioDTO;
import br.com.ecommerce.marcel.philippe.service.CompraService;
import br.com.ecommerce.marcel.philippe.service.RelatorioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
class CompraControllerTest {
	

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CompraService compraService;
	
	@MockBean
	private RelatorioService relatorioService;
	
	private CompraDTO compraDto;
	private RelatorioDTO relatorioDto;
	private List<CompraDTO> compras;
	
	private static final float COMPRA_TOTAL = 1000F;
	private static final float PRODUTO_PRECO = 1000F;
	private static final String PRODUTO_IDENTIFICADOR = "tv";
	private static final String URL_BASE = "/shopping";
	private static final String USUARIO_IDENTIFICADOR = "06618938635";
	private static final LocalDate COMPRA_DATA = LocalDate.now();

	private static final Date DATA_INICIAL = new Date();
	private static final Date DATA_FINAL = new Date();
	private static final Float VALOR_MINIMO = 500F;

	private static final Integer QUANTIDADE = 100;
	private static final Double MEDIA = 50D;
	private static final Double TOTAL = 1000D;
	private static final String KEY = "0d769a46-3919-4476-bc6d-f812da60144f";
	
	@BeforeEach
	public void setUp() {
		compras = new ArrayList<>();
		compraDto = obterDadosCompra();
		relatorioDto = obterRelatorioCompra();
		compras.add(compraDto);
		BDDMockito.given(this.compraService.save(Mockito.any(CompraDTO.class), anyString())).willReturn(compraDto);
	}

	@Test
	public void deveRetornarTodasAsCompras() throws Exception {
		BDDMockito.given(this.compraService.getAll()).willReturn(compras);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(compras.getFirst().getUserIdentifier())))
				.andExpect(content().string(containsString(Float.toString(compras.getFirst().getTotal()))))
				.andExpect(content().string(containsString(compras.getFirst().getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))));
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
		BDDMockito.given(this.compraService.getByDate(COMPRA_DATA.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))).willReturn(compras);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/shopByDate" + "?date=" + COMPRA_DATA.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(compras.getFirst().getUserIdentifier())))
				.andExpect(content().string(containsString(Float.toString(compras.getFirst().getTotal()))))
				.andExpect(content().string(containsString(compras.getFirst().getItems().getFirst().getProductIdentifier())))
				.andExpect(content().string(containsString(Float.toString(compras.getFirst().getItems().getFirst().getPrice()))));
	}
	
	@Test
	public void deveSalvarUmaNovaCompra() throws Exception {
		BDDMockito.given(this.compraService.save(compraDto, KEY)).willReturn(compraDto);
		
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.header("key", KEY)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().string(containsString(Float.toString(COMPRA_TOTAL))))
				.andExpect(content().string(containsString(USUARIO_IDENTIFICADOR)))
				.andExpect(content().string(containsString(COMPRA_DATA.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))))
				.andExpect(content().string(containsString(compras.getFirst().getItems().getFirst().getProductIdentifier())))
				.andExpect(content().string(containsString(Float.toString(compras.getFirst().getItems().getFirst().getPrice()))));
	}
	
	@Test
	public void deveBuscarComprasDadoUmaDataInicialOuUmaDataFinalOuUmValorMinimo() throws Exception {
		String dataInicio = new SimpleDateFormat("dd/MM/yyyy").format(DATA_INICIAL);
		String dataFim = new SimpleDateFormat("dd/MM/yyyy").format(DATA_FINAL);
		
		BDDMockito.given(this.relatorioService.getComprasByFilter(Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(Float.class))).willReturn(compras);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/search" + "?dataInicio=" + dataInicio + "&" + "dataFim=" + dataFim + "&" + "valorMinimo=" + VALOR_MINIMO)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(compras.getFirst().getUserIdentifier())));
	}
	
	@Test
	public void deveRetornarRelatorioDeComprasDadoUmaDataInicialEUmaDataFinal() throws Exception {
		String dataInicio = new SimpleDateFormat("dd/MM/yyyy").format(DATA_INICIAL);
		String dataFim = new SimpleDateFormat("dd/MM/yyyy").format(DATA_FINAL);
		
		BDDMockito.given(this.relatorioService.getReportByDate(Mockito.any(Date.class), Mockito.any(Date.class))).willReturn(relatorioDto);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/report" + "?dataInicio=" + dataInicio + "&" + "dataFim=" + dataFim)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(Integer.toString(relatorioDto.getQuantidade()))))
				.andExpect(content().string(containsString(Double.toString(relatorioDto.getMedia()))))
				.andExpect(content().string(containsString(Double.toString(relatorioDto.getTotal()))));
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
	
	private RelatorioDTO obterRelatorioCompra() {
		RelatorioDTO relatorioDto = new RelatorioDTO();
		relatorioDto.setQuantidade(QUANTIDADE);
		relatorioDto.setMedia(MEDIA);
		relatorioDto.setTotal(TOTAL);
		return relatorioDto;
	}
}
