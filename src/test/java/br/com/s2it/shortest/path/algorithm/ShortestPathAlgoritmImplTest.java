package br.com.s2it.shortest.path.algorithm;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import br.com.s2it.shortest.path.exception.PathNotFoundException;

public class ShortestPathAlgoritmImplTest {

	private ShortestPathAlgorithmImpl shortestPathAlgorithmImpl;

	@Before
	public void beforeTest() {
		shortestPathAlgorithmImpl = new ShortestPathAlgorithmImpl();
	}

	@Test
	public void singleArchTest() {
		final Node a = new Node("A");
		final Node b = new Node("B");
		a.addNeighbour(b, 1);

		assertEquals(1, shortestPathAlgorithmImpl.shortestPath(a, b));
		assertEquals(1, b.getDistanceToReachNode());
		assertEquals(singletonList(a), b.getNodesUntilReach());
	}

	@Test
	public void twoArchTest() {
		final Node a = new Node("A");
		final Node b = new Node("B");
		final Node c = new Node("C");
		a.addNeighbour(b, 2);
		a.addNeighbour(c, 1);

		assertEquals(2, shortestPathAlgorithmImpl.shortestPath(a, b));
		assertEquals(2, b.getDistanceToReachNode());
		assertEquals(singletonList(a), b.getNodesUntilReach());
	}

	@Test
	public void threeArchTest() {
		final Node a = new Node("A");
		final Node b = new Node("B");
		final Node c = new Node("C");
		a.addNeighbour(b, 10);
		a.addNeighbour(c, 1);
		c.addNeighbour(b, 1);

		assertEquals(2, shortestPathAlgorithmImpl.shortestPath(a, b));
		assertEquals(2, b.getDistanceToReachNode());
		assertEquals(Arrays.asList(a, c), b.getNodesUntilReach());
	}

	@Test(expected = PathNotFoundException.class)
	public void noArchToDestinationTest() {
		final Node a = new Node("A");
		final Node b = new Node("B");
		final Node c = new Node("C");
		a.addNeighbour(c, 1);

		shortestPathAlgorithmImpl.shortestPath(a, b);
	}

	@Test(expected = PathNotFoundException.class)
	public void noArchTest() {
		final Node a = new Node("A");
		final Node b = new Node("B");

		shortestPathAlgorithmImpl.shortestPath(a, b);
	}
}
