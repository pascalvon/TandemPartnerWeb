package models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "findByFreizeitaktivitaetenID", query = "SELECT freizeitaktivitaeten FROM Freizeitaktivitaeten freizeitaktivitaeten WHERE freizeitaktivitaeten.id = :id")
public class Freizeitaktivitaeten {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(nullable = false)
    private String nameAktivitaet;

    @ManyToMany(mappedBy = "freizeitaktivitaetenSet")
    private Set<Nutzer> nutzerSet;

    public int getId() {
        return Id;
    }

    public String getNameAktivitaet() {
        return nameAktivitaet;
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
