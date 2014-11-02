package br.com.angeliski.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.angeliski.dao.livro.LivroDAOTest;

@RunWith(Suite.class)
@SuiteClasses({ HomeControllerTest.class, LivroRepositoryTest.class, PersistenceTest.class, LivroDAOTest.class })
public class AllTests {

}
