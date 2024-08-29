package br.com.ecommerce.marcel.philippe.controller;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.marcel.philippe.dto.CompraDTO;
import br.com.ecommerce.marcel.philippe.dto.RelatorioDTO;
import br.com.ecommerce.marcel.philippe.service.CompraService;
import br.com.ecommerce.marcel.philippe.service.RelatorioService;
import jakarta.validation.Valid;

@RestController
public class CompraController {

	@Autowired
	private CompraService compraService;

	@Autowired
	private RelatorioService relatorioService;

	@GetMapping("/shopping")
	public List<CompraDTO> getCompras() {
		List<CompraDTO> produtos = compraService.getAll();
		return produtos;
	}

	@GetMapping("/shopping/shopByUser/{userIdentifier}")
	public List<CompraDTO> getCompras(@PathVariable String userIdentifier) {
		List<CompraDTO> produtos = compraService.getByUser(userIdentifier);
		return produtos;
	}

	@GetMapping("/shopping/shopByDate")
	public List<CompraDTO> getTodasAsCompras(@RequestParam String date) {
		List<CompraDTO> produtos = compraService.getByDate(date);
		return produtos;
	}

	@PostMapping("/shopping")
	public CompraDTO newCompra(@Valid @RequestHeader(name = "key", required=true) String key, @RequestBody CompraDTO compraDTO) {
		return compraService.save(compraDTO, key);
	}

	@GetMapping("/shopping/search")
	public List<CompraDTO> getComprasByFilter(
			@RequestParam(name = "dataInicio", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataInicio,
			@RequestParam(name = "dataFim", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataFim,
			@RequestParam(name = "valorMinimo", required = false) Float valorMinimo) {
		return relatorioService.getComprasByFilter(dataInicio, dataFim, valorMinimo);
	}

	@GetMapping("/shopping/report")
	public RelatorioDTO getRelatorioByDate(
			@RequestParam(name = "dataInicio", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataInicio,
			@RequestParam(name = "dataFim", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataFim) {
		return relatorioService.getReportByDate(dataInicio, dataFim);
	}
}
