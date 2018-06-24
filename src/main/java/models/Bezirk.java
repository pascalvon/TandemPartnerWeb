package models;


import javax.persistence.*;
import java.util.Objects;

/**
 * Bildet eine Bezirk-Entit&auml;t als Objekt in Java ab.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "findBezirkByName", query = "SELECT bezirk FROM Bezirk bezirk WHERE bezirk.bezirkName = :name"),
        @NamedQuery(name = "findBezirkList", query = "SELECT bezirk FROM Bezirk bezirk")})
public class Bezirk {

    /**
     * Repr&auml;sentiert die ID der Bezirk-Entit&auml;t.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Repr&auml;sentiert den Bezirksnamen der Bezirk-Entit&auml;t.
     */
    @Column(nullable = false)
    private String bezirkName;

    /**
     * Der Standardkonstruktor des {@code Bezirk}-Objektes.
     */
    public Bezirk() {
    }

    /**
     * Initialisiert ein {@code Bezirk}-Objekt mit den eingegebenen Parametern und weißt diese den entsprechenden
     * Variablen zu. Dieser Konstruktor wird ausschlie&szlig;ig f%uuml;r die Unittests ben&ouml;tigt.
     *
     * @param id {@code int}-Wert, welcher die ID des {@code Bezirk}-Objektes repr&auml;sentiert.
     * @param bezirkName {@code String}, welcher den Namen des {@code Bezirk}-Objektes repr&auml;sentiert.
     */
    public Bezirk(int id, String bezirkName) {
        this.id = id;
        this.bezirkName = bezirkName;
    }

    /**
     * Gibt die ID der Bezirk-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt die ID der Bezirk-Entit&auml;t zur&uuml;ck.
     */
    public int getId() {
        return id;
    }

    /**
     * Gibt den Namen der Bezirk-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt den Namen der Bezirk-Entit&auml;t zur&uuml;ck.
     */
    public String getBezirkName() {
        return bezirkName;
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

    /**
     * &Uuml;berschreibt die {@link #equals(Object) equals}-Methode, sodass {@code Bezirk}-Ojekte &uuml;ber
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
        Bezirk bezirk = (Bezirk) o;
        return id == bezirk.id;
    }

    /**
     * &Uuml;berschreibt die {@link #hashCode() hashCode}-Methode, sodass der Hashcode von {@code Bezirk}-
     * Objekten ihrer ID entspricht.
     *
     * @return Gibt den Hashcode des Objekts als seine ID wieder.
     */
    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
