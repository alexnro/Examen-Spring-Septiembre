package org.formacio.repositori;


import org.formacio.domain.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonaRepository extends CrudRepository<Persona, String> {

}
