package dao;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class JpaBaseRolledBackTestCase {
    protected static EntityManagerFactory emf;

    protected EntityManager em;

    @BeforeAll
    public static void createEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("ExperimentalJPADatabase");
    }

    @AfterAll
    public static void closeEntityManagerFactory() {
        emf.close();
    }

    @BeforeEach
    public void beginTransaction() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @AfterEach
    public void rollbackTransaction() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }

        if (em.isOpen()) {
            em.close();
        }
    }

    @Test
    void daoTest() {

    }
}
