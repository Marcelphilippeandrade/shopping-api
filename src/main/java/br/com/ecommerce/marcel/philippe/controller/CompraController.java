package br.com.ecommerce.marcel.philippe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.marcel.philippe.dto.CompraDTO;
import br.com.ecommerce.marcel.philippe.service.CompraService;
import jakarta.validation.Valid;

@RestController
public class CompraController {

	@Autowired
	private CompraService compraService;

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
	public CompraDTO newCompra(@Valid @RequestBody CompraDTO compraDTO) {
		return compraService.save(compraDTO);
	}
}
