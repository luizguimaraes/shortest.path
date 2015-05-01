package br.com.s2it.shortest.path.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repositório JPA da classe Map. Está exposto como um serviço REST, aceitando
 * requisições no formato JSON.
 * */
@RepositoryRestResource(collectionResourceRel = "maps", path = "/map")
interface MapRepository extends JpaRepository<Map, Long> {

}
