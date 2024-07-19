package br.com.ecommerce.marcel.philippe.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.ecommerce.marcel.philippe.dto.CompraDTO;
import br.com.ecommerce.marcel.philippe.dto.DTOConverter;
import br.com.ecommerce.marcel.philippe.dto.ItemDTO;
import br.com.ecommerce.marcel.philippe.modelo.Compra;
import br.com.ecommerce.marcel.philippe.modelo.Item;

public class DTOConverterTest {

    private static final float TOTAL_COMPRA = 49.98F;
	private static final String IDENTIFICADOR_USUARIO = "marcel";
	private static final float ITEM_PRECO = 19.99F;
	private static final String IDENTIFICADOR_PRODUTO = "12345";

	@Test
    public void testConvertItem() {
    	
        Item item = new Item();
        item.setProductIdentifier(IDENTIFICADOR_PRODUTO);
        item.setPrice(19.99F);

        ItemDTO itemDTO = DTOConverter.convert(item);

        assertEquals(IDENTIFICADOR_PRODUTO, itemDTO.getProductIdentifier());
        assertEquals(ITEM_PRECO, itemDTO.getPrice());
    }

    @Test
    public void testConvertCompra() {
        Item item1 = new Item();
        item1.setProductIdentifier(IDENTIFICADOR_PRODUTO);
        item1.setPrice(ITEM_PRECO);

        Item item2 = new Item();
        item2.setProductIdentifier(IDENTIFICADOR_PRODUTO);
        item2.setPrice(ITEM_PRECO);

        List<Item> items = Arrays.asList(item1, item2);

        Compra compra = new Compra();
        compra.setUserIdentifier(IDENTIFICADOR_USUARIO);
        compra.setTotal(TOTAL_COMPRA);
        compra.setDate(LocalDateTime.now());
        compra.setItems(items);

        CompraDTO compraDTO = DTOConverter.convert(compra);

        assertEquals(IDENTIFICADOR_USUARIO, compraDTO.getUserIdentifier());
        assertEquals(TOTAL_COMPRA, compraDTO.getTotal());
        assertEquals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), compraDTO.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertEquals(2, compraDTO.getItems().size());
        assertEquals(IDENTIFICADOR_PRODUTO, compraDTO.getItems().get(0).getProductIdentifier());
        assertEquals(19.99F, compraDTO.getItems().get(0).getPrice());
        assertEquals(IDENTIFICADOR_PRODUTO, compraDTO.getItems().get(1).getProductIdentifier());
        assertEquals(ITEM_PRECO, compraDTO.getItems().get(1).getPrice());
    }
}

