package br.com.s2it.shortest.path.controller;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.s2it.shortest.path.algorithm.Node;
import br.com.s2it.shortest.path.algorithm.ShortestPathAlgorithm;
import br.com.s2it.shortest.path.exception.DestinationNotFoundException;
import br.com.s2it.shortest.path.exception.NegativeAutonomyException;
import br.com.s2it.shortest.path.exception.NegativeCostOfLitreException;
import br.com.s2it.shortest.path.exception.OriginEqualsDestinationException;
import br.com.s2it.shortest.path.exception.OriginNotFoundException;
import br.com.s2it.shortest.path.exception.PathNotFoundException;
import br.com.s2it.shortest.path.model.ArchRepository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;

/**
 * Controller Rest que retorna o menor caminho e o custo associado, da seguinte
 * maneira:<br>
 * - Busca a representação do grafo inteiro atráves do arcos<br>
 * - Converte o conjunto de arcos para uma representação do grafo baseada em nós<br>
 * - Executa o algoritmo de menor caminho - Calcula o custo do menor caminho
 * 
 * @see ShortestPathResponse
 * */
@RestController
public class ShortestPathController {

	/**
	 * Resposta o serviço Rest com o caminho percorrido e o custo total
	 * */
	@VisibleForTesting
	static class ShortestPathResponse implements Serializable {

		private static final long serialVersionUID = -3453311200138853773L;

		private final List<String> path;
		private final double cost;

		private ShortestPathResponse(List<String> path, double cost) {
			this.path = checkNotNull(path, "path cannot be null");
			this.cost = cost;
		}

		public List<String> getPath() {
			return Collections.unmodifiableList(path);
		}

		public double getCost() {
			return cost;
		}
	}

	@Autowired
	private ShortestPathAlgorithm shortestPathAlgorithm;

	@Autowired
	private ArchRepository archRepository;

	@Autowired
	private ArchToNodeConverter archToNodeConverter;

	@RequestMapping("/shortestPath")
	public ShortestPathResponse shortestPath(@RequestParam final String origin,
			@RequestParam final String destination,
			@RequestParam final double autonomy,
			@RequestParam final double costOfLitre)
			throws NegativeAutonomyException, NegativeCostOfLitreException,
			OriginNotFoundException, DestinationNotFoundException,
			PathNotFoundException {
		if (origin.equals(destination)) {
			throw new OriginEqualsDestinationException();
		}
		if (autonomy <= 0) {
			throw new NegativeAutonomyException();
		}
		if (costOfLitre <= 0) {
			throw new NegativeCostOfLitreException();
		}

		final Map<String, Node> nodes = archToNodeConverter
				.convert(archRepository.findAll());
		final Node originNode = nodes.get(origin);
		if (originNode == null) {
			throw new OriginNotFoundException();
		}

		final Node destinationNode = nodes.get(destination);
		if (destinationNode == null) {
			throw new DestinationNotFoundException();
		}

		return buildResponse(autonomy, costOfLitre, destinationNode,
				shortestPathAlgorithm.shortestPath(originNode, destinationNode));
	}

	private ShortestPathResponse buildResponse(final double autonomy,
			final double costOfLitre, final Node destinationNode,
			final int distance) {
		final List<Node> nodePath = newArrayList(destinationNode
				.getNodesUntilReach());
		nodePath.add(destinationNode);

		final List<String> stringPath = copyOf(transform(nodePath,
				new Function<Node, String>() {

					@Override
					public String apply(Node input) {
						return input.getName();
					}
				}));
		return new ShortestPathResponse(stringPath, ((double) distance)
				/ autonomy * costOfLitre);
	}
}
