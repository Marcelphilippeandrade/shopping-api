package br.com.ecommerce.marcel.philippe.repository;

import java.util.Date;
import java.util.List;

import br.com.ecommerce.marcel.philippe.dto.RelatorioDTO;
import br.com.ecommerce.marcel.philippe.modelo.Compra;

public interface RelatorioRepository {

	public List<Compra> getComprasByFilters(Date dataInicio, Date dataFim, Float valorMinimo);

	public RelatorioDTO getRelatorioByDate(Date dataInicio, Date dataFim);

}
