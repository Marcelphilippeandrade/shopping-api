package br.com.ecommerce.marcel.philippe.modelo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ecommerce.marcel.philippe.dto.CompraDTO;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "compra")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String userIdentifier;
	private float total;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "item", joinColumns = @JoinColumn(name = "compra_id"))
	private List<Item> items;

	public static Compra convert(CompraDTO compraDto) {
		Compra compra = new Compra();
		compra.setUserIdentifier(compraDto.getUserIdentifier());
		compra.setTotal(compraDto.getTotal());
		compra.setDate(compraDto.getDate());
		compra.setItems(compraDto.getItems().stream().map(Item::convert).collect(Collectors.toList()));
		return compra;
	}
}
