package models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Bildet eine Freizeitaktivitaeten-Entit&auml;t als Objekt in Java ab.
 */
@Entity
@NamedQuery(name = "findByFreizeitaktivitaetenID", query = "SELECT freizeitaktivitaeten FROM Freizeitaktivitaeten freizeitaktivitaeten WHERE freizeitaktivitaeten.id = :id")
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

    public Freizeitaktivitaeten() {

    }

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
