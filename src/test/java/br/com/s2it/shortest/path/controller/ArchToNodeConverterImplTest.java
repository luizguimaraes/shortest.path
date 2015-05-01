package br.com.s2it.shortest.path.controller;

import static java.util.Collections.singleton;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.com.s2it.shortest.path.algorithm.Node;
import br.com.s2it.shortest.path.model.Arch;

public class ArchToNodeConverterImplTest {

	private ArchToNodeConverterImpl archToNodeConverterImpl;

	@Before
	public void beforeTest() {
		archToNodeConverterImpl = new ArchToNodeConverterImpl();
	}

	@Test
	public void convertOneArchTest() {
		final Arch arch = new Arch("A", "B", 1,
				new br.com.s2it.shortest.path.model.Map());
		final Map<String, Node> nodesByName = archToNodeConverterImpl
				.convert(singleton(arch));

		assertEquals("A", nodesByName.get("A").getName());
		assertEquals("B", nodesByName.get("B").getName());
		assertEquals(1, nodesByName.get("A").getNeighbours().size());
		assertEquals("B", nodesByName.get("A").getNeighbours().iterator()
				.next().getName());
	}
}
