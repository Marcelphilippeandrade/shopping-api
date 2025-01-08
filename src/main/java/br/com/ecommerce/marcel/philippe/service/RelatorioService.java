package br.com.ecommerce.marcel.philippe.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.ecommerce.marcel.philippe.dto.CompraDTO;
import br.com.ecommerce.marcel.philippe.dto.DTOConverter;
import br.com.ecommerce.marcel.philippe.dto.RelatorioDTO;
import br.com.ecommerce.marcel.philippe.modelo.Compra;
import br.com.ecommerce.marcel.philippe.repository.RelatorioRepository;

@Service
public class RelatorioService {

	@Autowired
	@Qualifier("relatorioRepositoryImpl")
	private RelatorioRepository relatorioRepository;

	public List<CompraDTO> getComprasByFilter(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo) {
		List<Compra> compras = relatorioRepository.getComprasByFilters(dataInicio, dataFim, valorMinimo);
		return compras.stream().map(DTOConverter::convert).collect(Collectors.toList());
	}

	public RelatorioDTO getReportByDate(Date dataInicio, Date dataFim) {
		return relatorioRepository.getRelatorioByDate(dataInicio, dataFim);
	}

}
