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

    // TODO Joe: 27.05.2018 Kann weg?
    public NutzerDAO() {
    }

    // TODO Joe: 17.05.2018 Variable "vn" entsprechend ueberall aendern
    // TODO Joe: 25.05.2018 try catch durch validator ersetzen
    public Nutzer findNutzerByMail(String mail) {
        try {
            return em.createNamedQuery("findNutzerByMail", Nutzer.class)
                    .setParameter("vn", mail)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ArrayList<Nutzer> findNutzerBySpracheID(int spracheID) {
        return (ArrayList<Nutzer>) em.createNamedQuery("findNutzerBySprachID", Nutzer.class)
                .setParameter("vn", spracheID)
                .getResultList();
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
        em.persist(nutzer);
    }

    public void merge(Nutzer nutzer) {
        em.merge(nutzer);
    }

    public void merge(Suchanfrage suchanfrage) {
        em.merge(suchanfrage);
    }

    public void deleteSuchanfrageNQ(Suchanfrage suchanfrage) {
        em.createNamedQuery("deleteSuchanfrage")
                .setParameter("vn", suchanfrage.getSuchId())
                .executeUpdate();
    }

    public void merge(Matchanfragen matchanfrage) {
        em.merge(matchanfrage);
    }

    public ArrayList<Matchanfragen> findMatchanfragenByMail(String mail) {
        return (ArrayList<Matchanfragen>) em.createNamedQuery("findMatchanfragenByMail", Matchanfragen.class)
                .setParameter("vn", mail)
                .getResultList();
    }

    public ArrayList<Matchanfragen> findMatchanfragenByAllColumns(String mail) {
        return (ArrayList<Matchanfragen>) em.createNamedQuery("findMatchanfragenByAllColumns", Matchanfragen.class)
                .setParameter("vn", mail)
                .getResultList();
    }

    public void deleteMatchanfrage(Matchanfragen matchanfragen) {
        em.createNamedQuery("deleteMatchanfrage")
                .setParameter("in", matchanfragen.getInitiator())
                .setParameter("pa", matchanfragen.getPartner())
                .executeUpdate();
    }

    public void deleteNutzer(Nutzer nutzer) {
        em.createNamedQuery("deleteNutzer")
                .setParameter("vn", nutzer.getId())
                .executeUpdate();
    }

    public void deleteSuchanfrageByNutzerID(Nutzer nutzer) {
        em.createNamedQuery("deleteSuchanfrageByNutzerID")
                .setParameter("vn", nutzer)
                .executeUpdate();
    }

    public void deleteMatchanfrageByNutzer(Nutzer nutzer) {
        em.createNamedQuery("deleteMatchanfrageByNutzer")
                .setParameter("vn", nutzer.getMail())
                .executeUpdate();
    }
}