package dao;

import models.Bezirk;
import models.Nutzer;
import models.Sprache;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import java.util.Collection;

@Stateless
public class NutzerDAO {

    //private EntityManagerFactory factory;
    @PersistenceContext(unitName = "ExperimentalJPADatabase")
    private EntityManager em;
    private UserTransaction userTransaction;

    public NutzerDAO() {
        //factory = Persistence.createEntityManagerFactory("ExperimentalJPADatabase");
        //em = factory.createEntityManager();
    }
    public void shutdown() {
        //em.close();
        //factory.close();
        //em = null;
        //factory = null;
    }
    public Nutzer find(String Mail) {
        return em.find(Nutzer.class, Mail);
    }

    public Collection<Nutzer> findAll() {
        TypedQuery<Nutzer> query = em.createNamedQuery("findAll", Nutzer.class);
        Collection<Nutzer> collection = query.getResultList();
        return collection;
    }

    public Nutzer findByVorname(String vorname) {
        Nutzer nutzer = (Nutzer) em.createNamedQuery("findByVorname")
                .setParameter("vn", vorname)
                .getSingleResult();
        return nutzer;
    }

    public void persist(Nutzer nutzer) {
        //em.getTransaction().begin();
        em.persist(nutzer);
        //em.getTransaction().commit();
    }

    public Nutzer merge(Nutzer nutzer) {
        //em.detach(nutzer);
        return em.merge(nutzer);
    }

    public void delete(String Mail) {
        em.getTransaction().begin();
        Nutzer nutzer = em.getReference(Nutzer.class, Mail);
        em.remove(nutzer);
        em.getTransaction().commit();
    }

    public void persist(Bezirk bezirk) {
        em.getTransaction().begin();
        em.persist(bezirk);
        em.getTransaction().commit();
    }

    public void persist(Sprache sprache) {
        em.getTransaction().begin();
        em.persist(sprache);
        em.getTransaction().commit();
    }

    public Bezirk find(int id) {
        return em.find(Bezirk.class, id);
    }
}