package br.com.s2it.shortest.path.algorithm;

import javax.validation.constraints.NotNull;

import br.com.s2it.shortest.path.exception.PathNotFoundException;

/**
 * Interface que prove a menor distância entre entre dois nós de um grafo.
 * */
public interface ShortestPathAlgorithm {
	int shortestPath(@NotNull Node origin, @NotNull Node destination)
			throws PathNotFoundException;
}
