package org.formacio.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "t_persones")
@SequenceGenerator(name = "sequencia_persones", sequenceName = "seq_persona", initialValue = 1, allocationSize = 50)
public class Persona {

	/*
	 * Persona ha d'emprar la sequencia SEQ_PERSONA per els ids
	 * 
	 * Es necessari modificar el fitxer schema.sql per a esborrar 
	 * generated by default
	 * A mes, hem de refrescar el projecte!
	 */


 	@Id	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequencia_persones")
	@Column(name = "per_id")
	private Long id;

	@Column(name = "per_nom")
	private String nom;

	@OneToMany
	@JoinColumn(name = "cas_propietari")
	private Set<Casa> propietats = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "ani_propietari")
	private Set<Animal> mascotes = new HashSet<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Set<Casa> getPropietats() {
		return propietats;
	}
	public void setPropietats(Set<Casa> propietats) {
		this.propietats = propietats;
	}
	public Set<Animal> getMascotes() {
		return mascotes;
	}
	public void setMascotes(Set<Animal> mascotes) {
		this.mascotes = mascotes;
	}

	
}
