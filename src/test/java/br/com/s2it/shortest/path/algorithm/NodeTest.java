package br.com.s2it.shortest.path.algorithm;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class NodeTest {

	@Test
	public void markSingleVisitedTest() {
		final Node a = new Node("A");
		a.markAsVisited(null);

		assertEquals(a.getDistanceToReachNode(), 0);
		assertEquals(a.getNodesUntilReach(), emptyList());
	}

	@Test
	public void markTwoNodesVisitedTest() {
		final Node a = new Node("A");
		final Node b = new Node("B");
		a.addNeighbour(b, 1);

		a.markAsVisited(null);
		b.markAsVisited(a);

		assertEquals(a.getDistanceToReachNode(), 0);
		assertEquals(a.getNodesUntilReach(), emptyList());
		assertEquals(b.getDistanceToReachNode(), 1);
		assertEquals(b.getNodesUntilReach(), singletonList(a));
	}

	@Test
	public void markThreeNodesVisitedTest() {
		final Node a = new Node("A");
		final Node b = new Node("B");
		final Node c = new Node("C");
		a.addNeighbour(b, 1);
		b.addNeighbour(c, 2);

		a.markAsVisited(null);
		b.markAsVisited(a);
		c.markAsVisited(b);

		assertEquals(a.getDistanceToReachNode(), 0);
		assertEquals(a.getNodesUntilReach(), emptyList());
		assertEquals(b.getDistanceToReachNode(), 1);
		assertEquals(b.getNodesUntilReach(), singletonList(a));
		assertEquals(c.getDistanceToReachNode(), 3);
		assertEquals(c.getNodesUntilReach(), Arrays.asList(a, b));
	}

	@Test(expected = RuntimeException.class)
	public void markVisitedWithUnvisitedNodeBeforeTest() {
		final Node a = new Node("A");
		final Node b = new Node("B");
		a.addNeighbour(b, 1);

		b.markAsVisited(a);
	}

	@Test(expected = RuntimeException.class)
	public void markVisitedWithNoArchBeforeTest() {
		final Node a = new Node("A");
		final Node b = new Node("B");

		a.markAsVisited(null);
		b.markAsVisited(a);
	}

	@Test
	public void addTwoPathToSameNeighbourTest() {
		final Node a = new Node("A");
		final Node b = new Node("B");
		a.addNeighbour(b, 1);
		a.addNeighbour(b, 2);

		assertEquals(1, a.distanceToNeighbour(b));
	}
}
