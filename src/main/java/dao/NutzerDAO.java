package dao;

import models.*;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

// TODO Joe: 14.05.2018 ALle unnoetigen Methoden am Ende loeschen und verbleibende sortieren
// TODO Joe: 14.05.2018 Ueberpruefen ob DAO's erstellt werden sollten
@Stateless
public class NutzerDAO {

    @PersistenceContext(unitName = "ExperimentalJPADatabase")
    private EntityManager em;
    private UserTransaction userTransaction;

    public NutzerDAO() {
    }

    // TODO Joe: 17.05.2018 Variable "vn" entsprechend ueberall aendern
    public Nutzer findNutzerByMail(String mail) {
        try {
            return em.createNamedQuery("findNutzerByMail", Nutzer.class)
                    .setParameter("vn", mail)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

//    public Nutzer findNutzerByID(int nutzerID) {
//        return em.createNamedQuery("findNutzerByID", Nutzer.class)
//                .setParameter("vn", nutzerID)
//                .getSingleResult();
//    }

    public ArrayList<Nutzer> findNutzerBySpracheID(int spracheID) {
        return (ArrayList<Nutzer>) em.createNamedQuery("findNutzerBySprachID", Nutzer.class)
                .setParameter("vn", spracheID)
                .getResultList();
    }

    public List<Sprache> findAllSprache() {
        return em.createNamedQuery("findAllSprache", Sprache.class).getResultList();
    }

    public Sprache findSpracheByName(String spracheName) {
        try {
            return em.createNamedQuery("findBySpracheName", Sprache.class)
                    .setParameter("vn", spracheName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Sprache findSpracheByID(String spracheID) {
        return em.createNamedQuery("findBySpracheID", Sprache.class)
                .setParameter("vn", Integer.parseInt(spracheID))
                .getSingleResult();
    }

    public Freizeitaktivitaeten findFreizeitaktivitaetenByID(String freizeitaktivitaetenID) {
        return em.createNamedQuery("findByFreizeitaktivitaetenID", Freizeitaktivitaeten.class)
                .setParameter("vn", Integer.parseInt(freizeitaktivitaetenID))
                .getSingleResult();
    }

    public ArrayList<Nutzer> findAll() {
        TypedQuery<Nutzer> query = em.createNamedQuery("findAllNutzer", Nutzer.class);
        return (ArrayList<Nutzer>)query.getResultList();
    }

    public Nutzer findByVorname(String vorname) {
        return (Nutzer) em.createNamedQuery("findNutzerByVorname")
                .setParameter("vn", vorname)
                .getSingleResult();
    }

    public Bezirk findBezirkByID(int bezirkID) {
        return em.createNamedQuery("findBezirkByID", Bezirk.class)
                .setParameter("vn", bezirkID)
                .getSingleResult();
    }

    public ArrayList<Suchanfrage> findSuchanfrageByNutzerID(Nutzer nutzer) {
        return (ArrayList<Suchanfrage>) em.createNamedQuery("findSuchanfrageByNutzerID", Suchanfrage.class)
                .setParameter("vn", nutzer)
                .getResultList();
    }

    public void persist(Nutzer nutzer) {
        em.merge(nutzer);
    }

    // TODO Joe: 17.05.2018 Wieso nicht void?
    public Nutzer merge(Nutzer nutzer) {
        return em.merge(nutzer);
    }

    public Suchanfrage merge(Suchanfrage suchanfrage) {
        return em.merge(suchanfrage);
    }

    public void deleteSuchanfrage(Suchanfrage suchanfrage) {
        em.remove(em.contains(suchanfrage) ? suchanfrage : em.merge(suchanfrage));
    }

    public void deleteSuchanfrageNQ(Suchanfrage suchanfrage) {
        em.createNamedQuery("deleteSuchanfrage")
                .setParameter("vn", suchanfrage.getSuchId())
                .executeUpdate();
    }

    public Suchanfrage findSuchanfrageByID(Suchanfrage suchanfrage) {
        return em.createNamedQuery("findSuchanfrageByID", Suchanfrage.class)
                .setParameter("vn", suchanfrage.getSuchId())
                .getSingleResult();
    }

    public Bezirk findNutzerByID(int id) {
        return em.find(Bezirk.class, id);
    }

    public ArrayList<Nutzer> findNutzerByFreizeitaktivitaetenID(Freizeitaktivitaeten freizeitaktivitaeten) {
        return (ArrayList<Nutzer>) em.createNamedQuery("findNutzerByFreizeitaktivitaetenID", Nutzer.class)
                .setParameter("vn", freizeitaktivitaeten.getId())
                .getResultList();
    }

    public ArrayList<Nutzer> findNutzerBySuchergebnis(int freizeitaktivitaetID, int spracheID) {
        return (ArrayList<Nutzer>) em.createNamedQuery("findNutzerBySuchergebnis", Nutzer.class)
                .setParameter("fa", freizeitaktivitaetID)
                .setParameter("sp", spracheID)
                .getResultList();
    }

    public void merge(Matchanfragen matchanfrage) {
        em.merge(matchanfrage);
    }
}