package dao;

import models.*;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;

/**
 * Das {@code DAO}-Objekt enth&auml;lt Methoden, um alle notwendigen Datenbankabfragen zu erm&ouml;glichen.
 */
@Stateless
public class DAO {

    /**
     * Das {@code EntityManager}-Objekt wird verwendet, um persistente Entit�ten zu erstellen, zu entfernen und
     * Entit�ten anhand ihres Prim�rschl�ssels bzw. weiteren Parametern zu finden und �ber Entit�ten abzufragen.
     */
    @PersistenceContext(unitName = "ExperimentalJPADatabase")
    private EntityManager em;

    // ================= Bezirk ===================

    /**
     * Sucht anhand der ID, in Form eines {@code int}-Wertes, eine Bezirk-Entit&auml;t in der Datenbank
     * und gibt sie als {@code Bezirk}-Objekt wieder.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "findBezirkByID" der Klasse {@code Bezirk}.
     *
     * @param   bezirkID Die ID der gesuchten Bezirk-Entit&auml;t.
     * @return  Gibt das gesuchte {@code Bezirk}-Objekt zur&uuml;ck.
     */
    public Bezirk findBezirkByID(int bezirkID) {
        return em.createNamedQuery("findBezirkByID", Bezirk.class)
                .setParameter("id", bezirkID)
                .getSingleResult();
    }

    // ================= Freizeitaktivitaeten ===================
    /**
     * Sucht anhand der ID, in Form eines {@code int}-Wertes, eine Freizeitaktivitaeten-Entit&auml;t in der Datenbank
     * und gibt sie als {@code Freizeitaktivitaeten}-Objekt wieder.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "findByFreizeitaktivitaetenID" der Klasse {@code Freizeitaktivitaeten}.
     *
     * @param   freizeitaktivitaetenID Die ID der gesuchten Freizeitaktivitaeten-Entit&auml;t.
     * @return  Gibt das gesuchte {@code Freizeitaktivitaeten}-Objekt zur&uuml;ck.
     */
    public Freizeitaktivitaeten findFreizeitaktivitaetenByID(int freizeitaktivitaetenID) {
        return em.createNamedQuery("findByFreizeitaktivitaetenID", Freizeitaktivitaeten.class)
                .setParameter("id", freizeitaktivitaetenID)
                .getSingleResult();
    }

    // ================= Matchanfragen ===================
    /**
     * Sucht anhand der ID eines {@code Nutzer}-Objektes, in Form eines {@code int}-Wertes, eine Liste mit Matchanfragen-Entit&auml;ten in der Datenbank,
     * in der die ID dem Wert der partner-Spalte entspricht und der Wert der angenommen-Spalte "0" ist.
     * Anschlie&szlig;end wird die Liste als {@code ArrayList}-Objekt mit der Typisierung {@code Matchanfragen} zur&uuml;ckgegeben.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "findMatchanfragenByNutzerID" der Klasse {@code Matchanfragen}.
     *
     * @param   nutzerID Die ID des {@code Nutzer}-Objektes, dessen Matchanfragen-Entit&auml;ten gesucht werden.
     * @return  Gibt eine {@code ArrayList} mit den {@code Matchanfragen}-Objekten wieder, bei denen der Wert der partner-Spalte mit der ID
     *          des {@code Nutzer}-Objektes &uuml;berseinstimmen und der Wert der angenommen-Spalte "0" ist.
     */
    public ArrayList<Matchanfragen> findMatchanfragenByNutzerID(int nutzerID) {
        return (ArrayList<Matchanfragen>) em.createNamedQuery("findMatchanfragenByNutzerID", Matchanfragen.class)
                .setParameter("partnerID", nutzerID)
                .getResultList();
    }

    /**
     * Sucht anhand der ID eines {@code Nutzer}-Objektes, in Form eines {@code int}-Wertes, eine Liste mit Matchanfragen-Entit&auml;ten in der Datenbank,
     * in der die ID dem Wert der initiator-Spalte oder der partner-Spalte entspricht und der Wert der angenommen-Spalte "1" ist.
     * Anschlie&szlig;end wird die Liste als {@code ArrayList}-Objekt mit der Typisierung {@code Matchanfragen} zur&uuml;ckgegeben.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "findMatchanfragenByAllColumns" der Klasse {@code Matchanfragen}.
     *
     * @param   nutzerID Die ID des {@code Nutzer}-Objektes, dessen Matchanfragen-Entit&auml;ten gesucht werden.
     * @return  Gibt eine {@code ArrayList} mit den {@code Matchanfragen} wieder, bei denen der Wert der initiator-Spalte oder
     *          der partner-Spalte mit der ID des {@code Nutzer}-Objektes &uuml;berseinstimmen und der Wert der angenommen-Spalte "1" ist.
     */
    public ArrayList<Matchanfragen> findMatchanfragenByAllColumns(int nutzerID) {
        return (ArrayList<Matchanfragen>) em.createNamedQuery("findMatchanfragenByAllColumns", Matchanfragen.class)
                .setParameter("nutzerID", nutzerID)
                .getResultList();
    }

