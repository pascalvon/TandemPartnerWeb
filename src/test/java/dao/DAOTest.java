package dao;

import models.Nutzer;
import org.junit.jupiter.api.Test;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ManagedBean
class DAOTest {

    @EJB
    private DAO dao;

    @Test
    void daoTest() {
        EntityManager em = dao.getEm();
        em.getTransaction().begin();
        Nutzer nutzer = em.find(Nutzer.class, 1241);

        //Nutzer nutzer = dao.findNutzerByMail("c.cetinkaya@live.de");
        assertEquals("Coskun", nutzer.getVorname());
    }

}