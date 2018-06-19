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
    private int Id;

    /**
     * Repr&auml;sentiert den Bezirksnamen der Freizeitaktivitaeten-Entit&auml;t.
     */
    @Column(nullable = false)
    private String nameAktivitaet;

    /**
     * Mapped in der Datenbank die Freizeitaktivitaeten-Tabelle mit der Nutzer-Tabelle
     */
    @ManyToMany(mappedBy = "freizeitaktivitaetenSet")
    private Set<Nutzer> nutzerSet;

    
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNameAktivitaet() {
        return nameAktivitaet;
    }

    public void setNameAktivitaet(String nameAktivitaet) {
        this.nameAktivitaet = nameAktivitaet;
    }

    @Override
    public String toString() {
        return getNameAktivitaet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Freizeitaktivitaeten that = (Freizeitaktivitaeten) o;
        return Id == that.Id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(Id);
    }
}
