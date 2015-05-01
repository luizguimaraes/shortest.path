package br.com.s2it.shortest.path.controller;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.s2it.shortest.path.algorithm.Node;
import br.com.s2it.shortest.path.model.Arch;

/**
 * Implementação padrão de {@link ArchToNodeConverter}.
 * */
@Component
class ArchToNodeConverterImpl implements ArchToNodeConverter {

	public Map<String, Node> convert(final Iterable<Arch> arches) {
		final Map<String, Node> nodesByName = newHashMap();
		for (final Arch arch : arches) {
			final Node begin = getNode(nodesByName, arch.getBegin());
			final Node end = getNode(nodesByName, arch.getEnd());
			begin.addNeighbour(end, arch.getDistance());
		}
		return nodesByName;
	}

	private Node getNode(final Map<String, Node> nodesByName, final String name) {
		Node node = nodesByName.get(name);
		if (node == null) {
			node = new Node(name);
			nodesByName.put(name, node);
		}
		return node;
	}
}
