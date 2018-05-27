package dao;

import models.*;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;

@Stateless
public class DAO {

    @PersistenceContext(unitName = "ExperimentalJPADatabase")
    private EntityManager em;

    /** ================= Bezirk =================== */
    public Bezirk findBezirkByID(int bezirkID) {
        return em.createNamedQuery("findBezirkByID", Bezirk.class)
                .setParameter("id", bezirkID)
                .getSingleResult();
    }

    /** ================= Freizeitaktivitaeten =================== */
    public Freizeitaktivitaeten findFreizeitaktivitaetenByID(String freizeitaktivitaetenID) {
        return em.createNamedQuery("findByFreizeitaktivitaetenID", Freizeitaktivitaeten.class)
                .setParameter("id", Integer.parseInt(freizeitaktivitaetenID))
                .getSingleResult();
    }

    /** ================= Matchanfragen =================== */
    public ArrayList<Matchanfragen> findMatchanfragenByMail(String mail) {
        return (ArrayList<Matchanfragen>) em.createNamedQuery("findMatchanfragenByMail", Matchanfragen.class)
                .setParameter("partnerMail", mail)
                .getResultList();
    }

    public ArrayList<Matchanfragen> findMatchanfragenByAllColumns(String mail) {
        return (ArrayList<Matchanfragen>) em.createNamedQuery("findMatchanfragenByAllColumns", Matchanfragen.class)
                .setParameter("mail", mail)
                .getResultList();
    }

    public Matchanfragen findMatchanfragenByMatchanfragen(Matchanfragen matchanfragen) {
        try {
            return em.createNamedQuery("findMatchanfragenByMatchanfragen", Matchanfragen.class)
                    .setParameter("matchanfragen", matchanfragen)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Matchanfragen findMatchanfragenByInitiatorAndPartner(Nutzer initiator, Nutzer partner) {
        try {
            return (Matchanfragen) em.createNamedQuery("findMatchanfragenByInitiatorAndPartner", Matchanfragen.class)
                    .setParameter("initiator", initiator.getMail())
                    .setParameter("partner", partner.getMail())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void deleteMatchanfrage(Matchanfragen matchanfragen) {
        em.createNamedQuery("deleteMatchanfrage")
                .setParameter("initiator", matchanfragen.getInitiator())
                .setParameter("partner", matchanfragen.getPartner())
                .executeUpdate();
    }

    public void deleteMatchanfrageByNutzer(Nutzer nutzer) {
        em.createNamedQuery("deleteMatchanfrageByNutzer")
                .setParameter("mail", nutzer.getMail())
                .executeUpdate();
    }

    public void merge(Matchanfragen matchanfrage) {
        em.merge(matchanfrage);
    }

    /** ================= Nutzer =================== */
    public Nutzer findNutzerByMail(String mail) {
        try {
            return em.createNamedQuery("findNutzerByMail", Nutzer.class)
                    .setParameter("mail", mail)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ArrayList<Nutzer> findNutzerBySpracheID(int spracheID) {
        return (ArrayList<Nutzer>) em.createNamedQuery("findNutzerBySprachID", Nutzer.class)
                .setParameter("spracheID", spracheID)
                .getResultList();
    }

    public void deleteNutzer(Nutzer nutzer) {
        em.createNamedQuery("deleteNutzer")
                .setParameter("id", nutzer.getId())
                .executeUpdate();
    }

    public void merge(Nutzer nutzer) {
        em.merge(nutzer);
    }

    /** ================= Sprache =================== */
    public Sprache findSpracheByID(String spracheID) {
        return em.createNamedQuery("findBySpracheID", Sprache.class)
                .setParameter("id", Integer.parseInt(spracheID))
                .getSingleResult();
    }

    /** ================= Suchanfrage =================== */
    public ArrayList<Suchanfrage> findSuchanfrageByNutzer(Nutzer nutzer) {
        return (ArrayList<Suchanfrage>) em.createNamedQuery("findSuchanfrageByNutzer", Suchanfrage.class)
                .setParameter("nutzer", nutzer)
                .getResultList();
    }
    public void deleteSuchanfrageNQ(Suchanfrage suchanfrage) {
        em.createNamedQuery("deleteSuchanfrage")
                .setParameter("id", suchanfrage.getSuchId())
                .executeUpdate();
    }

    public void deleteSuchanfrageByNutzer(Nutzer nutzer) {
        em.createNamedQuery("deleteSuchanfrageByNutzer")
                .setParameter("nutzer", nutzer)
                .executeUpdate();
    }

    public void merge(Suchanfrage suchanfrage) {
        em.merge(suchanfrage);
    }
















}