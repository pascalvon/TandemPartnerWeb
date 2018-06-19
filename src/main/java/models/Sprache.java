package models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Bildet eine Sprache-Entit&auml;t als Objekt in Java ab.
 */
@Entity
@NamedQuery(name = "findBySpracheID", query = "SELECT sprache FROM Sprache sprache WHERE sprache.id = :id")
public class Sprache {

    /**
     * Repr&auml;sentiert die ID der Sprache-Entit&auml;t.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Repr&auml;sentiert den Namen der Sprache-Entit&auml;t.
     */
    @Column(nullable = false)
    private String nameSprache;

    /**
     * Ein {@code Set}-Objekt, welches, durch die {@code @ManyToMany}-Annotation, in der Datenbank die
     * Sprache-Tabelle mit der Nutzer-Tabelle mapped.
     */
    @ManyToMany(mappedBy = "sprachenSet")
    private Set<Nutzer> nutzerSet;

    /**
     * Gibt die ID der Sprache-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt die ID der Sprache-Entit&auml;t zur&uuml;ck.
     */
    public int getId() {
        return id;
    }

    /**
     * Gibt den Namen der Sprache-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt den Namen der Sprache-Entit&auml;t zur&uuml;ck.
     */
    public String getNameSprache() {
        return nameSprache;
    }

    /**
     * &Uuml;berschreibt die {@link #toString() toString}-Methode, sodass {@link #nameSprache nameSprache}
     * zur&uuml;ckgegeben wird.
     *
     * @return Gibt den Namen der Sprache zur&uuml;ck.
     */
    @Override
    public String toString() {
        return nameSprache;
    }

    /**
     * &Uuml;berschreibt die {@link #equals(Object) equals}-Methode, sodass {@code Sprache}-Ojekte &uuml;ber
     * ihre {@link #id id} verglichen werden.
     *
     * @param o Das Objekt, mit dem verglichen werden soll.
     * @return Gibt true zur&uuml;ck, wenn beide Objekte die selbe ID besitzen und false, wenn sie eine
     * unterschiedliche ID besitzen.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sprache sprache = (Sprache) o;
        return id == sprache.id;
    }

    /**
     * &Uuml;berschreibt die {@link #hashCode() hashCode}-Methode, sodass der Hashcode von {@code Sprache}-
     * Objekten ihrer ID entspricht.
     *
     * @return Gibt den Hashcode des Objekts als seine ID wieder.
     */
    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