    /**
     * Sucht anhand eines {@code Matchanfragen}-Objektes in der Datenbank nach einer identischen Matchanfragen-Entit&auml;t
     * und gibt sie als {@code Matchanfragen}-Objekt wieder. Falls es die Entit&auml;t nicht gibt, wird ein {@code Matchanfragen}-Objekt
     * mit dem Wert "null" zur&uuml;ckgegeben.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "findMatchanfragenByMatchanfragen" der Klasse {@code Matchanfragen}.
     *
     * @param   matchanfragen {@code Matchanfragen}-Objekt, welches gesucht wird.
     * @return  Gibt ein {@code Matchanfragen}-Objekt wieder, welches dem gesuchten {@code Matchanfragen}-Objekt entspricht oder den Wert "null" besitzt.
     */
    public Matchanfragen findMatchanfragenByMatchanfragen(Matchanfragen matchanfragen) {
        try {
            return em.createNamedQuery("findMatchanfragenByMatchanfragen", Matchanfragen.class)
                    .setParameter("matchanfragen", matchanfragen)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Sucht anhand der ID's des Initiators, Partners und der Sprache, in Form eines {@code int}-Wertes, in der Datenbank nach einer Matchanfragen-Entit&auml;t
     * und gibt sie als {@code Matchanfragen}-Objekt wieder. Falls es die Entit&auml;t nicht gibt, wird ein {@code Matchanfragen}-Objekt
     * mit dem Wert "null" zur&uuml;ckgegeben.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "findMatchanfragenByInitiatorPartnerSpracheID" der Klasse {@code Matchanfragen}.
     *
     * @param   initiator {@code Nutzer}-Objekt, welches dem Initiator entspricht.
     * @param   partner {@code Nutzer}-Objekt, welches dem Partner entspricht.
     * @param   spracheID ID der Sprache der gesuchten Matchanfragen-Entit&auml;t.
     * @return  Gibt ein {@code Matchanfragen}-Objekt wieder, welches mit den gesuchten Parametern &uuml;bereinstimmt oder den Wert "null" besitzt.
     */
    public Matchanfragen findMatchanfragenByInitiatorPartnerSpracheID(Nutzer initiator, Nutzer partner, int spracheID) {
        try {
            return em.createNamedQuery("findMatchanfragenByInitiatorPartnerSpracheID", Matchanfragen.class)
                    .setParameter("initiatorID", initiator.getId())
                    .setParameter("partnerID", partner.getId())
                    .setParameter("spracheID", spracheID)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * L&ouml;scht eine Matchanfragen-Entit&auml;t aus der Datenbank, die der matchID des &uuml;bergebenen {@code Matchanfrage}-Objektes entspricht.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "deleteMatchanfrage" der Klasse {@code Matchanfragen}.
     *
     * @param matchanfragen {@code Matchanfragen}-Objekt, welches gel&ouml;scht werdensoll.
     */
    public void deleteMatchanfrage(Matchanfragen matchanfragen) {
        em.createNamedQuery("deleteMatchanfrage")
                .setParameter("matchID", matchanfragen.getId())
                .executeUpdate();
    }

    /**
     * L&ouml;scht alle Matchanfragen-Entit&auml;ten aus der Datenbank, die der ID des &uuml;bergebenen {@code Nutzer}-Objektes entsprechen.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "deleteMatchanfrageByNutzer" der Klasse {@code Matchanfragen}.
     *
     * @param nutzer {@code Nutzer}-Objekt, dessen Matchanfragen-Entit&auml;ten gel&ouml;scht werden sollen.
     */
    public void deleteMatchanfrageByNutzer(Nutzer nutzer) {
        em.createNamedQuery("deleteMatchanfrageByNutzer")
                .setParameter("nutzerID", nutzer.getId())
                .executeUpdate();
    }

    // ================= Nutzer ===================
    /**
     * Sucht anhand der E-Mail-Adresse, in Form eines {@code String}-Wertes, eine Nutzer-Entit&auml;t in der Datenbank
     * und gibt sie als {@code Nutzer}-Objekt wieder. Falls es die Entit&auml;t nicht gibt, wird ein {@code Nutzer}-Objekt
     * mit dem Wert "null" zur&uuml;ckgegeben.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "findNutzerByMail" der Klasse {@code Nutzer}.
     *
     * @param   mail Die E-Mail-Adresse des gesuchten {@code Nutzer}-Objektes.
     * @return  Gibt ein {@code Nutzer}-Objekt wieder, dessen E-Mail-Adresse mit der &uuml;bergebenen E-Mail-Adresse &uuml;bereinstimmt oder den Wert "null" besitzt.
     */
    public Nutzer findNutzerByMail(String mail) {
        try {
            return em.createNamedQuery("findNutzerByMail", Nutzer.class)
                    .setParameter("mail", mail)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Sucht anhand der ID, in Form eines {@code int}-Wertes, eine Nutzer-Entit&auml;t in der Datenbank
     * und gibt sie als {@code Nutzer}-Objekt wieder. Falls es die Entit&auml;t nicht gibt, wird ein {@code Nutzer}-Objekt
     * mit dem Wert "null" zur&uuml;ckgegeben.
     * Daf&uuml;r nutzt die Methode die Methode find() der Klasse {@code EntityManager}.
     *
     * @param   nutzerID Die ID der gesuchten Nutzer-Entit&auml;t.
     * @return  Gibt ein {@code Nutzer}-Objekt wieder, dessen ID mit der &uuml;bergebenen ID &uuml;bereinstimmt oder den Wert "null" besitzt.
     */
    public Nutzer findNutzerByID(int nutzerID) {
        try {
            return em.find(Nutzer.class, nutzerID);

        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Sucht anhand der ID einer Sprache-Entit&auml;t, in Form eines {@code int}-Wertes, eine Liste mit Nutzer-Entit&auml;ten in der Datenbank,
     * in der die ID dem Wert einer sprachenSet_id-Spalte in der Nutzer_Sprache entspricht.
     * Anschlie&szlig;end wird die Liste als {@code ArrayList}-Objekt mit der Typisierung {@code Nutzer} zur&uuml;ckgegeben.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "findNutzerBySprachID" der Klasse {@code Nutzer}.
     *
     * @param   spracheID Die ID der Sprache-Entit&auml;t, dessen Nutzer-Entit&auml;ten gesucht werden.
     * @return  Gibt eine {@code ArrayList} mit den {@code Nutzer}-Objekten wieder, die in der Nutzer_Sprache-Tabelle in der
     *          sprachenSet_id-Spalte den Wert der ID der Sprache-Entit&auml;t haben.
     */
    public ArrayList<Nutzer> findNutzerBySpracheID(int spracheID) {
        return (ArrayList<Nutzer>) em.createNamedQuery("findNutzerBySprachID", Nutzer.class)
                .setParameter("spracheID", spracheID)
                .getResultList();
    }

    /**
     * Sucht in der Tabelle Nutzer nach Eintr�gen, wo in der Spalte Mail der Wert @param mail steht
     * @param mail
     * @return
     */
    public boolean findNutzerByMailBoolean(String mail) {
        try {
            Nutzer n = em.createNamedQuery("findNutzerByMailBoolean", Nutzer.class)
                    .setParameter("mail", mail)
                    .getSingleResult();
            /**
             * Wird eine Mail gefunden (length>0) wird der Wert auf false gesetzt, falls nicht auf true
             */
            if (n.getMail().length()>0) {
                return false;
            }
            else {
                return true;
            }
        } catch (NoResultException e) {
            return true;
        }
    }

    /**
     * L&ouml;scht eine  Nutzer-Entit&auml;t aus der Datenbank, die der ID des &uuml;bergebenen {@code Nutzer}-Objektes entspricht.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "deleteNutzer" der Klasse {@code Nutzer}.
     *
     * @param nutzer {@code Nutzer}-Objekt, dessen Nutzer-Entit&auml;t gel&ouml;scht werden soll.
     */
    public void deleteNutzer(Nutzer nutzer) {
        em.createNamedQuery("deleteNutzer")
                .setParameter("id", nutzer.getId())
                .executeUpdate();
    }

    // ================= Sprache ===================
    /**
     * Sucht anhand der ID einer Sprache-Entit&auml;t, in Form eines {@code int}-Wertes, eine Sprache-Entit&auml;t in der Datenbank
     * und gibt sie als {@code Sprache}-Objekt wieder.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "findBySpracheID" der Klasse {@code Sprache}.
     *
     * @param   spracheID Die ID der Sprache-Entit&auml;t, dessen Sprache-Entit&auml;t gesucht wird.
     * @return  Gibt das gesuchte {@code Sprache}-Objekt wieder.
     */
    public Sprache findSpracheByID(int spracheID) {
        return em.createNamedQuery("findBySpracheID", Sprache.class)
                .setParameter("id", spracheID)
                .getSingleResult();
    }

    // ================= Suchanfrage ===================

    /**
     * Sucht anhand eines {@code Nutzer}-Objektes in der Datenbank nach allen Suchanfrage-Entit&auml;ten,
     * die diesem {@code Nutzer}-Objekt entsprechen.
     * Anschlie&szlig;end wird die Liste als {@code ArrayList}-Objekt mit der Typisierung {@code Suchanfrage} zur&uuml;ckgegeben.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "findSuchanfrageByNutzer" der Klasse {@code Suchanfrage}.
     *
     * @param   nutzer {@code Nutzer}-Objekt, dessen Suchanfrage-Entit&auml;ten gesucht werden.
     * @return  Gibt eine {@code ArrayList} mit den {@code Suchanfrage}-Objekten wieder, nutzer_Id-Spalte mit der ID
     *          des {@code Nutzer}-Objektes &uuml;berseinstimmen.
     */
    public ArrayList<Suchanfrage> findSuchanfrageByNutzer(Nutzer nutzer) {
        return (ArrayList<Suchanfrage>) em.createNamedQuery("findSuchanfrageByNutzer", Suchanfrage.class)
                .setParameter("nutzer", nutzer)
                .getResultList();
    }

    /**
     * Sucht anhand der Sprache-ID eines {@code Suchanfrage}-Objektes und der ID eines {@code Nuter}-Objektes, in der Datenbank nach einer Suchanfrage-Entit&auml;t
     * und gibt sie als {@code Suchanfrage}-Objekt wieder. Falls es die Entit&auml;t nicht gibt, wird ein {@code Suchanfrage}-Objekt
     * mit dem Wert "null" zur&uuml;ckgegeben.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "findSuchanfrage" der Klasse {@code Suchanfrage}.
     *
     * @param   suchanfrage {@code Suchanfrage}-Objekt, dessen Sprache-ID f&uuml;r die Suche genutzt wird.
     * @param   nutzer {@code Nutzer}-Objekt, dessen ID f&uuml;r die Suche genutzt wird.
     * @return  Gibt ein {@code Suchanfrage}-Objekt wieder, welches mit den gesuchten Parametern &uuml;bereinstimmt oder den Wert "null" besitzt.
     */
    public Suchanfrage findSuchanfrage(Suchanfrage suchanfrage, Nutzer nutzer) {
        try {
            return (Suchanfrage) em.createNamedQuery("findSuchanfrage")
                    .setParameter("spracheId", suchanfrage.getParamSpracheID())
                    .setParameter("nutzerId", nutzer.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * L&ouml;scht eine  Suchanfrage-Entit&auml;t aus der Datenbank, die der ID des &uuml;bergebenen {@code Suchanfrage}-Objektes entspricht.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "deleteSuchanfrage" der Klasse {@code Suchanfrage}.
     *
     * @param suchanfrage {@code Suchanfrage}-Objekt, welches gel&ouml;scht werden soll.
     */
    public void deleteSuchanfrageNQ(Suchanfrage suchanfrage) {
        em.createNamedQuery("deleteSuchanfrage")
                .setParameter("id", suchanfrage.getSuchId())
                .executeUpdate();
    }

    /**
     * L&ouml;scht alle  Suchanfrage-Entit&auml;ten aus der Datenbank, die der ID des &uuml;bergebenen {@code Nutzer}-Objektes entsprechen.
     * Daf&uuml;r nutzt die Methode die {@code @NamedQuery} "deleteSuchanfrageByNutzer" der Klasse {@code Suchanfrage}.
     *
     * @param nutzer {@code Nutzer}-Objekt, dessen Suchanfrage-Entit&auml;ten gel&ouml;scht werden sollen.
     */
    public void deleteSuchanfrageByNutzer(Nutzer nutzer) {
        em.createNamedQuery("deleteSuchanfrageByNutzer")
                .setParameter("nutzer", nutzer)
                .executeUpdate();
    }

    // ================= Merge ===================
    /**
     * Merged ein Objekt mit der Datenbank
     * @param objectToMerge Objekt, welches gemerged werden soll.
     */
    public void merge(Object objectToMerge) {
        em.merge(objectToMerge);
    }
}