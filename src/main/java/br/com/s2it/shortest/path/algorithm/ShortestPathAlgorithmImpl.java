package br.com.s2it.shortest.path.algorithm;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Sets.newTreeSet;

import java.util.TreeSet;

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

	private static class VisitedArch implements Comparable<VisitedArch> {

		private final Node begin;
		private final Node end;
		private final int distance;

		private VisitedArch(final Node begin, final Node end) {
			this.begin = begin;
			this.end = end;
			this.distance = begin.distanceToNeighbour(end);
		}

		private Node getBegin() {
			return begin;
		}

		private Node getEnd() {
			return end;
		}

		@Override
		public int compareTo(final VisitedArch o) {
			if (distance != o.distance) {
				return distance - o.distance;
			}
			if (!begin.getName().equals(o.begin.getName())) {
				return begin.getName().compareTo(o.begin.getName());
			}
			return end.getName().compareTo(o.end.getName());
		}
	}

	public int shortestPath(@NotNull final Node origin,
			@NotNull final Node destination) {
		checkNotNull(origin, "origin cannot be null");
		checkNotNull(destination, "destination cannot be null");
		checkArgument(!origin.equals(destination),
				"origin cannot be equal to destination");

		final TreeSet<VisitedArch> visitedArches = newTreeSet();
		origin.markAsVisited(null);

		Node nodeToVisit = origin;

		// Enquanto existem nós a serem visitados
		while (nodeToVisit != null) {

			for (final Node neighbour : nodeToVisit.getNotVisitedNeighbours()) {
				checkState(
						nodeToVisit.distanceToNeighbour(neighbour) > 0,
						"neighbour " + neighbour.getName()
								+ " must be reacheable from "
								+ nodeToVisit.getName());
				// Calcula a distância para se chegar da origem até este
				// vizinho.
				visitedArches.add(new VisitedArch(nodeToVisit, neighbour));
			}

			while (!visitedArches.isEmpty()
					&& visitedArches.first().getEnd().isVisited()) {
				visitedArches.pollFirst();
			}

			if (!visitedArches.isEmpty()) {
				final VisitedArch shortestArch = visitedArches.pollFirst();
				shortestArch.getEnd().markAsVisited(shortestArch.getBegin());

				// caso o nó escolhido seja o destino a busca concluiu com êxito
				if (shortestArch.getEnd().equals(destination)) {
					return shortestArch.getEnd().getDistanceToReachNode();
				}

				nodeToVisit = shortestArch.getEnd();
			} else {
				nodeToVisit = null;
			}
		}

		// Não há nenhum caminho possível entre o destino e a origem
		throw new PathNotFoundException();
	}
}
