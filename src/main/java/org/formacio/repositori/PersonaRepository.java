package org.formacio.repositori;


import org.formacio.domain.Persona;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "persones", path = "persona")
public interface PersonaRepository extends CrudRepository<Persona, Long> {

}
