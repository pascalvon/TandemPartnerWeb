package dao;

import models.Bezirk;
import models.Nutzer;
import models.Sprache;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Stateless
public class NutzerDAO {

    @PersistenceContext(unitName = "ExperimentalJPADatabase")
    private EntityManager em;
    private UserTransaction userTransaction;

    public NutzerDAO() {
    }

    public Nutzer find(String mail) {
        try {
            return em.createNamedQuery("findByMail", Nutzer.class)
                    .setParameter("vn", mail)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Sprache> findAllSprache() {
        return em.createNamedQuery("findAllSprache", Sprache.class).getResultList();
    }

    public Sprache findSprache(String spracheName) {
        try {
            return em.createNamedQuery("findBySpracheName", Sprache.class)
                    .setParameter("vn", spracheName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ArrayList<Nutzer> findAll() {
        TypedQuery<Nutzer> query = em.createNamedQuery("findAll", Nutzer.class);
        return (ArrayList<Nutzer>)query.getResultList();
    }

    public Nutzer findByVorname(String vorname) {
        return (Nutzer) em.createNamedQuery("findByVorname")
                .setParameter("vn", vorname)
                .getSingleResult();
    }

    public void persist(Nutzer nutzer) {
        em.persist(nutzer);
    }

    public Nutzer merge(Nutzer nutzer) {
        return em.merge(nutzer);
    }

    public void delete(String Mail) {
        em.getTransaction().begin();
        Nutzer nutzer = em.getReference(Nutzer.class, Mail);
        em.remove(nutzer);
        em.getTransaction().commit();
    }

    public Bezirk find(int id) {
        return em.find(Bezirk.class, id);
    }
}