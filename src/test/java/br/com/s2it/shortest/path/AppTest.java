package br.com.s2it.shortest.path;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.s2it.shortest.path.algorithm.NodeTest;
import br.com.s2it.shortest.path.algorithm.ShortestPathAlgoritmImplTest;
import br.com.s2it.shortest.path.controller.ArchToNodeConverterImplTest;
import br.com.s2it.shortest.path.controller.ShortestPathControllerTest;

@RunWith(Suite.class)
@SuiteClasses({ NodeTest.class, ShortestPathAlgoritmImplTest.class,
		ArchToNodeConverterImplTest.class, ShortestPathControllerTest.class })
public class AppTest {

}
