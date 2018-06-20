package dao;

import models.Nutzer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DAOTest extends JpaBaseRolledBackTestCase{

    private static DAO dao;

    @Test
    void daoTest() {
        dao = new DAO(em);
        Nutzer nutzer = dao.findNutzerByMail("c.cetinkaya@live.de");
        assertEquals("Coskun", nutzer.getVorname());
    }

}