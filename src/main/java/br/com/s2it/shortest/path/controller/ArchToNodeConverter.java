package br.com.s2it.shortest.path.controller;

import java.util.Map;

import javax.validation.constraints.NotNull;

import br.com.s2it.shortest.path.algorithm.Node;
import br.com.s2it.shortest.path.model.Arch;

/**
 * Interface responsável por converter a representação do grafo por arcos para
 * uma representação por nós.
 * */
interface ArchToNodeConverter {
	Map<String, Node> convert(@NotNull Iterable<Arch> arches);
}
