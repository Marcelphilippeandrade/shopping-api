package br.com.ecommerce.marcel.philippe.dto;

import java.util.stream.Collectors;

import br.com.ecommerce.marcel.philippe.modelo.Compra;
import br.com.ecommerce.marcel.philippe.modelo.Item;

public class DTOConverter {

	public static ItemDTO convert(Item item) {
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setProductIdentifier(item.getProductIdentifier());
		itemDTO.setPrice(item.getPrice());
		return itemDTO;
	}

	public static CompraDTO convert(Compra compra) {
		CompraDTO compraDTO = new CompraDTO();
		compraDTO.setUserIdentifier(compra.getUserIdentifier());
		compraDTO.setTotal(compra.getTotal());
		compraDTO.setDate(compra.getDate());
		compraDTO.setItems(compra.getItems().stream().map(DTOConverter::convert).collect(Collectors.toList()));
		return compraDTO;
	}

}
