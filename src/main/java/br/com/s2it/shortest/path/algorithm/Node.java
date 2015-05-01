package br.com.s2it.shortest.path.algorithm;

import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.filter;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.google.common.base.Predicate;

/**
 * Classe que representa um nó do grafo. Possui informações estáticas como nome
 * e os vizinhos alcançáveis além de propriedades dinâmicas que são preenchidas
 * durante a execução do algoritmo de menor caminho.
 * */
public class Node {
	// Propriedades da classe
	private final String name;

	/**
	 * Mapa que possui como chave os vizinhos alcançáveis a partir deste nó e
	 * possui a distância até eles como valor.
	 * */
	private final Map<Node, Integer> neighours = newHashMap();

	// Propriedades utilizadas no algoritmo de menor caminnho
	/**
	 * Lista com os nós que formam o menor caminho para chegar do nó de origem à
	 * este nó.
	 * */
	private final List<Node> nodesUntilReach = newArrayList();
	private boolean visited = false;
	/**
	 * Distância para chegar do nó de origem até este nó
	 * */
	private int distanceToReachNode = -1;

	public Node(@NotNull final String name) {
		checkArgument(name != null && !name.isEmpty(),
				"name cannot be null nor empty");
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getDistanceToReachNode() {
		return distanceToReachNode;
	}

	public boolean isVisited() {
		return visited;
	}

	public Set<Node> getNeighbours() {
		return unmodifiableSet(neighours.keySet());
	}

	/**
	 * Retorna todos os nós vizinhos que não foram visitados durante a exucução
	 * atual do algoritmo.
	 * */
	public Set<Node> getNotVisitedNeighbours() {
		return filter(getNeighbours(), new Predicate<Node>() {

			@Override
			public boolean apply(final Node input) {
				return !input.visited;
			}
		});
	}

	public List<Node> getNodesUntilReach() {
		return unmodifiableList(nodesUntilReach);
	}

	/**
	 * Retorna a distância entre o nó atual e o nó passado como parâmetro.
	 * 
	 * @param node
	 *            nó para o qual será calculada a distância.
	 * @throws NullPointerException
	 *             caso node sejá null.
	 * */
	public int distanceToNeighbour(@NotNull final Node node) {
		checkNotNull(node, "node cannot be null");

		final Integer distance = neighours.get(node);
		if (distance == null) {
			return -1;
		}
		return distance;
	}

	/**
	 * Marca o nó atual como visitado, calculando a distância da origem e quais
	 * nós foram percorridos.
	 * 
	 * @param node
	 *            nó para o qual será calculada a distância.
	 * */
	public void markAsVisited(final Node nodeBefore) {

		this.visited = true;
		if (nodeBefore == null) {
			distanceToReachNode = 0;
		} else {
			checkArgument(nodeBefore.isVisited(), "node before must be visited");
			checkArgument(nodeBefore.distanceToNeighbour(this) > 0,
					"distanceToNeighbour must be greater than 0");
			this.distanceToReachNode = nodeBefore.distanceToNeighbour(this)
					+ nodeBefore.getDistanceToReachNode();
			this.nodesUntilReach.addAll(nodeBefore.nodesUntilReach);
			this.nodesUntilReach.add(nodeBefore);
		}
	}

	public void addNeighbour(@NotNull final Node node, final int distance) {
		checkNotNull(node, "node cannot be null");
		checkArgument(!node.equals(this), "node cannot be equal to this");
		checkArgument(distance > 0, "distance must be greater than 0");

		// Armazena apenas o arco com menor distância
		if (firstNonNull(neighours.get(node), Integer.MAX_VALUE) > distance) {
			neighours.put(node, distance);
		}
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof Node && ((Node) obj).name.equals(name);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("name", name).add("visited", visited)
				.add("distanceToReachNode", distanceToReachNode)
				.add("nodesUntilReach", nodesUntilReach).toString();
	}
}
