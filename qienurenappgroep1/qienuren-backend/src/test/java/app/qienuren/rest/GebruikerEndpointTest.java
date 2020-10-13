package app.qienuren.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;


import app.qienuren.controller.GebruikerRepository;
import app.qienuren.model.Gebruiker;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GebruikerEndpointTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	GebruikerRepository gebruikerRepo;

	@Test
	@WithMockUser(roles = "ADMIN")
	public void shouldReturnDefaultMessage() throws Exception {
		Gebruiker gebruiker = new Gebruiker();
		gebruiker.setEmail("belle2@qien.nl");
		gebruiker.setEncryptedPassword("halloalex");
		gebruiker.setUserId("id7");
		gebruikerRepo.save(gebruiker);

		this.mockMvc.perform(get("/api/gebruiker/all/")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].email", is("admin@qien.nl")))
				.andExpect(jsonPath("$.[1].email", is("belle2@qien.nl")));
	}
}
