package br.com.ecommerce.marcel.philippe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioDTO {
	
	private Integer quantidade;
	private Double total;
	private Double media;

}
