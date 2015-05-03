package br.com.s2it.shortest.path.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Repositório JPA da classe Arch.
 * */
@RestResource(exported = false)
public interface ArchRepository extends JpaRepository<Arch, Long> {

}
