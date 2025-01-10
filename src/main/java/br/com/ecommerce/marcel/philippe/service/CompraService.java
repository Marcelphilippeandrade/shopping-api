package br.com.ecommerce.marcel.philippe.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ecommerce.marcel.philippe.communication.ProdutoService;
import br.com.ecommerce.marcel.philippe.communication.UsuarioService;
import br.com.ecommerce.marcel.philippe.dto.CompraDTO;
import br.com.ecommerce.marcel.philippe.dto.ItemDTO;
import br.com.ecommerce.marcel.philippe.dto.ProdutoDTO;
import br.com.ecommerce.marcel.philippe.modelo.Compra;
import br.com.ecommerce.marcel.philippe.repository.CompraRepository;

@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProdutoService produtoService;

	public List<CompraDTO> getAll() {
		List<Compra> compras = compraRepository.findAll();
		return compras.stream().map(CompraDTO::convert).collect(Collectors.toList());
	}

	public List<CompraDTO> getByUser(String userIdentifier) {
		List<Compra> compras = compraRepository.findAllByUserIdentifier(userIdentifier);
		return compras.stream().map(CompraDTO::convert).collect(Collectors.toList());
	}

	public List<CompraDTO> getByDate(String data) {
		List<Compra> compras = compraRepository.findAllByDateGreaterThan(LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		return compras.stream().map(CompraDTO::convert).collect(Collectors.toList());
	}

	public CompraDTO findById(long produtoId) {
		Optional<Compra> compra = compraRepository.findById(produtoId);
		if (compra.isPresent()) {
			return CompraDTO.convert(compra.get());
		}
		return null;
	}

	public CompraDTO save(CompraDTO compraDTO, String key) {

		if (usuarioService.getUserByCpf(compraDTO.getUserIdentifier(), key) == null) {
			return null;
		}
		
		if (!existeProduto(compraDTO.getItems())) {
			return null;
		}
		
		compraDTO.setTotal(compraDTO.getItems().stream().map(i -> i.getPrice()).reduce((float) 0, Float::sum));
		Compra compra = Compra.convert(compraDTO);
		compra.setDate(LocalDate.now());
		compra = compraRepository.save(compra);
		return CompraDTO.convert(compra);
	}

	public boolean existeProduto(List<ItemDTO> items) {
		for (ItemDTO item : items) {
			ProdutoDTO produtoDTO = produtoService.getProdutoByIdentifier(item.getProductIdentifier());
			if (produtoDTO == null) {
				return false;
			}
			item.setPrice(produtoDTO.getPreco());
		}
		return true;
	}
}
