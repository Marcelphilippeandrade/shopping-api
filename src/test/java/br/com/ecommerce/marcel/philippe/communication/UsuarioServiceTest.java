package br.com.ecommerce.marcel.philippe.communication;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import br.com.ecommerce.marcel.philippe.exception.UsuarioNotFoundException;

class UsuarioServiceTest {

	@InjectMocks
	private UsuarioService usuarioService;

	@Mock
	private RestTemplate restTemplate;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void deveRetornarUmUsuarioApartirDeUmCpf() {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		String cpf = "06618938635";
		usuarioDTO.setCpf(cpf);
		ResponseEntity<Object> responseEntity = ResponseEntity.ok(usuarioDTO);

		when(restTemplate.getForEntity(anyString(), any())).thenReturn(responseEntity);
		UsuarioDTO result = usuarioService.getUserByCpf(cpf);

		assertEquals(cpf, result.getCpf());
	}

	@Test
	public void deveRetornarUmaExcecaoCasoOUsuarioNaoEstejaCadastrado() {
		when(restTemplate.getForEntity(anyString(), eq(UsuarioDTO.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		assertThrows(UsuarioNotFoundException.class, () -> {
			usuarioService.getUserByCpf("Usuario inexistente");
		});
	}
}