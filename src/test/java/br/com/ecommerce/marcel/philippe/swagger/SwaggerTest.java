package br.com.ecommerce.marcel.philippe.swagger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SwaggerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void deveRetornarPaginaUiDoSwagger() throws Exception {
		mockMvc.perform(get("/shopping/swagger-ui/index.html")).andExpect(status().isOk());
	}

	@Test
	public void deveRetornarOpenApiDocs() throws Exception {
		mockMvc.perform(get("/shopping/v3/api-docs")).andExpect(status().isOk())
				.andExpect(jsonPath("$.paths['/shopping']").exists())
				.andExpect(jsonPath("$.paths['/shopping'].get.responses['200'].description").exists())
				.andExpect(jsonPath("$.paths['/shopping/shopByUser/{userIdentifier}'].get.responses['200'].description").exists())
				.andExpect(jsonPath("$.paths['/shopping/shopByDate'].get.responses['200'].description").exists())
				.andExpect(jsonPath("$.paths['/shopping'].post.responses['201'].description").exists())
				.andExpect(jsonPath("$.paths['/shopping/search'].get.responses['200'].description").exists())
				.andExpect(jsonPath("$.paths['/shopping/report'].get.responses['200'].description").exists());
	}
}
