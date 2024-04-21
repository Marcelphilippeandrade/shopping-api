package br.com.ecommerce.marcel.philippe.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ecommerce.marcel.philippe.modelo.Compra;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompraDTO {

	@NotBlank
	private String userIdentifier;

	private Float total;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date;

	@NotNull
	private List<ItemDTO> items;

	public static CompraDTO convert(Compra compra) {
		CompraDTO compraDto = new CompraDTO();
		compraDto.setUserIdentifier(compra.getUserIdentifier());
		compraDto.setTotal(compra.getTotal());
		compraDto.setDate(compra.getDate());
		compraDto.setItems(compra.getItems().stream().map(ItemDTO::convert).collect(Collectors.toList()));
		return compraDto;
	}
}
