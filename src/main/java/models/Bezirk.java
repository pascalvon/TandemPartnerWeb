package models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Bildet eine Bezirk-Entit&auml;t als Objekt in Java ab.
 */
@Entity
@NamedQuery(name = "findBezirkByID", query = "SELECT bezirk FROM Bezirk bezirk WHERE bezirk.id = :id")
public class Bezirk {

    /**
     * Repr&auml;sentiert die ID der Entit&auml;t.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    /**
     * // todo CCE : Ueberhaupt noetig?
     */
    @OneToMany(mappedBy = "bezirk", cascade = CascadeType.ALL)
    private Set<Nutzer> nutzerSet;

    @Column(nullable = false)
    private String bezirkName;

    public Bezirk(){
        super();
        nutzerSet = new HashSet<>();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBezirkName() {
        return bezirkName;
    }

    public void setBezirkName(String bezirkName) {
        this.bezirkName = bezirkName;
    }

    @Override
    public String toString() {
        return bezirkName;
    }
}
