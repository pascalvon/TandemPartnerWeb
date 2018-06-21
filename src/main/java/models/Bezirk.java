package models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Bildet eine Bezirk-Entit&auml;t als Objekt in Java ab.
 */
@Entity
@NamedQuery(name = "findBezirkByID", query = "SELECT bezirk FROM Bezirk bezirk WHERE bezirk.id = :id")
public class Bezirk {

    /**
     * Repr&auml;sentiert die ID der Bezirk-Entit&auml;t.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    /**
     * Repr&auml;sentiert den Bezirksnamen der Bezirk-Entit&auml;t.
     */
    @Column(nullable = false)
    private String bezirkName;

    public Bezirk() {

    }

    public Bezirk(int id, String bezirkName) {
        this.Id = id;
        this.bezirkName = bezirkName;
    }

    /**
     * Gibt die ID der Bezirk-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt die ID der Bezirk-Entit&auml;t zur&uuml;ck.
     */
    public int getId() {
        return Id;
    }

    /**
     * &Uuml;berschreibt die {@link #toString() toString}-Methode, sodass der {@link #bezirkName bezirkName}
     * zur&uuml;ckgegeben wird.
     *
     * @return Gibt den Bezirksnamen zur&uuml;ck.
     */
    @Override
    public String toString() {
        return bezirkName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bezirk bezirk = (Bezirk) o;
        return Id == bezirk.Id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(Id);
    }
}
