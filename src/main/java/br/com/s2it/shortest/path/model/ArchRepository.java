package br.com.s2it.shortest.path.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.s2it.shortest.path.model.Arch.ArchId;

/**
 * Repositório JPA da classe Arch. Está exposto como um serviço REST, aceitando
 * requisições no formato JSON.
 * */
@RepositoryRestResource(collectionResourceRel = "arches", path = "/arch")
public interface ArchRepository extends JpaRepository<Arch, ArchId> {

}
