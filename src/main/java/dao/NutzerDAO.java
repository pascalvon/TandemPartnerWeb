package dao;

import models.Bezirk;
import models.Nutzer;
import models.Sprache;

import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import javax.servlet.annotation.WebServlet;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.Collection;

@Stateless
public class NutzerDAO {

    @PersistenceContext(unitName = "ExperimentalJPADatabase")
    private EntityManager em;
    private UserTransaction userTransaction;

    public NutzerDAO() {
    }

    public Nutzer find(String Mail) {
        return em.createNamedQuery("findByMail", Nutzer.class)
                .setParameter("vn", Mail)
                .getSingleResult();
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

    public ArrayList<Sprache> findAllSprache() {
        return (ArrayList<Sprache>) em.createNamedQuery("findAllSprache", Sprache.class)
                .getResultList();
    }

    public void persist(Nutzer nutzer) {
        em.merge(nutzer);
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