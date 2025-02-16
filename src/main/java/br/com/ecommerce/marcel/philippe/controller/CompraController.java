package br.com.ecommerce.marcel.philippe.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.marcel.philippe.dto.CompraDTO;
import br.com.ecommerce.marcel.philippe.dto.RelatorioDTO;
import br.com.ecommerce.marcel.philippe.service.CompraService;
import br.com.ecommerce.marcel.philippe.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;


@RestController
public class CompraController {

	@Autowired
	private CompraService compraService;

	@Autowired
	private RelatorioService relatorioService;

	@Operation(summary = "Lista todas as compras")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de compras retornada com sucesso"), })
	@GetMapping("/shopping")
	@ResponseStatus(HttpStatus.OK)
	public List<CompraDTO> getCompras() {
		List<CompraDTO> produtos = compraService.getAll();
		return produtos;
	}

	@Operation(summary = "Lista todas as compras de um usuário específico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de compras retornada com sucesso"), })
	@GetMapping("/shopping/shopByUser/{userIdentifier}")
	@ResponseStatus(HttpStatus.OK)
	public List<CompraDTO> getCompras(@PathVariable String userIdentifier) {
		List<CompraDTO> produtos = compraService.getByUser(userIdentifier);
		return produtos;
	}

	@Operation(summary = "Lista todas as compras de um usuário maior que uma determinada data")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de compras retornada com sucesso"), })
	@GetMapping("/shopping/shopByDate")
	@ResponseStatus(HttpStatus.OK)
	public List<CompraDTO> getTodasAsCompras(
			@RequestParam(name = "data", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") String date) {
		List<CompraDTO> produtos = compraService.getByDate(date);
		return produtos;
	}
	
	@Operation(summary = "Salvar Compras")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Compras salvas com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro ao salvar compras") })
	@PostMapping("/shopping")
	@ResponseStatus(HttpStatus.CREATED)
	public CompraDTO newCompra(@Valid @RequestHeader(name = "key", required=true) String key, @RequestBody CompraDTO compraDTO) {
		return compraService.save(compraDTO, key);
	}

	@Operation(summary = "Lista todas as compras de um usuário a partir ou entre duas datas")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de compras retornada com sucesso"), })
	@GetMapping("/shopping/search")
	@ResponseStatus(HttpStatus.OK)
	public List<CompraDTO> getComprasByFilter(
			@RequestParam(name = "dataInicio", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
			@RequestParam(name = "dataFim", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim,
			@RequestParam(name = "valorMinimo", required = false) Float valorMinimo) {
		return relatorioService.getComprasByFilter(dataInicio, dataFim, valorMinimo);
	}

	@Operation(summary = "Lista todas as compras de um usuário entre duas datas")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de compras retornada com sucesso"), })
	@GetMapping("/shopping/report")
	@ResponseStatus(HttpStatus.OK)
	public RelatorioDTO getRelatorioByDate(
			@RequestParam(name = "dataInicio", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataInicio,
			@RequestParam(name = "dataFim", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataFim) {
		return relatorioService.getReportByDate(dataInicio, dataFim);
	}
}
