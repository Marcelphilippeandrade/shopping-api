package br.com.ecommerce.marcel.philippe.repository.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import br.com.ecommerce.marcel.philippe.dto.RelatorioDTO;
import br.com.ecommerce.marcel.philippe.modelo.Compra;
import br.com.ecommerce.marcel.philippe.repository.RelatorioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

public class RelatorioRepositoryImpl implements RelatorioRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Compra> getComprasByFilters(Date dataInicio, Date dataFim, Float valorMinimo) {

		StringBuilder sb = new StringBuilder();
		sb.append("select c ");
		sb.append("from compra c ");
		sb.append("where c.date >= :dataInicio ");

		if (dataFim != null) {
			sb.append("and c.date <= :dataFim ");
		}
		if (valorMinimo != null) {
			sb.append("and c.total <= :valorMinimo ");
		}

		Query query = entityManager.createQuery(sb.toString());
		LocalDateTime dataInicioLocalDateTime = dataInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		query.setParameter("dataInicio", dataInicioLocalDateTime);

		if (dataFim != null) {
			LocalDateTime dataFimLocalDateTime = dataFim.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			query.setParameter("dataFim", dataFimLocalDateTime);
		}

		if (valorMinimo != null) {
			query.setParameter("valorMinimo", valorMinimo);
		}
		return query.getResultList();
	}

	@Override
	public RelatorioDTO getRelatorioByDate(Date dataInicio, Date dataFim) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(sc.id), sum(sc.total), avg(sc.total)");
		sb.append("from shopping.compra sc ");
		sb.append("where sc.date >= :dataInicio ");
		sb.append("and sc.date <= :dataFim ");

		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);

		Object[] result = (Object[]) query.getSingleResult();
		RelatorioDTO relatorioDTO = new RelatorioDTO();
		relatorioDTO.setQuantidade((Integer.valueOf(result[0].toString())));
		relatorioDTO.setTotal((Double) result[1]);
		relatorioDTO.setMedia((Double) result[2]);
		return relatorioDTO;
	}

}
