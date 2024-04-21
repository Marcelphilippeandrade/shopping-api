package br.com.ecommerce.marcel.philippe.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ecommerce.marcel.philippe.dto.CompraDTO;
import br.com.ecommerce.marcel.philippe.modelo.Compra;
import br.com.ecommerce.marcel.philippe.repository.CompraRepository;

@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepository;

	public List<CompraDTO> getAll() {
		List<Compra> compras = compraRepository.findAll();
		return compras.stream().map(CompraDTO::convert).collect(Collectors.toList());
	}

	public List<CompraDTO> getByUser(String userIdentifier) {
		List<Compra> compras = compraRepository.findAllByUserIdentifier(userIdentifier);
		return compras.stream().map(CompraDTO::convert).collect(Collectors.toList());
	}

	public List<CompraDTO> getByDate(CompraDTO compraDTO) {
		List<Compra> compras = compraRepository.findAllByDateGreaterThan(compraDTO.getDate());
		return compras.stream().map(CompraDTO::convert).collect(Collectors.toList());
	}

	public CompraDTO findById(long produtoId) {
		Optional<Compra> compra = compraRepository.findById(produtoId);
		if (compra.isPresent()) {
			return CompraDTO.convert(compra.get());
		}
		return null;
	}

	public CompraDTO save(CompraDTO compraDTO) {
		compraDTO.setTotal(compraDTO.getItems().stream().map(i -> i.getPrice()).reduce((float) 0, Float::sum));
		Compra compra = Compra.convert(compraDTO);
		compra.setDate(LocalDateTime.now());
		compra = compraRepository.save(compra);
		return CompraDTO.convert(compra);
	}
}
