package jsfbeans;

import dao.DAO;
import models.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * JUnit 4, weil es Arquillian besser unterst&uuml;zt.
 */
@RunWith(Arquillian.class)
public class LoginManagedBeanTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "joe-test.war")
                .addPackage(DAO.class.getPackage())
                .addPackage(LoginManagedBean.class.getPackage())
                .addPackage(Nutzer.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private DAO dao;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void loginTest() {
        Nutzer nutzer = dao.findNutzerByMail("c.cetinkaya@live.de");

        LoginManagedBean loginManagedBean = new LoginManagedBean(dao);

//        when(dao.findNutzerByMail("c.cetinkaya@live.de")).thenReturn(dao.findNutzerByMail("c.cetinkaya@live.de"));
        loginManagedBean.setMail("c.cetinkaya@live.de");
        loginManagedBean.setPassword("test1234");
        loginManagedBean.login();

        assertEquals("Coskun", loginManagedBean.getNutzer().getVorname());
        assertEquals("Cetinkaya", loginManagedBean.getNutzer().getNachname());
    }
}
