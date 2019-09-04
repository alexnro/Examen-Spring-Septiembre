package org.formacio;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.formacio.domain.Animal;
import org.formacio.domain.Municipi;
import org.formacio.domain.Persona;
import org.formacio.repositori.PersonaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(statements= {
		"delete from t_animals","delete from t_cases","delete from t_persones","delete from t_municipis",
		"insert into t_municipis (mun_nom) values ('Selva')",
        "insert into t_persones (per_id, per_nom) values (10, 'Antonia')",
        "insert into t_animals (ani_nom, ani_propietari) values ('rambo',10)",
        "insert into t_cases (cas_adreca, cas_municipi, cas_propietari) values ('carrer gran','Selva',10)"
     })
public class JpaBasicApplicationTests {
	

	@PersistenceContext 
	private EntityManager em;
	
	@Autowired(required=false)
	private PersonaRepository repositori;

    @Autowired(required=false)
    private MockMvc mockMvc;

    /**
	 * Mapeija les entitats del pacakage org.formacio.domain
	 * Nomes has d'afegir les anotacions que falten. No has d'afegir cap propietat.
	 * 
	 * T'has de basar en l'esquema a src/main/resources/schema.sql
	 */
	@Test
	public void test_mapping() { 
		
		Assert.assertNotNull("No troba municipi", em.find(Municipi.class, "Selva"));
		Assert.assertNotNull("No troba animal", em.find(Animal.class	, "rambo"));
		Assert.assertNotNull("No troba persona", em.find(Persona.class, 10L));
	}	

	
	/**
	 * Canvieu el mapping de Persona per a que quan es carregui una persona
	 * automaticament es carreguin tambe els seus animals
	 */
	@Test
	public void test_eager_mapping() {
		Assert.assertEquals(1, em.find(Persona.class, 10L).getMascotes().size());
	}
	
	/**
	 * Modifica PersonaRepository per a que sigui un repositori Spring Data
	 */
	@Test
	public void test_repositori_spring_data() {
		Assert.assertNotNull(repositori);
	}
	
	/**
	 * Creau una consulta al repositori que carregui una persona a partir de la
	 * seva clau primaria carregant tambe les seves propietats
	 *   ----> heu de modificar el codi del test<----
	 */
	@Test
	public void test_consulta_amb_precarrega() {
		// Canvia l'assignacio a null per la invocacio al metode del repositori
		// que hageu creat passant com a parametre 10L
		// Per exemple Persona antonia = repositori.carrega(10L);
		Persona antonia = null;
		Assert.assertNotNull("No deixis a la pobre antonia a null", antonia);
		Assert.assertEquals(1, antonia.getPropietats().size());
	}
	
	/**
	 * Fes que PersonaRepository se publiqui via Spring Data Rest
	 * Configureu el repositori per a que empri la url /persona
	 */
	@Test
	public void test_acces_rest() throws Exception {
	       this.mockMvc.perform(get("/persona/10")).andExpect(status().isOk());
	}
}
