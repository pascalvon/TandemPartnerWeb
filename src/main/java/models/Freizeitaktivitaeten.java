package models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Bildet eine Freizeitaktivitaeten-Entit&auml;t als Objekt in Java ab.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "findFreizeitaktivitaetenByID", query = "SELECT freizeitaktivitaeten FROM Freizeitaktivitaeten freizeitaktivitaeten WHERE freizeitaktivitaeten.id = :id"),
        @NamedQuery(name = "findFreizeitaktivitaetenByName", query = "SELECT freizeitaktivitaeten FROM Freizeitaktivitaeten freizeitaktivitaeten WHERE freizeitaktivitaeten.nameAktivitaet = :name" ),
        @NamedQuery(name = "findFreizeitaktivitaetenList", query = "SELECT freizeitaktivitaeten FROM Freizeitaktivitaeten freizeitaktivitaeten")})
public class Freizeitaktivitaeten {

    /**
     * Repr&auml;sentiert die ID der Freizeitaktivitaeten-Entit&auml;t.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * Repr&auml;sentiert den Namen der Freizeitaktivitaeten-Entit&auml;t.
     */
    @Column(nullable = false)
    private String nameAktivitaet;

    /**
     * Ein {@code Set}-Objekt, welches, durch die {@code @ManyToMany}-Annotation, in der Datenbank die
     * Freizeitaktivitaeten-Tabelle mit der Nutzer-Tabelle mapped.
     */
    @ManyToMany(mappedBy = "freizeitaktivitaetenSet")
    private Set<Nutzer> nutzerSet;

    /**
     * Der Standardkonstruktor des {@code Freizeitaktivitaeten}-Objektes.
     */
    public Freizeitaktivitaeten() {
    }

    /**
     * Initialisiert ein {@code Freizeitaktivitaeten}-Objekt mit den eingegebenen Parametern und weißt diese den entsprechenden
     * Variablen zu. Dieser Konstruktor wird ausschlie&szlig;ig f%uuml;r die Unittests ben&ouml;tigt.
     *
     * @param id {@code int}-Wert, welcher die ID des {@code Freizeitaktivitaeten}-Objektes repr&auml;sentiert.
     * @param nameAktivitaet {@code String}, welcher den Namen des {@code Freizeitaktivitaeten}-Objektes repr&auml;sentiert.
     */
    public Freizeitaktivitaeten(int id, String nameAktivitaet) {
        this.id = id;
        this.nameAktivitaet = nameAktivitaet;
    }

    /**
     * Gibt die ID der Freizeitaktivitaeten-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt die ID der Freizeitaktivitaeten-Entit&auml;t zur&uuml;ck.
     */
    public int getId() {
        return id;
    }

    /**
     * Gibt den Namen der Freizeitaktivitaeten-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt den Namen der Freizeitaktivitaeten-Entit&auml;t zur&uuml;ck.
     */
    public String getNameAktivitaet() {
        return nameAktivitaet;
    }

    /**
     * &Uuml;berschreibt die {@link #toString() toString}-Methode, sodass {@link #nameAktivitaet nameAktivitaet}
     * zur&uuml;ckgegeben wird.
     *
     * @return Gibt den Namen der Freizeitaktivit&auml;t zur&uuml;ck.
     */
    @Override
    public String toString() {
        return nameAktivitaet;
    }

    /**
     * &Uuml;berschreibt die {@link #equals(Object) equals}-Methode, sodass {@code Freizeitaktivitaeten}-Ojekte &uuml;ber
     * ihre {@link #id id} verglichen werden.
     *
     * @param   o Das Objekt, mit dem verglichen werden soll.
     * @return  Gibt true zur&uuml;ck, wenn beide Objekte die selbe ID besitzen und false, wenn sie eine
     *          unterschiedliche ID besitzen.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Freizeitaktivitaeten that = (Freizeitaktivitaeten) o;
        return id == that.id;
    }

    /**
     * &Uuml;berschreibt die {@link #hashCode() hashCode}-Methode, sodass der Hashcode von {@code Freizeitaktivitaeten}-
     * Objekten ihrer ID entspricht.
     *
     * @return Gibt den Hashcode des Objekts als seine ID wieder.
     */
    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
