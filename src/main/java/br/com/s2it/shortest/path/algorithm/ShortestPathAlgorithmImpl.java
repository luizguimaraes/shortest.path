package br.com.s2it.shortest.path.algorithm;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import br.com.s2it.shortest.path.exception.PathNotFoundException;

/**
 * Implementação baseada no algoritmo de Dijkstra's para menor caminho.
 * 
 * @see Dijkstra, E. W. (1959).
 *      "A note on two problems in connexion with graphs" (PDF). Numerische
 *      Mathematik 1: 269–271. doi:10.1007/BF01386390.
 * */
@Component
class ShortestPathAlgorithmImpl implements ShortestPathAlgorithm {

	public int shortestPath(@NotNull final Node origin,
			@NotNull final Node destination) {
		checkNotNull(origin, "origin cannot be null");
		checkNotNull(destination, "destination cannot be null");
		checkArgument(!origin.equals(destination),
				"origin cannot be equal to destination");

		final Set<Node> nodesToVisit = newHashSet();
		nodesToVisit.add(origin);
		origin.markAsVisited(null);

		// Enquanto existem nós a serem visitados que possuem possíveis
		// vizinhos.
		while (!nodesToVisit.isEmpty()) {
			Integer shortestDistance = null;
			Node shortestDistanceNode = null;
			Node nodeBefore = null;

			// Nós que não possuem mais nenhum vizinho devem ser removidos do
			// conjunto dde nós a serem visitados, pois não oferecem nenhuma
			// solução a mais
			final Set<Node> nodesToRemove = newHashSet();

			for (final Node nodeToVisit : nodesToVisit) {

				if (nodeToVisit.getNotVisitedNeighbours().isEmpty()) {
					nodesToRemove.add(nodeToVisit);
				} else {
					for (final Node neighbour : nodeToVisit
							.getNotVisitedNeighbours()) {
						checkState(
								nodeToVisit.distanceToNeighbour(neighbour) > 0,
								"neighbour " + neighbour.getName()
										+ " must be reacheable from "
										+ nodeToVisit.getName());
						// Calcula a distância para se chegar da origem até este
						// vizinho.
						final int distance = nodeToVisit
								.getDistanceToReachNode()
								+ nodeToVisit.distanceToNeighbour(neighbour);

						// Verifica se este movimento é a menor distância
						// encontrada nesta iteração
						if (shortestDistance == null
								|| distance < shortestDistance) {
							shortestDistance = distance;
							shortestDistanceNode = neighbour;
							nodeBefore = nodeToVisit;
						}
					}
				}
			}

			// atualiza o critério de parada
			nodesToVisit.removeAll(nodesToRemove);

			if (shortestDistanceNode != null) {
				shortestDistanceNode.markAsVisited(nodeBefore);

				// caso o nó escolhido seja o destino a busca concluiu com êxito
				if (shortestDistanceNode.equals(destination)) {
					return shortestDistance;
				}

				nodesToVisit.add(shortestDistanceNode);
			}
		}

		// Não há nenhum caminho possível entre o destino e a origem
		throw new PathNotFoundException();
	}
}
