package models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "findByFreizeitaktivitaetenID", query = "SELECT freizeitaktivitaeten FROM Freizeitaktivitaeten freizeitaktivitaeten WHERE freizeitaktivitaeten.id = :vn")
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
}
