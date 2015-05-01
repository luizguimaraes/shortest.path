package br.com.s2it.shortest.path.controller;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;

import br.com.s2it.shortest.path.algorithm.Node;
import br.com.s2it.shortest.path.algorithm.ShortestPathAlgorithm;
import br.com.s2it.shortest.path.controller.ShortestPathController.ShortestPathResponse;
import br.com.s2it.shortest.path.exception.DestinationNotFoundException;
import br.com.s2it.shortest.path.exception.NegativeAutonomyException;
import br.com.s2it.shortest.path.exception.NegativeCostOfLitreException;
import br.com.s2it.shortest.path.exception.OriginEqualsDestinationException;
import br.com.s2it.shortest.path.exception.OriginNotFoundException;
import br.com.s2it.shortest.path.exception.PathNotFoundException;
import br.com.s2it.shortest.path.model.Arch;
import br.com.s2it.shortest.path.model.ArchRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = ShortestPathControllerTest.class)
public class ShortestPathControllerTest {

	@InjectMocks
	private ShortestPathController shortestPathController;

	@Mock
	private ArchToNodeConverter archToNodeConverter;

	@Mock
	private ArchRepository archRepository;

	@Mock
	private ShortestPathAlgorithm shortestPathAlgorithm;

	@Test(expected = OriginEqualsDestinationException.class)
	public void OriginEqualsDestinationTest() {
		shortestPathController.shortestPath("A", "A", 1, 1);
	}

	@Test(expected = NegativeAutonomyException.class)
	public void negativeAutonomyTest() {
		shortestPathController.shortestPath("A", "B", -1, 1);
	}

	@Test(expected = NegativeCostOfLitreException.class)
	public void negativeCostOfLitreTest() {
		shortestPathController.shortestPath("A", "B", 1, -1);
	}

	@Test(expected = OriginNotFoundException.class)
	public void originNotFoundTest() {
		when(archRepository.findAll()).thenReturn(
				Collections.<Arch> emptyList());
		shortestPathController.shortestPath("A", "B", 1, 1);
	}

	@Test(expected = DestinationNotFoundException.class)
	public void destinationNotFoundTest() {
		when(archRepository.findAll()).thenReturn(
				Collections.<Arch> emptyList());
		when(archToNodeConverter.convert(Collections.<Arch> emptyList()))
				.thenReturn(singletonMap("A", mock(Node.class)));
		shortestPathController.shortestPath("A", "B", 1, 1);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = PathNotFoundException.class)
	public void pathNotFoundTest() {
		when(archRepository.findAll()).thenReturn(
				Collections.<Arch> emptyList());
		final Map<String, Node> map = newHashMap();
		final Node mock = mock(Node.class);
		map.put("A", mock);
		map.put("B", mock);

		when(archToNodeConverter.convert(Collections.<Arch> emptyList()))
				.thenReturn(map);
		when(shortestPathAlgorithm.shortestPath(mock, mock)).thenThrow(
				PathNotFoundException.class);
		shortestPathController.shortestPath("A", "B", 1, 1);
	}

	@Test
	public void sucessTest() {
		when(archRepository.findAll()).thenReturn(
				Collections.<Arch> emptyList());
		final Map<String, Node> map = newHashMap();
		final Node mockA = mock(Node.class);
		final Node mockB = mock(Node.class);
		when(mockA.getName()).thenReturn("A");
		when(mockB.getName()).thenReturn("B");
		when(mockB.getNodesUntilReach()).thenReturn(
				Collections.singletonList(mockA));
		map.put("A", mockA);
		map.put("B", mockB);

		when(archToNodeConverter.convert(Collections.<Arch> emptyList()))
				.thenReturn(map);
		when(shortestPathAlgorithm.shortestPath(mockA, mockB)).thenReturn(1);
		final ShortestPathResponse response = shortestPathController
				.shortestPath("A", "B", 1, 1);

		assertEquals(1.0, response.getCost(), 0.0001);
		assertEquals(asList("A", "B"), response.getPath());
	}
}
